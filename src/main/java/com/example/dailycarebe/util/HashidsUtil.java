package com.example.dailycarebe.util;


import com.example.dailycarebe.hash.HashIds;

public class HashidsUtil {
    private static HashIds defaultHashids;

    public static void setDefaultHashids(HashIds defaultHashids) {
        HashidsUtil.defaultHashids = defaultHashids;
    }

    public static String encodeNumber(Long number) {
        return defaultHashids.encodeNumber(number);
    }

    public static long decodeNumber(String hashId) {
        return defaultHashids.decodeNumber(hashId);
    }
}
