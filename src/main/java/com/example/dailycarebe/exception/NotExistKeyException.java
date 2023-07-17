package com.example.dailycarebe.exception;

public class NotExistKeyException extends InvalidKeyException {
    public NotExistKeyException(String message) {
        this(message, null);
    }

    public NotExistKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
