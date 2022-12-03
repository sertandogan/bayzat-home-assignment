package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class TriggeredAlertCannotBeCancelledException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.TRIGGERED_ALERT_CANNOT_BE_CANCELLED_EXCEPTION;
    }
}
