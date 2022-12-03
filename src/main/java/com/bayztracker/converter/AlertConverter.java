package com.bayztracker.converter;

import com.bayztracker.domain.Alert;
import com.bayztracker.enums.Status;
import com.bayztracker.exceptions.*;
import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.request.PutAlertRequest;
import com.bayztracker.model.response.GetAlertResponse;
import com.bayztracker.service.CurrencyService;
import com.bayztracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AlertConverter {
    private final CurrencyService currencyService;
    private final UserService userService;

    public Alert fromCreateRequest(CreateAlertRequest payload) {
        var currency = currencyService.getById(payload.getCurrencyId());
        var userExists = userService.existById(payload.getUserId());
        if (!userExists)
            throw new UserNotFoundException();
        Alert alert = new Alert();
        alert.setCurrency(currency);
        alert.setCreatedAt(new Date());
        alert.setTargetPrice(payload.getTargetPrice());
        alert.setStatus(Status.NEW);
        alert.setUserId(payload.getUserId());

        return alert;
    }

    public void fromPutRequest(Alert alert, PutAlertRequest payload) {
        if (payload.getStatus() == Status.NEW) {
            throw new AlertCannotSetAsNewException();
        }

        if (payload.getStatus() == Status.TRIGGERED && alert.getStatus() != Status.NEW) {
            throw new OnlyNewStatusAlertsCanBeTriggeredException();
        }

        if (payload.getStatus() == Status.CANCELLED && alert.getStatus() == Status.TRIGGERED) {
            throw new TriggeredAlertCannotBeCancelledException();
        }

        if (payload.getStatus() == Status.ACKED && alert.getStatus() != Status.TRIGGERED) {
            throw new OnlyTriggeredAlertsCanBeAckedException();
        }

        var currency = currencyService.getById(payload.getCurrencyId());
        alert.setCurrency(currency);
        alert.setTargetPrice(payload.getTargetPrice());
        alert.setStatus(payload.getStatus());
    }

    public GetAlertResponse fromEntity(Alert alert) {
        return GetAlertResponse.builder().
                currencyName(alert.getCurrency().getName())
                .status(alert.getStatus())
                .targetPrice(alert.getTargetPrice())
                .createdAt(alert.getCreatedAt())
                .id(alert.getId())
                .build();
    }
}
