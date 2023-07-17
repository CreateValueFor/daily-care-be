package com.example.dailycarebe.util;

import com.example.dailycarebe.base.orm.IdSupport;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Collection;

public class EntityUtils {
    public static <T> String getRealTypeName(T entity) {
        entity = getReal(entity);
        return entity == null ? null : entity.getClass().getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getReal(T entity) {
        entity = ObjectUtils.getReal(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    public static <ENTITY extends Collection> ENTITY mergeCollections(ENTITY source, ENTITY target) {
        return ObjectUtils.mergeCollections(source, target, true);
    }

    public static <ENTITY extends Collection> ENTITY mergeCollections(ENTITY source, ENTITY target, boolean removeOrphansInTarget) {
        return ObjectUtils.mergeCollections(source, target, removeOrphansInTarget, EntityUtils::touchSourceBeforeMerge);
    }

    public static <ENTITY> ENTITY mergeObject(ENTITY source, ENTITY target) {

        return ObjectUtils.mergeObject(source, target, true, false, EntityUtils::touchSourceBeforeMerge);
    }

    public static <ENTITY> ENTITY mergeObject(ENTITY source, ENTITY target, boolean overwriteIfOnlyNull) {
        return ObjectUtils.mergeObject(source, target, true, overwriteIfOnlyNull, EntityUtils::touchSourceBeforeMerge);
    }

    private static <ENTITY> ENTITY touchSourceBeforeMerge(ENTITY source, ENTITY target) {
        if (source instanceof IdSupport) {
            Object id = ((IdSupport) source).getId();
            if (id instanceof Number && ((Number) id).intValue() == -1) {
                source = null;
            } else if (id instanceof String && StringUtils.isEmpty((String) id)) {
                source = null;
            }
        } else if (source instanceof Number && ((Number) source).intValue() == -1) {
            if (!(source instanceof BigDecimal)) {
                source = null;
            }
        }
        return source;
    }
}
