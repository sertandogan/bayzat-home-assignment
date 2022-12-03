package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class AlertNotFoundException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ALERT_NOT_FOUND_EXCEPTION;
    }
}
