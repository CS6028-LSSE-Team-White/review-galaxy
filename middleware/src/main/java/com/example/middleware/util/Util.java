package com.example.middleware.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Util {

    public static List<String[]> readSampleReviews(String filePath) throws IOException, CsvException {
        FileReader reader = new FileReader(filePath);
        CSVReader csvReader = new CSVReader(reader);
        return csvReader.readAll();
    }
}