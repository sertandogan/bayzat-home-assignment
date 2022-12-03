package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class AlertCannotSetAsNewException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ALERT_CANNOT_SET_AS_NEW_EXCEPTION;
    }
}
