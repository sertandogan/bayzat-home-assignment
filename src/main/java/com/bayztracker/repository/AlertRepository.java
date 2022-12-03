package com.bayztracker.repository;

import com.bayztracker.domain.Alert;
import com.bayztracker.domain.Currency;
import com.bayztracker.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends CrudRepository<Alert, Long> {
    Optional<Alert> findAlertByUserIdAndId(Long userId, Long id);

    List<Alert> findAlertsByUserId(Long userId);

    List<Alert> findAlertsByStatusAndCurrencyAndTargetPriceLessThanEqual(Status status, Currency currency, Float targetPrice, Pageable pageable);
}
