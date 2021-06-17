package com.example.ecolytiq.exception;

public class ErrorMessage {

    // ErrorMessage shouldn't be instantiated
    private ErrorMessage() {
    }
    public static final String WRONG_EXTENSION = "File should be ZIP";
    public static final String NO_FILE_PROVIDED = "Provide a zip file";
    public static final String FILE_IS_TOO_LARGE = "File too large!";
    public static final String REQUIRE_CSV_FILE = "Require .csv file";
    public static final String COULD_NOT_OPEN_FILE = "Could not open zip file";
    public static final String COULD_NOT_PARSE_CSV = "Failed to parse CSV file";
    public static final String NO_DATE_FIELD = "Found no Date field";
    public static final String CSV_CONTAINS_EMPTY_FIELD = "CSV contains empty field";
    public static final String FAILED_TO_STORE_DATA = "Failed to store data";
}
