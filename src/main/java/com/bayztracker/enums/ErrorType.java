package com.bayztracker.enums;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorType {

    UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, 5000, "api.error.UNKNOWN_ERROR.message"),
    CURRENCY_NOT_FOUND_EXCEPTION(NOT_FOUND, 4001, "api.error.CURRENCY_NOT_FOUND_EXCEPTION.message"),
    ALERT_CANNOT_SET_AS_NEW_EXCEPTION(BAD_REQUEST, 4002, "api.error.ALERT_CANNOT_SET_AS_NEW_EXCEPTION.message"),
    TRIGGERED_ALERT_CANNOT_BE_CANCELLED_EXCEPTION(BAD_REQUEST, 4003, "api.error.TRIGGERED_ALERT_CANNOT_BE_CANCELLED_EXCEPTION.message"),
    ONLY_TRIGGERED_ALERTS_CAN_BE_ACKED_EXCEPTION(BAD_REQUEST, 4004, "api.error.ONLY_TRIGGERED_ALERTS_CAN_BE_ACKED_EXCEPTION.message"),
    ONLY_NEW_STATUS_ALERTS_CAN_BE_TRIGGERED_EXCEPTION(BAD_REQUEST, 4005, "api.error.ONLY_NEW_STATUS_ALERTS_CAN_BE_TRIGGERED_EXCEPTION.message"),
    USER_NOT_FOUND_EXCEPTION(NOT_FOUND, 4041, "api.error.USER_NOT_FOUND_EXCEPTION.message"),
    ALERT_NOT_FOUND_EXCEPTION(NOT_FOUND, 4042, "api.error.ALERT_NOT_FOUND_EXCEPTION.message");

    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessageKey;

    ErrorType(HttpStatus httpStatus, Integer errorCode, String errorMessageKey) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessageKey = errorMessageKey;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }


}
