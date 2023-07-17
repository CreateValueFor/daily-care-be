package com.example.dailycarebe.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.dailycarebe.user.model.User;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum EntityType implements Serializable {
    USER(User.class);

    @Getter
    @JsonIgnore
    private final Class<?> mappedClass;

    EntityType(Class<?> clazz) {
        mappedClass = clazz;
    }

    private static final Map<Class<?>, EntityType> classToEntityMap = new HashMap<>();

    static {
        Stream.of(EntityType.values()).forEach(entityType -> {
            classToEntityMap.put(entityType.getMappedClass(), entityType);
        });
    }

    public static EntityType fromClass(Class<?> clazz) {
        return classToEntityMap.get(clazz);
    }
}
