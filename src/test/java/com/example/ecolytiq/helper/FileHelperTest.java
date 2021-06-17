package com.example.ecolytiq.helper;

import com.example.ecolytiq.Constant;
import com.example.ecolytiq.model.Currency;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

class FileHelperTest {

    private MultipartFile multipartFile;
    private FileHelper fileHelper = new FileHelper();
    private List<Currency> listOfCurrencies;

//    @Before
    public void setUp() throws IOException {
        multipartFile = new MockMultipartFile("test_files/currencies_test.csv.zip", new FileInputStream(new File("/src/main/resources/currencies_test.csv.zip")));
        listOfCurrencies = List.of(
                new Currency("USD", LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("1.2195")),
                new Currency("JPY", LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("133.38")),
                new Currency("BGN", LocalDate.parse("09 June 2021", Constant.DEFAULT_DATE_FORMATTER), new BigDecimal("1.9558")));
    }



//    @Test
    void unzipAndFetch() {
        List<Currency> testList = fileHelper.unzipAndFetch(multipartFile);
        assertEquals(testList, listOfCurrencies);
    }

}