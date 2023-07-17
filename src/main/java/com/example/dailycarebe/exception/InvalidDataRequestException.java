package com.example.dailycarebe.exception;

import com.example.dailycarebe.base.exception.BaseApiException;
import lombok.Getter;
import lombok.Setter;

public class InvalidDataRequestException extends BaseApiException {
    @Getter
    @Setter
    private boolean shouldSendToSlack;

    public InvalidDataRequestException(String message) {
        this(message, null);
    }

    public InvalidDataRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
