package com.bayztracker.exceptions;

import com.bayztracker.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class BaseException extends RuntimeException {
    public abstract ErrorType getErrorType();
}
