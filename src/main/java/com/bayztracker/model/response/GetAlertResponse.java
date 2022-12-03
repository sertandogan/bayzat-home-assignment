package com.bayztracker.model.response;

import com.bayztracker.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class GetAlertResponse {
    private Long id;
    private String currencyName;
    private float targetPrice;
    private Date createdAt;
    private Status status;
}
