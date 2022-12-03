package com.bayztracker.builder;

import com.bayztracker.domain.Alert;
import com.bayztracker.domain.Currency;
import com.bayztracker.enums.Status;

import java.util.Date;
import java.util.Random;

public class TestDataBuilder {

    public static Currency createValidCurrency(Float currentPrice) {
        var currency = new Currency();
        currency.setName("Dollar");
        currency.setId(1L);
        currency.setCurrentPrice(currentPrice);
        currency.setEnabled(true);
        return currency;
    }

    public static Alert createValidAlert(Float targetPrice, Currency currency) {
        var alert = new Alert();
        alert.setUserId(new Random().nextLong());
        alert.setId(new Random().nextInt());
        alert.setStatus(Status.NEW);
        alert.setCurrency(currency);
        alert.setTargetPrice(targetPrice);
        alert.setCreatedAt(new Date());
        return alert;
    }
}
