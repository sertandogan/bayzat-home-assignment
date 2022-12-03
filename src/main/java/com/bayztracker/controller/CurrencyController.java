package com.bayztracker.controller;

import com.bayztracker.domain.Currency;
import com.bayztracker.model.response.GetCurrencyResponse;
import com.bayztracker.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<GetCurrencyResponse>> getAll() {
        List<Currency> currencies = currencyService.getEnabledCurrencies();
        List<GetCurrencyResponse> payloads = currencies.stream().map(GetCurrencyResponse::create).collect(Collectors.toList());
        return ResponseEntity.ok(payloads);
    }
}
