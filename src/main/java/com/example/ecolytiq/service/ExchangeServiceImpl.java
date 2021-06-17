package com.example.ecolytiq.service;


import com.example.ecolytiq.exception.ErrorMessage;
import com.example.ecolytiq.exception.ValidationException;
import com.example.ecolytiq.helper.FileHelper;
import com.example.ecolytiq.model.Currency;
import com.example.ecolytiq.repository.ExchangeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final FileHelper fileHelper;

    @Autowired
    public ExchangeServiceImpl(ExchangeRepository exchangeRepository, FileHelper fileHelper) {
        this.exchangeRepository = exchangeRepository;
        this.fileHelper = fileHelper;
    }

    @Override
    public Iterable<Currency> findAll() {
        return exchangeRepository.findAll();
    }


    @Override
    public Iterable<Currency> findByDate(LocalDate date) {
        return exchangeRepository.findByDate(date);
    }

    @Override
    public Iterable<Currency> findByName(String name) {
        return exchangeRepository.findByName(name);
    }

    @Override
    public Iterable<Currency> findByDateAndName(LocalDate date, String name) {
        return exchangeRepository.findByDateAndName(date, name);
    }

    @Override
    public void fetchData(MultipartFile file) throws ValidationException {
        List<Currency> listOfCurrencies = fileHelper.unzipAndFetch(file);
        try {
            exchangeRepository.saveAll(listOfCurrencies);
        } catch (DataAccessException e){
            throw new ValidationException(ErrorMessage.FAILED_TO_STORE_DATA);
        }
    }

}