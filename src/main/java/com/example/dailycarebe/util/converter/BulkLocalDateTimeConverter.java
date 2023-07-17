package com.example.dailycarebe.util.converter;

import com.example.dailycarebe.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BulkLocalDateTimeConverter implements BulkConverter<LocalDateTime> {

    @Override
    public LocalDateTime toObj(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        } else {
          return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
        }
    }

    @Override
    public String fromObj(LocalDateTime obj) {
        if (obj == null) {
            return null;
        } else {
            return DateUtil.format(obj);
        }
    }
}
