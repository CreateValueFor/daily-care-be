package com.example.dailycarebe.util.converter;

public interface BulkConverter<T> {
    T toObj(String value);

    String fromObj(T obj);
}
