package com.bayztracker.repository;

import com.bayztracker.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByEnabledIsTrue();

    @Override
    Optional<Currency> findById(Long id);
}
