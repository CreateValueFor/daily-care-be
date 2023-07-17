package com.example.dailycarebe.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.internal.PersistentList;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class ObjectUtils {
    public static String getRealTypeName(Object proxied) {
        return getReal(proxied).getClass().getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getReal(Object proxied) {
        if (AopUtils.isJdkDynamicProxy(proxied)) {
            try {
                return (T) ((Advised) proxied).getTargetSource().getTarget();
            } catch (Exception e) {
                return (T) proxied;
            }
        } else {
            return (T) proxied;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T coalesce(T... ts) {
        for (T t : ts)
            if (t != null)
                return t;

        return null;
    }

    /**
     * @return null if string is null or empty
     */
    public static String getNullIfEmpty(final String text) {
        return text == null ? null : text.trim().isEmpty() ? null : text.trim();
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        // else
        return false;
    }

    public static String joinIgnoreNull(CharSequence delimiter, CharSequence... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        // Number of elements not likely worth Arrays.stream overhead.
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs : elements) {
            if (cs != null)
                joiner.add(cs);
        }
        return joiner.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepClone(T object) {
        try {
            FastByteArrayOutputStream fbos =
                new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(object);
            out.flush();
            out.close();

            ObjectInputStream in =
                new ObjectInputStream(fbos.getInputStream());
            return (T) in.readObject();
        } catch (Exception e) {
            log.warn("cannot clone object", e);
            return null;
        }
    }

    public static Optional<Object> getPropertyValueWithAnnotation(Object object, PropertyDescriptor propertyDescriptor, Class<? extends Annotation> annotationClass) {
        try {
            Field field = ReflectionUtils.findField(object.getClass(), propertyDescriptor.getName());
            if (field == null || !field.isAnnotationPresent(annotationClass))
                return Optional.empty();

            Method method = propertyDescriptor.getReadMethod();
            if (Objects.isNull(method))
                return Optional.empty();

            Object value = method.invoke(object);

            return Optional.ofNullable(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<Object> getPropertyValueWithAnnotation(Object object, String propertyName, Class<? extends Annotation> annotationClass) {
        try {
            return getPropertyValueWithAnnotation(object, new BeanWrapperImpl(object).getPropertyDescriptor(propertyName), annotationClass);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <ENTITY extends Collection> ENTITY mergeCollections(ENTITY source, ENTITY target, boolean removeOrphansInTarget) {
        return mergeCollections(source, target, removeOrphansInTarget, null);
    }

    @SuppressWarnings("unchecked")
    public static <ENTITY extends Collection> ENTITY mergeCollections(ENTITY source, ENTITY target, boolean removeOrphansInTarget, onMergeListener onMergeListener) {
        if (source == null)
            return target;

        if (target == null)
            return source;

        if (removeOrphansInTarget) {
            target.removeIf(targetItem -> source.stream().noneMatch(sourceItem -> sourceItem.equals(targetItem)));
        }

        source.forEach(sourceItem -> {
            target.stream()
                .filter(sourceItem::equals)
                .findAny()
                .map(targetItem -> mergeObject(sourceItem, targetItem, true, false, onMergeListener))
                .orElseGet(() -> target.add(sourceItem));
        });

        return target;
    }

    public static <ENTITY> ENTITY mergeObject(ENTITY source, ENTITY target, boolean recursiveOnCollection) {
        return mergeObject(source, target, recursiveOnCollection, false, null);
    }

    public static <ENTITY> ENTITY mergeObject(ENTITY source, ENTITY target, boolean recursiveOnCollection, boolean overwriteIfOnlyNull) {
        return mergeObject(source, target, recursiveOnCollection, overwriteIfOnlyNull, null);
    }

    @SuppressWarnings("unchecked")
    public static <ENTITY> ENTITY mergeObject(ENTITY source, ENTITY target, boolean recursiveOnCollection, boolean overwriteIfOnlyNull, onMergeListener onMergeListener) {
        if (source == null)
            return target;

        if (target == null)
            return source;

        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        final BeanWrapper wrappedTarget = new BeanWrapperImpl(target);

        Stream.of(wrappedSource.getPropertyDescriptors())
            .filter(pd -> Objects.nonNull(pd.getReadMethod()))
            .filter(pd -> wrappedTarget.isReadableProperty(pd.getName()) && wrappedTarget.isWritableProperty(pd.getName()))
            .forEach(pd -> {
                try {
                    PropertyDescriptor targetPD = wrappedTarget.getPropertyDescriptor(pd.getName());
                    Method targetReadMethod = targetPD.getReadMethod();
                    Method targetWriteMethod = targetPD.getWriteMethod();

                    if (Objects.isNull(targetReadMethod) || Objects.isNull(targetWriteMethod))
                        return;

                    Object sourceValue = pd.getReadMethod().invoke(source);
                    if (sourceValue == null)
                        return;

                    Object originalValue = targetReadMethod.invoke(target);

                    if (overwriteIfOnlyNull && originalValue != null)
                        return;

                    if (originalValue instanceof Map) {
                        Map targetMap = (Map) originalValue;
                        Map sourceMap = (Map) sourceValue;

                        List<Object> deleteItemKeys = new ArrayList<>();
                        targetMap.keySet().forEach(key -> {
                            if (!sourceMap.containsKey(key)) {
                                deleteItemKeys.add(key);
                            } else {
                                if (recursiveOnCollection)
                                    mergeObject(sourceMap.get(key), targetMap.get(key), recursiveOnCollection, overwriteIfOnlyNull, onMergeListener);
                            }
                        });
                        deleteItemKeys.forEach(targetMap::remove);

                        sourceMap.keySet().stream()
                            .filter(key -> !targetMap.containsKey(key))
                            .forEach(key -> targetMap.put(key, sourceMap.get(key)));
                    } else if (originalValue instanceof Collection
                        && (originalValue instanceof List || originalValue instanceof Set)) {
                        Collection targetCollection = (Collection) originalValue;
                        Collection sourceCollection = (Collection) sourceValue;

                        if (!overwriteIfOnlyNull) {
                            List deleteItems = new ArrayList();
                            targetCollection.forEach(o -> {
                                Optional optional = sourceCollection.stream()
                                    .filter(o::equals)
                                    .findAny();
                                if (!optional.isPresent()) {
                                    deleteItems.add(o);
                                } else {
                                    if (recursiveOnCollection)
                                        mergeObject((ENTITY) optional.get(), (ENTITY) o, recursiveOnCollection, overwriteIfOnlyNull, onMergeListener);
                                }
                            });
                            targetCollection.removeAll(deleteItems);
                        }

                        sourceCollection.stream()
                            .filter(o -> targetCollection.stream().noneMatch(o::equals))
                            .forEach(targetCollection::add);

                        if (targetCollection instanceof List && !(targetCollection instanceof PersistentList)) {
                            List sourceList = (List) sourceCollection;
                            List targetList = (List) targetCollection;

                            int sourceListSize = sourceList.size();
                            int targetListSize = targetList.size();
                            for (int sourceIndex = 0; sourceIndex < sourceListSize; sourceIndex++) {
                                int targetIndex = targetList.indexOf(sourceList.get(sourceIndex));
                                if (sourceIndex != targetIndex
                                    && targetIndex >= 0 && targetIndex < targetListSize
                                    && sourceIndex < targetListSize)
                                    Collections.swap(targetList, sourceIndex, targetIndex);
                            }
                        }
                    } else {
                        if (onMergeListener != null) {
                            sourceValue = onMergeListener.touchSourceBeforeMerge(sourceValue, target);
                        }
                        if (!overwriteIfOnlyNull || originalValue == null)
                            targetWriteMethod.invoke(target, sourceValue);
                    }
                } catch (Exception e) {
                    ObjectUtils.log.error("cannot merge two objects", e);
                }
            });

        return target;
    }

    public interface onMergeListener {
        <ENTITY> ENTITY touchSourceBeforeMerge(ENTITY source, ENTITY target);
    }
}
