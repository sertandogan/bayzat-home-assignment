package com.bayztracker.model.request;

import com.bayztracker.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutAlertRequest {
    @JsonIgnore
    private long userId;
    private Long currencyId;
    private float targetPrice;
    private Status status;
}
