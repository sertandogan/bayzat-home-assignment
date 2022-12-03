package com.bayztracker.service;

import com.bayztracker.converter.AlertConverter;
import com.bayztracker.domain.Alert;
import com.bayztracker.domain.Currency;
import com.bayztracker.enums.Status;
import com.bayztracker.exceptions.AlertNotFoundException;
import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.request.PutAlertRequest;
import com.bayztracker.model.response.CreateAlertResponse;
import com.bayztracker.model.response.GetAlertResponse;
import com.bayztracker.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final AlertConverter alertConverter;

    public CreateAlertResponse save(CreateAlertRequest payload) {
        var toBeInsertedEntity = alertConverter.fromCreateRequest(payload);
        var insertedEntity = alertRepository.save(toBeInsertedEntity);
        return new CreateAlertResponse(insertedEntity.getId());
    }

    public void update(PutAlertRequest putAlertRequest, Long alertId) {
        var alert = getAlert(putAlertRequest.getUserId(), alertId);
        alertConverter.fromPutRequest(alert, putAlertRequest);
        alertRepository.save(alert);
    }

    public void setStatusTriggered(Alert alert) {
        alert.setStatus(Status.TRIGGERED);
        alertRepository.save(alert);
    }

    public void delete(Long userId, Long alertId) {
        var alert = getAlert(userId, alertId);
        alertRepository.delete(alert);
    }

    public GetAlertResponse getById(Long userId, Long alertId) {
        var alert = getAlert(userId, alertId);
        return alertConverter.fromEntity(alert);
    }

    public List<GetAlertResponse> getAllByUserId(Long userId) {
        var alertList = alertRepository.findAlertsByUserId(userId);
        return alertList.stream().map(alertConverter::fromEntity).collect(Collectors.toList());
    }

    private Alert getAlert(Long userId, Long alertId) {
        return alertRepository.findAlertByUserIdAndId(userId, alertId)
                .orElseThrow(AlertNotFoundException::new);
    }

    public List<Alert> getAllByStatusAndCurrencyAndTargetPriceLessThanEqual(Status status, Currency currency, Pageable pageable) {
        return alertRepository.findAlertsByStatusAndCurrencyAndTargetPriceLessThanEqual(status, currency, currency.getCurrentPrice(), pageable);
    }

}
