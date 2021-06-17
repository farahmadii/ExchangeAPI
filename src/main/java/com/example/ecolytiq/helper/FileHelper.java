package com.example.ecolytiq.helper;

import com.example.ecolytiq.Constant;
import com.example.ecolytiq.exception.ErrorMessage;
import com.example.ecolytiq.model.Currency;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecolytiq.exception.ValidationException;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log4j2
public class FileHelper {
    public static String TYPE_ZIP = "application/zip";
    public static String TYPE_CSV = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE_CSV.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


    public static boolean hasZipFormat(MultipartFile file) {

        if (!TYPE_ZIP.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public List<Currency> unzipAndFetch(MultipartFile file) throws ValidationException {

        try {
            ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                throw new ValidationException(ErrorMessage.NO_FILE_PROVIDED);
            }
            if (!zipEntry.getName().toLowerCase().endsWith(".csv")) {
                throw new ValidationException(ErrorMessage.REQUIRE_CSV_FILE);
            }
            return csvToCurrencies(zipInputStream);

        } catch (IOException e) {
            throw new ValidationException(ErrorMessage.COULD_NOT_OPEN_FILE);
        }

    }

    private List<Currency> csvToCurrencies(InputStream inputStream) throws ValidationException {

        List<Currency> listOfCurrencies = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            List<String> names = new ArrayList<>();
            int dateIdx = -1;
            // reading csv header
            if (records.iterator().hasNext()) {
                CSVRecord firstRec = records.iterator().next();
                for (int i = 0; i < firstRec.size(); i++) {
                    if (firstRec.get(i).equals("Date")) {
                        dateIdx = i;
                    }
                    if (firstRec.get(i) == null || firstRec.get(i).trim().isEmpty()){
                        throw new ValidationException(ErrorMessage.CSV_CONTAINS_EMPTY_FIELD);
                    }
                    names.add(firstRec.get(i));
                }
            }
            if (dateIdx == -1) {
                throw new ValidationException(ErrorMessage.NO_DATE_FIELD);
            }

            for (CSVRecord record : records) {
                log.debug("Record #: {}", record.getRecordNumber());
                System.out.println("********************** " + record.getRecordNumber() + " ***********************");
                for (int i = 1; i < record.size(); i++) {
                    if (record.get(i) == null || record.get(i).trim().isEmpty()){
                        throw new ValidationException(ErrorMessage.CSV_CONTAINS_EMPTY_FIELD);
                    }
                    Currency newCurrency = new Currency();
                    log.debug("Currency date of rate: {}", record.get(dateIdx));
                    newCurrency.setDate(LocalDate.parse(record.get(dateIdx).trim(), Constant.DEFAULT_DATE_FORMATTER));
                    log.debug("Currency name: {}", names.get(i));
                    newCurrency.setName(names.get(i).trim());
                    log.debug("Currency rate: {}", record.get(i));
                    System.out.println("********************** date: " + record.get(0) + " name: " + names.get(i) + " rate: " + record.get(i) + " **********************");
                    newCurrency.setRate(new BigDecimal(Double.parseDouble(record.get(i).trim())));
                    listOfCurrencies.add(newCurrency);
                }
            }
            return listOfCurrencies;
        } catch (IOException e) {
            throw new ValidationException(ErrorMessage.COULD_NOT_PARSE_CSV);
        }

    }
}
