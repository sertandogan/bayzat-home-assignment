package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class OnlyTriggeredAlertsCanBeAckedException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ONLY_TRIGGERED_ALERTS_CAN_BE_ACKED_EXCEPTION;
    }
}
