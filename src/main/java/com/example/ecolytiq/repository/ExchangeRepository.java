package com.example.ecolytiq.repository;

import com.example.ecolytiq.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface ExchangeRepository extends JpaRepository<Currency, Long> {

    public Iterable<Currency> findByDate(LocalDate date);
    public Iterable<Currency> findByName(String name);
    public Iterable<Currency> findByDateAndName(LocalDate date, String name);
}
