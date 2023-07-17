package com.example.dailycarebe.base.exception;

public class BaseApiException extends BaseException {
    public BaseApiException() {
    }

    public BaseApiException(String message) {
        super(message);
    }

    public BaseApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseApiException(Throwable cause) {
        super(cause);
    }

    public BaseApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
