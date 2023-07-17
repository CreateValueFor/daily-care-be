package com.example.dailycarebe.exception;

import com.example.dailycarebe.base.exception.BaseApiException;
import lombok.Getter;
import lombok.Setter;

public class MethodNotImplementedException extends BaseApiException {
    @Getter
    @Setter
    private boolean shouldSendToSlack;

    public MethodNotImplementedException(String message) {
        this(message, null);
    }

    public MethodNotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
