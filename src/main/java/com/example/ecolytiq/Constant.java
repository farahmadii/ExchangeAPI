package com.example.ecolytiq;


import java.time.format.DateTimeFormatter;


public class Constant {

    // Constant should not be instantiated
    private Constant() {

    }

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");

}
