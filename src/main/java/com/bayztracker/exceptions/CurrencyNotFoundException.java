package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;

public class CurrencyNotFoundException extends BaseException{
    @Override
    public ErrorType getErrorType() {
        return ErrorType.CURRENCY_NOT_FOUND_EXCEPTION;
    }
}
