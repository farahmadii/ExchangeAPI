package com.example.ecolytiq.controller;


import com.example.ecolytiq.exception.ErrorMessage;
import com.example.ecolytiq.exception.ValidationException;
import com.example.ecolytiq.helper.FileHelper;
import com.example.ecolytiq.model.Currency;
import com.example.ecolytiq.service.ExchangeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Log4j2
@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;


    @Autowired
    public ExchangeController(ExchangeService exchangeService){
        this.exchangeService = exchangeService;
    }


    @GetMapping(value = "/findall", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Currency>> findAll() {
        try {
            return new ResponseEntity<Iterable<Currency>>(exchangeService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Iterable<Currency>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchExchangeData(@RequestParam("file") MultipartFile file){
        if (!FileHelper.hasZipFormat(file)) {
            throw new ValidationException(ErrorMessage.WRONG_EXTENSION);
        }
        exchangeService.fetchData(file);
        String message = "Fetched the data successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @GetMapping(value = "/find", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Currency>> find(@RequestParam(required = false) @DateTimeFormat(pattern = "dd MMMM yyyy") LocalDate date,
                                                         @RequestParam(required = false) String currency) {

        boolean isFindByCurrency = (currency != null && !currency.isEmpty());
        boolean isFindByDate = date != null;
        try {
            if (isFindByDate && isFindByCurrency) {
                log.debug("*************isFindByDate && isFindByCurrency*************");
                return new ResponseEntity<Iterable<Currency>>(exchangeService.findByDateAndName(date, currency), HttpStatus.OK);
            } else if (isFindByDate) {
                log.debug("*************isFindByDate************* {}", date);
                return new ResponseEntity<Iterable<Currency>>(exchangeService.findByDate(date), HttpStatus.OK);
            } else if (isFindByCurrency) {
                log.debug("*************isFindByCurrency************* {}", currency);
                return new ResponseEntity<Iterable<Currency>>(exchangeService.findByName(currency), HttpStatus.OK);
            } else {
                return new ResponseEntity<Iterable<Currency>>(exchangeService.findAll(), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<Iterable<Currency>>(HttpStatus.BAD_REQUEST);
        }
    }

}
