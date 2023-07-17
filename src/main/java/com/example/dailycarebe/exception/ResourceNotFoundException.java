package com.example.dailycarebe.exception;


import com.example.dailycarebe.base.exception.BaseApiException;

public class ResourceNotFoundException extends BaseApiException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
