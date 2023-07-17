package com.example.dailycarebe.base.orm.model.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.dailycarebe.util.json.JsonObjectUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.regex.Pattern;

@Slf4j
public abstract class JpaJsonConverter<T> implements AttributeConverter<T, String> {

    protected Class<T> entityClass;

    private static final Pattern EMPTY_JSON_PATTERN = Pattern.compile("\\s*\\{\\s*\\}\\s*");

    abstract protected TypeReference<T> getTypeReference();

    @SuppressWarnings("unchecked")
    public JpaJsonConverter(T... types) {
        if (types.length == 0) {
            entityClass = (Class<T>) types.getClass().getComponentType();
        } else {
            entityClass = (Class<T>) types[0].getClass();
        }
    }

    @Override
    public String convertToDatabaseColumn(T meta) {
        if (meta == null)
            return null;

        try {
            return JsonObjectUtil.toJson(meta);
        } catch (Exception ex) {
            log.error("cannot convert to json", ex);
            return null;
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().length() == 0)
            return null;

        dbData = dbData.trim();

        if (EMPTY_JSON_PATTERN.matcher(dbData).matches())
            return null;

        try {
            return JsonObjectUtil.fromJson(dbData, getTypeReference());
        } catch (Exception ex) {
            log.error("cannot convert from json", ex);
            return null;
        }
    }
}
