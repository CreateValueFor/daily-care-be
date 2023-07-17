package com.example.dailycarebe.util.exception;


import com.example.dailycarebe.base.exception.BaseApiException;

public class FieldParseException extends BaseApiException {
    private String fieldName;

    public FieldParseException(String message) {
        this(message, null);
    }

    public FieldParseException(String message, String fieldName) {
        this(message, null, fieldName);
    }

    public FieldParseException(String message, Throwable cause, String fieldName) {
        super(message, cause);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
