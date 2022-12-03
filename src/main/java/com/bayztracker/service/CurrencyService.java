package com.bayztracker.service;

import com.bayztracker.domain.Currency;
import com.bayztracker.exceptions.CurrencyNotFoundException;
import com.bayztracker.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Transactional(readOnly = true)
    public List<Currency> getEnabledCurrencies() {
        return currencyRepository.findAllByEnabledIsTrue();
    }

    @Transactional(readOnly = true)
    public Currency getById(Long currencyId) {
        return currencyRepository.findById(currencyId).orElseThrow(CurrencyNotFoundException::new);
    }
}
