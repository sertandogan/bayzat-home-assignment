package com.bayztracker.converter;

import com.bayztracker.base.TestBase;
import com.bayztracker.domain.Alert;
import com.bayztracker.domain.Currency;
import com.bayztracker.enums.Status;
import com.bayztracker.exceptions.*;
import com.bayztracker.model.request.CreateAlertRequest;
import com.bayztracker.model.request.PutAlertRequest;
import com.bayztracker.service.CurrencyService;
import com.bayztracker.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertConverterTests extends TestBase {
    @InjectMocks
    AlertConverter alertConverter;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private UserService userService;

    @Test
    void it_should_convert_from_create_successfully_when_everything_is_fine() {
        Currency currency = dataGenerator.nextObject(Currency.class);
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);
        createAlertRequest.setCurrencyId(currency.getId());

        when(currencyService.getById(createAlertRequest.getCurrencyId())).thenReturn(currency);
        when(userService.existById(createAlertRequest.getUserId())).thenReturn(true);
        var newEntity = alertConverter.fromCreateRequest(createAlertRequest);

        verify(currencyService, times(1)).getById(createAlertRequest.getCurrencyId());
        verify(userService, times(1)).existById(createAlertRequest.getUserId());
        assertEquals(newEntity.getTargetPrice(), createAlertRequest.getTargetPrice());
        assertEquals(newEntity.getCurrency().getId(), createAlertRequest.getCurrencyId());
        assertEquals(newEntity.getStatus(), Status.NEW);

    }

    @Test
    void it_should_throws_exception_for_create_when_currency_not_found() {
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);

        when(currencyService.getById(createAlertRequest.getCurrencyId())).thenThrow(CurrencyNotFoundException.class);
        assertThrows(CurrencyNotFoundException.class, () -> alertConverter.fromCreateRequest(createAlertRequest));

        verify(currencyService, times(1)).getById(createAlertRequest.getCurrencyId());

    }

    @Test
    void it_should_throws_exception_for_create_when_user_not_found() {
        Currency currency = dataGenerator.nextObject(Currency.class);
        CreateAlertRequest createAlertRequest = dataGenerator.nextObject(CreateAlertRequest.class);
        createAlertRequest.setCurrencyId(currency.getId());

        when(currencyService.getById(createAlertRequest.getCurrencyId())).thenReturn(currency);
        when(userService.existById(createAlertRequest.getUserId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> alertConverter.fromCreateRequest(createAlertRequest));

        verify(currencyService, times(1)).getById(createAlertRequest.getCurrencyId());
        verify(userService, times(1)).existById(createAlertRequest.getUserId());

    }

    @Test
    void it_should_convert_from_put_successfully_when_new_status_is_cancelled() {
        Currency currency = dataGenerator.nextObject(Currency.class);
        Alert alert = dataGenerator.nextObject(Alert.class);
        alert.setStatus(Status.NEW);
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        putAlertRequest.setStatus(Status.CANCELLED);
        putAlertRequest.setCurrencyId(currency.getId());

        when(currencyService.getById(putAlertRequest.getCurrencyId())).thenReturn(currency);
        alertConverter.fromPutRequest(alert, putAlertRequest);

        verify(currencyService, times(1)).getById(putAlertRequest.getCurrencyId());

        assertEquals(alert.getStatus(), putAlertRequest.getStatus());
        assertEquals(alert.getTargetPrice(), putAlertRequest.getTargetPrice());
    }

    @Test
    void it_should_convert_from_put_successfully_when_new_status_is_acked() {
        Currency currency = dataGenerator.nextObject(Currency.class);
        Alert alert = dataGenerator.nextObject(Alert.class);
        alert.setStatus(Status.TRIGGERED);
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        putAlertRequest.setStatus(Status.ACKED);
        putAlertRequest.setCurrencyId(currency.getId());

        when(currencyService.getById(putAlertRequest.getCurrencyId())).thenReturn(currency);
        alertConverter.fromPutRequest(alert, putAlertRequest);

        verify(currencyService, times(1)).getById(putAlertRequest.getCurrencyId());

        assertEquals(alert.getStatus(), putAlertRequest.getStatus());
        assertEquals(alert.getTargetPrice(), putAlertRequest.getTargetPrice());
    }

    @Test
    void it_should_throws_exception_when_new_status_is_acked_and_old_status_is_new() {
        Alert alert = dataGenerator.nextObject(Alert.class);
        alert.setStatus(Status.NEW);
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        putAlertRequest.setStatus(Status.ACKED);

        assertThrows(OnlyTriggeredAlertsCanBeAckedException.class, () -> alertConverter.fromPutRequest(alert, putAlertRequest));

    }

    @Test
    void it_should_throws_exception_when_new_status_is_cancelled_and_old_status_is_triggered() {
        Alert alert = dataGenerator.nextObject(Alert.class);
        alert.setStatus(Status.TRIGGERED);
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        putAlertRequest.setStatus(Status.CANCELLED);

        assertThrows(TriggeredAlertCannotBeCancelledException.class, () -> alertConverter.fromPutRequest(alert, putAlertRequest));

    }

    @Test
    void it_should_throws_exception_for_put_when_status_is_new() {
        Alert alert = dataGenerator.nextObject(Alert.class);
        alert.setStatus(Status.CANCELLED);
        PutAlertRequest putAlertRequest = dataGenerator.nextObject(PutAlertRequest.class);
        putAlertRequest.setStatus(Status.NEW);

        assertThrows(AlertCannotSetAsNewException.class, () -> alertConverter.fromPutRequest(alert, putAlertRequest));

    }

    @Test
    void it_should_convert_successfully_when_everything_is_fine() {
        Alert alert = dataGenerator.nextObject(Alert.class);
        var response = alertConverter.fromEntity(alert);

        assertEquals(alert.getId(), response.getId());
        assertEquals(alert.getTargetPrice(), response.getTargetPrice());
        assertEquals(alert.getCurrency().getName(), response.getCurrencyName());
        assertEquals(alert.getStatus(), response.getStatus());
        assertEquals(alert.getCreatedAt(), response.getCreatedAt());
    }
}
