package com.bayztracker.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateAlertRequest {
    @JsonIgnore
    private long userId;
    @NotNull(message = "api.validation.constraints.currencyId.message")
    private Long currencyId;
    @NotNull(message = "api.validation.constraints.targetPrice.message")
    private float targetPrice;
}
