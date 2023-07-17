package com.example.dailycarebe.util.converter;

import com.example.dailycarebe.util.exception.FieldParseException;
import org.apache.commons.lang3.StringUtils;

public class BulkBooleanConverter implements BulkConverter<Boolean> {

    @Override
    public Boolean toObj(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        } else if ("Y".equalsIgnoreCase(value)) {
            return true;
        } else if ("N".equalsIgnoreCase(value)) {
            return false;
        } else {
            throw new FieldParseException(String.format("Boolean value %s cannot be parsed.", value));
        }
    }

    @Override
    public String fromObj(Boolean obj) {
        if (obj == null) {
            return null;
        } else if (obj) {
            return "Y";
        } else {
            return "N";
        }
    }
}
