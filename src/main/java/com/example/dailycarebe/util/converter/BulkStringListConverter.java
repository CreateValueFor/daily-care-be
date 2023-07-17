package com.example.dailycarebe.util.converter;

import com.example.dailycarebe.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BulkStringListConverter implements BulkConverter<List<String>> {

    @Override
    public List<String> toObj(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else {
            return StringUtil.splitAndTrim(value, ",").stream().collect(Collectors.toList());
        }
    }

    @Override
    public String fromObj(List<String> obj) {
        if (obj == null) {
            return null;
        } else {
            return obj.stream().collect(Collectors.joining(","));
        }
    }
}
