package com.example.dailycarebe.util;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class StringUtil {
    public static String extractFirstPart(String value, String delimiter) {
        String[] values = value.split(delimiter);
        if (values.length > 0) {
            return values[0].trim();
        }
        return "";
    }

    public static Set<String> splitAndTrim(String string, String delimiter) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(delimiter))
            return Sets.newHashSet();

        return Arrays.stream(string.split(delimiter))
                .map(String::trim)
                .map(str -> str.replaceAll("\u00A0", ""))
                .collect(Collectors.toSet());
    }

    public static String replaceAllSpaces(String string) {
        return string.replaceAll("[\\s|\\u2028|\\u00A0]+", "");
    }

    public static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }
}
