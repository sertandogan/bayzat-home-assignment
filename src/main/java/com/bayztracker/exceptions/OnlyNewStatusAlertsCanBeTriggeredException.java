package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class OnlyNewStatusAlertsCanBeTriggeredException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ONLY_NEW_STATUS_ALERTS_CAN_BE_TRIGGERED_EXCEPTION;
    }
}
