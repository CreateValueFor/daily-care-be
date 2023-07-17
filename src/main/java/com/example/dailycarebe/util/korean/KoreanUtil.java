package com.example.dailycarebe.util.korean;

import java.util.Locale;

public class KoreanUtil {
    private static JosaFormatter defaultJosaFormatter = new JosaFormatter();

    public static String format(String format, Object... args) {
        return format(Locale.getDefault(), format, args);
    }

    public static String format(Locale l, String format, Object... args) {
        return defaultJosaFormatter.format(l, format, args);
    }
}
