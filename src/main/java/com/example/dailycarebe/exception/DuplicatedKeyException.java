package com.example.dailycarebe.exception;

public class DuplicatedKeyException extends InvalidKeyException {
    public DuplicatedKeyException(String message) {
        this(message, null);
    }

    public DuplicatedKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
