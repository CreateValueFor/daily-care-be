package com.example.dailycarebe.util.converter;

import com.example.dailycarebe.util.exception.FieldParseException;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class BulkCommaSeparatedLongConverter implements BulkConverter<Long> {

    @Override
    public Long toObj(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        } else {
            try {
                return Long.valueOf(value.replaceAll(",",""));
            } catch (Throwable t) {
                throw new FieldParseException(String.format("%s 는 숫자로 변환할 수 없는 값 입니다.", value));
            }
        }
    }

    @Override
    public String fromObj(Long obj) {
        if (obj == null) {
            return null;
        } else {
            return NumberFormat.getNumberInstance(Locale.US).format(obj);
        }
    }
}
