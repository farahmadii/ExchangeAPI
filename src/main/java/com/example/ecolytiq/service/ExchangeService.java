package com.example.ecolytiq.service;

import com.example.ecolytiq.exception.ValidationException;
import com.example.ecolytiq.model.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface ExchangeService {

    public Iterable<Currency> findAll();
    public void fetchData(MultipartFile file) throws ValidationException;
    public Iterable<Currency> findByDate(LocalDate date);
    public Iterable<Currency> findByName(String name);
    public Iterable<Currency> findByDateAndName(LocalDate date, String name);
}
