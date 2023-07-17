package com.example.dailycarebe.base.orm.model.converter;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class JpaStringSetConverter implements AttributeConverter<Set<String>, String> {

    private String delimiter;

    public JpaStringSetConverter() {
        delimiter = ",";
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String convertToDatabaseColumn(Set<String> meta) {
        if (meta == null)
            return null;

        return meta.stream().collect(Collectors.joining(delimiter));
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return new HashSet<>(Arrays.asList(dbData.split(delimiter)));
    }

}
