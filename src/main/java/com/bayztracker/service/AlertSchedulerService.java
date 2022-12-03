package com.bayztracker.service;


import com.bayztracker.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AlertSchedulerService {

    public static Integer PAGE_SIZE = 1000;
    private final AlertService alertService;
    private final CurrencyService currencyService;
    private final NotificationService notificationService;

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}", initialDelayString = "${scheduler.initial-delay}")
    public void checkAlarm() {
        log.info("job triggered.");
        var pagingRequest = PageRequest.of(0, PAGE_SIZE, Sort.by("createdAt").descending());
        var currencies = currencyService.getEnabledCurrencies();

        currencies.forEach(currency -> {
            var alerts = alertService.getAllByStatusAndCurrencyAndTargetPriceLessThanEqual(Status.NEW, currency, pagingRequest);
            log.info(String.format("Processing Alert Count: %d", alerts.size()));
            alerts.parallelStream().forEach(a -> {
                alertService.setStatusTriggered(a);
                notificationService.sendNotification("Target price was reached for alertId: " + a.getId());
            });
        });
    }

}
