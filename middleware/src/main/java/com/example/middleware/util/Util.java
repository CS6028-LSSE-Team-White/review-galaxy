package com.example.middleware.util;

import com.example.middleware.model.Feature;
import com.example.middleware.model.Review;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Util {

    @Getter
    private static List<Review> reviews = new ArrayList<>();

    @Getter
    private static List<Feature> features = new ArrayList<>();

    @Getter
    @Setter
    private static boolean loaded = false;

    public static List<String[]> readCSVLines(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }


    private static List<Review> retrieveReviews() throws IOException, CsvException {
        if(isLoaded()) {
            Util.reviews = new ArrayList<>();
        }
        List<Review> retReviews = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String csvPath = currentPath + File.separator + "zoom-reviews.csv";

        List<String[]> data = Util.readCSVLines(csvPath);
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = Long.parseLong(row[0]);
            String username = row[2];
            String comment = row[3];
            Integer score = Integer.parseInt(row[4]);
            String version = row[8];
            int lastDot = version.lastIndexOf('.');
            version = version.substring(0, lastDot);
            Timestamp timestamp = Timestamp.valueOf(row[7]);
            Review review = new Review(id, username, score, comment, version, timestamp);
            retReviews.add(review);
        }
        return retReviews;
    }

    public static List<Feature> retrieveFeatures() throws IOException, CsvException {
        if(isLoaded()) {
            Util.features = new ArrayList<>();
        }
        List<Feature> retFeats = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String path = currentPath + File.separator + "zoom-features.csv";
        List<String[]> data = Util.readCSVLines(path);

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = (long) (i-1);
            String feature = row[0];
            String description = row[1];
            java.sql.Date releaseDate;
            // one row has no release date
            // tried to look at the KB on zoom's site but couldn't find anything
            // so I just set it to null here (for this row)
            if(row[2] == null || row[2].isEmpty()) {
                releaseDate = null;
            } else {
                releaseDate = java.sql.Date.valueOf(row[2]);
            }
            String version = row[3].replace("version ", "").trim();
            version = version.replaceAll(" \\(.*\\)", "");


            Feature feat = new Feature(id, feature, description, releaseDate, version);
            retFeats.add(feat);
        }
        return retFeats;
    }

    public static Feature findFeatureById(Long id) {
        for (Feature feature : features) {
            if (feature.getId().equals(id)) {
                return feature;
            }
        }
        return null;
    }

    public static Review findReviewById(Long id) {
        for (Review review : reviews) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

    public static List<Review> findReviewsByProductVersion(String version) {
        List<Review> ret = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getProductVersion().equals(version)) {
                ret.add(review);
            }
        }
        return ret;
    }

    public static void load() throws IOException, CsvException {
        // Simple multi-threading to load reviews and features
        // in parallel
        // This is not the best way to do this, but it works
        Thread thread1 = new Thread(() -> {
            try {
                Util.reviews = retrieveReviews();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Util.features = retrieveFeatures();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loaded = true;
    }
}