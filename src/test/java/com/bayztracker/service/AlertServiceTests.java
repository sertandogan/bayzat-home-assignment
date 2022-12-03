package com.bayztracker.service;

import com.bayztracker.base.TestBase;
import com.bayztracker.converter.AlertConverter;
import com.bayztracker.domain.Alert;
import com.bayztracker.exceptions.AlertNotFoundException;
import com.bayztracker.exceptions.CurrencyNotFoundException;
import com.bayztracker.exceptions.UserNotFoundException;
import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.request.PutAlertRequest;
import com.bayztracker.model.response.GetAlertResponse;
import com.bayztracker.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTests extends TestBase {

    @InjectMocks
    AlertService alertService;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private AlertConverter alertConverter;


    @Test
    void it_should_save_alert_successfully_when_everything_is_fine() {
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);
        when(alertConverter.fromCreateRequest(createAlertRequest)).thenReturn(alertEntity);
        when(alertRepository.save(alertEntity)).thenReturn(alertEntity);
        var createAlertResponse = alertService.save(createAlertRequest);

        verify(alertConverter, times(1)).fromCreateRequest(createAlertRequest);
        verify(alertRepository, times(1)).save(alertEntity);
        assertEquals(alertEntity.getId(), createAlertResponse.getId());
    }

    @Test
    void it_should_throws_exception_for_save_when_currency_not_found() {
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);

        when(alertConverter.fromCreateRequest(createAlertRequest)).thenThrow(CurrencyNotFoundException.class);

        assertThrows(CurrencyNotFoundException.class, () -> alertService.save(createAlertRequest));
        verify(alertConverter, times(1)).fromCreateRequest(createAlertRequest);
    }

    @Test
    void it_should_throws_exception_for_save_when_user_not_found() {
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);

        when(alertConverter.fromCreateRequest(createAlertRequest)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> alertService.save(createAlertRequest));
        verify(alertConverter, times(1)).fromCreateRequest(createAlertRequest);
    }

    @Test
    void it_should_throws_exception_for_save_when_request_is_corrupted() {
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertConverter.fromCreateRequest(createAlertRequest)).thenReturn(alertEntity);
        when(alertRepository.save(alertEntity)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> alertService.save(createAlertRequest));
        verify(alertConverter, times(1)).fromCreateRequest(createAlertRequest);
        verify(alertRepository, times(1)).save(alertEntity);
    }

    @Test
    void it_should_update_alert_successfully_when_everything_is_fine() {
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        doNothing().when(alertConverter).fromPutRequest(alertEntity, putAlertRequest);
        when(alertRepository.findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId())).thenReturn(Optional.of(alertEntity));
        when(alertRepository.save(alertEntity)).thenReturn(alertEntity);
        alertService.update(putAlertRequest, alertEntity.getId());

        verify(alertConverter, times(1)).fromPutRequest(alertEntity, putAlertRequest);
        verify(alertRepository, times(1)).save(alertEntity);
        verify(alertRepository, times(1)).findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId());
    }

    @Test
    void it_should_throws_exception_for_update_when_alert_not_found() {
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertRepository.findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId())).thenThrow(AlertNotFoundException.class);
        assertThrows(AlertNotFoundException.class, () -> alertService.update(putAlertRequest, alertEntity.getId()));

        verify(alertRepository, times(1)).findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId());
    }

    @Test
    void it_should_throws_exception_for_update_when_user_not_found() {
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertRepository.findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> alertService.update(putAlertRequest, alertEntity.getId()));

        verify(alertRepository, times(1)).findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId());
    }

    @Test
    void it_should_throws_exception_for_update_when_currency_not_found() {
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertRepository.findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId())).thenReturn(Optional.of(alertEntity));
        doThrow(CurrencyNotFoundException.class).when(alertConverter).fromPutRequest(alertEntity, putAlertRequest);
        assertThrows(CurrencyNotFoundException.class, () -> alertService.update(putAlertRequest, alertEntity.getId()));

        verify(alertConverter, times(1)).fromPutRequest(alertEntity, putAlertRequest);
        verify(alertRepository, times(1)).findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId());
    }

    @Test
    void it_should_throws_exception_for_update_when_request_is_corrupted() {
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertRepository.findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId())).thenReturn(Optional.of(alertEntity));
        when(alertRepository.save(alertEntity)).thenThrow(IllegalArgumentException.class);
        doNothing().when(alertConverter).fromPutRequest(alertEntity, putAlertRequest);
        assertThrows(IllegalArgumentException.class, () -> alertService.update(putAlertRequest, alertEntity.getId()));

        verify(alertConverter, times(1)).fromPutRequest(alertEntity, putAlertRequest);
        verify(alertRepository, times(1)).save(alertEntity);
        verify(alertRepository, times(1)).findAlertByUserIdAndId(putAlertRequest.getUserId(), alertEntity.getId());
    }

    @Test
    void it_should_delete_alert_successfully_when_everything_is_fine() {
        Alert alertEntity = dataGenerator.nextObject(Alert.class);

        when(alertRepository.findAlertByUserIdAndId(alertEntity.getUserId(), alertEntity.getId())).thenReturn(Optional.of(alertEntity));
        doNothing().when(alertRepository).delete(alertEntity);
        alertService.delete(alertEntity.getUserId(), alertEntity.getId());

        verify(alertRepository, times(1)).findAlertByUserIdAndId(alertEntity.getUserId(), alertEntity.getId());
        verify(alertRepository, times(1)).delete(alertEntity);
    }

    @Test
    void it_should_throws_exception_for_delete_when_alert_not_found() {
        var userId = 1L;
        var alertId = 1L;

        when(alertRepository.findAlertByUserIdAndId(userId, alertId)).thenThrow(AlertNotFoundException.class);
        assertThrows(AlertNotFoundException.class, () -> alertService.delete(userId, alertId));

        verify(alertRepository, times(1)).findAlertByUserIdAndId(userId, alertId);
    }

    @Test
    void it_should_throws_exception_for_delete_when_user_not_found() {
        var userId = 1L;
        var alertId = 1L;

        when(alertRepository.findAlertByUserIdAndId(userId, alertId)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> alertService.delete(userId, alertId));

        verify(alertRepository, times(1)).findAlertByUserIdAndId(userId, alertId);
    }

    @Test
    void it_should_get_alert_successfully_when_everything_is_fine() {
        Alert alertEntity = dataGenerator.nextObject(Alert.class);
        GetAlertResponse getAlertResponse = GetAlertResponse.builder().
                currencyName(alertEntity.getCurrency().getName())
                .status(alertEntity.getStatus())
                .targetPrice(alertEntity.getTargetPrice())
                .createdAt(alertEntity.getCreatedAt())
                .id(alertEntity.getId())
                .build();

        when(alertRepository.findAlertByUserIdAndId(alertEntity.getUserId(), alertEntity.getId())).thenReturn(Optional.of(alertEntity));
        when(alertConverter.fromEntity(alertEntity)).thenReturn(getAlertResponse);
        var response = alertService.getById(alertEntity.getUserId(), alertEntity.getId());

        verify(alertRepository, times(1)).findAlertByUserIdAndId(alertEntity.getUserId(), alertEntity.getId());
        verify(alertConverter, times(1)).fromEntity(alertEntity);
        assertEquals(alertEntity.getId(), response.getId());
        assertEquals(alertEntity.getStatus(), response.getStatus());
        assertEquals(alertEntity.getTargetPrice(), response.getTargetPrice());

    }

    @Test
    void it_should_get_alerts_successfully_when_everything_is_fine() {
        Alert alertEntity = dataGenerator.nextObject(Alert.class);
        GetAlertResponse getAlertResponse = GetAlertResponse.builder().
                currencyName(alertEntity.getCurrency().getName())
                .status(alertEntity.getStatus())
                .targetPrice(alertEntity.getTargetPrice())
                .createdAt(alertEntity.getCreatedAt())
                .id(alertEntity.getId())
                .build();

        when(alertRepository.findAlertsByUserId(alertEntity.getUserId())).thenReturn(List.of(alertEntity));
        when(alertConverter.fromEntity(alertEntity)).thenReturn(getAlertResponse);
        var response = alertService.getAllByUserId(alertEntity.getUserId());

        verify(alertRepository, times(1)).findAlertsByUserId(alertEntity.getUserId());
        verify(alertConverter, times(1)).fromEntity(alertEntity);
        assertEquals(1, response.size());
    }
}
