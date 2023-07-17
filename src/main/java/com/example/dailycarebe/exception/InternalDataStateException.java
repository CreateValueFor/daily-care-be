package com.example.dailycarebe.exception;


import com.example.dailycarebe.base.exception.BaseException;

public class InternalDataStateException extends BaseException {
    public InternalDataStateException(String message) {
        super(message);
    }

    public InternalDataStateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
