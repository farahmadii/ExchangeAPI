package com.example.ecolytiq.controller;


import com.example.ecolytiq.Constant;
import com.example.ecolytiq.exception.ErrorMessage;
import com.example.ecolytiq.exception.ValidationException;
import com.example.ecolytiq.model.Currency;
import com.example.ecolytiq.service.ExchangeService;
import com.example.ecolytiq.service.ExchangeServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Log4j2
public class ExchangeControllerTest {

    private ExchangeController exchangeController;

    private ExchangeService exchangeService = mock(ExchangeServiceImpl.class);

    private List<Currency> listOfCurrenciesTest;

    @Before
    public void setUp() throws IOException {
        exchangeController = new ExchangeController(exchangeService);
        listOfCurrenciesTest = List.of(
                new Currency("USD", LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("1.2195")),
                new Currency("JPY", LocalDate.parse("11 August 2010", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("133.38")),
                new Currency("BGN", LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("1.9558")));

    }

    @Test(expected = ValidationException.class)
    public void fetchData(){
        Mockito.doThrow(new ValidationException(ErrorMessage.COULD_NOT_OPEN_FILE)).when(exchangeService).fetchData(any());
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getContentType()).thenReturn("application/zip");
        exchangeController.fetchExchangeData(multipartFile);
    }

    @Test
    public void findAll_happy_path(){
        when(exchangeService.findAll()).thenReturn(listOfCurrenciesTest);
        ResponseEntity<Iterable<Currency>> response = exchangeController.findAll();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(listOfCurrenciesTest, response.getBody());
    }

    @Test
    public void findByDate_happy_path(){
        LocalDate testDate = LocalDate.parse("11 August 2010", Constant.DEFAULT_DATE_FORMATTER);
        List<Currency> expectedResponse = List.of(listOfCurrenciesTest.get(1));
        when(exchangeService.findByDate(testDate)).thenReturn(expectedResponse);
        ResponseEntity<Iterable<Currency>> response = exchangeController.find(testDate, null);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void findByDateAndCurrency_happy_path(){
        LocalDate testDate = LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER);
        String currencyName = "BGN";
        List<Currency> expectedResponse = List.of(listOfCurrenciesTest.get(2));
        when(exchangeService.findByDateAndName(testDate, currencyName)).thenReturn(expectedResponse);
        ResponseEntity<Iterable<Currency>> response = exchangeController.find(testDate, currencyName);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void findByCurrency_happy_path(){
        String currencyName = "USD";
        List<Currency> expectedResponse = List.of(listOfCurrenciesTest.get(0));
        when(exchangeService.findByName(currencyName)).thenReturn(expectedResponse);
        ResponseEntity<Iterable<Currency>> response = exchangeController.find(null, currencyName);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResponse, response.getBody());
    }

}
