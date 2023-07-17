package com.example.dailycarebe.exception;


import com.example.dailycarebe.base.exception.BaseApiException;

public class InvalidKeyException extends BaseApiException {
    public InvalidKeyException(String message) {
        this(message, null);
    }

    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
