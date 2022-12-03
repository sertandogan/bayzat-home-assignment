package com.bayztracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum Status implements Serializable {
    NEW,
    TRIGGERED,
    ACKED,
    CANCELLED;
}
