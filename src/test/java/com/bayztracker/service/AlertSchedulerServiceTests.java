package com.bayztracker.service;


import com.bayztracker.base.TestBase;
import com.bayztracker.builder.TestDataBuilder;
import com.bayztracker.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.bayztracker.service.AlertSchedulerService.PAGE_SIZE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertSchedulerServiceTests extends TestBase {

    @InjectMocks
    AlertSchedulerService alertSchedulerService;

    @Mock
    NotificationService notificationService;

    @Mock
    AlertService alertService;

    @Mock
    CurrencyService currencyService;


    @Test
    void it_should_set_triggerred_successfully_when_target_prices_have_reached() {
        float currentPrice = 10F;
        float targetReachedPrice = 9F;
        var currency = TestDataBuilder.createValidCurrency(currentPrice);
        var currencies = List.of(currency);
        var pagingRequest = PageRequest.of(0, PAGE_SIZE, Sort.by("createdAt").descending());
        var firstAlert = TestDataBuilder.createValidAlert(targetReachedPrice, currency);
        var secondAlert = TestDataBuilder.createValidAlert(targetReachedPrice, currency);

        when(currencyService.getEnabledCurrencies()).thenReturn(currencies);
        when(alertService.getAllByStatusAndCurrencyAndTargetPriceLessThanEqual(Status.NEW, currency, pagingRequest)).thenReturn(List.of(firstAlert, secondAlert));
        doNothing().when(alertService).setStatusTriggered(any());
        doNothing().when(notificationService).sendNotification(any());
        alertSchedulerService.checkAlarm();

        verify(alertService, times(2)).setStatusTriggered(any());
        verify(alertService, times(1)).getAllByStatusAndCurrencyAndTargetPriceLessThanEqual(Status.NEW, currency, pagingRequest);
        verify(notificationService, times(1)).sendNotification("Target price was reached for alertId: " + firstAlert.getId());
        verify(notificationService, times(1)).sendNotification("Target price was reached for alertId: " + secondAlert.getId());
    }

    @Test
    void it_should_done_nothing_when_enabled_currency_not_found() {
        when(currencyService.getEnabledCurrencies()).thenReturn(List.of());
        alertSchedulerService.checkAlarm();

        verify(alertService, times(0)).getAllByStatusAndCurrencyAndTargetPriceLessThanEqual(any(), any(), any());
        verify(alertService, times(0)).setStatusTriggered(any());
        verify(notificationService, times(0)).sendNotification(any());
        verify(notificationService, times(0)).sendNotification(any());
    }
}
