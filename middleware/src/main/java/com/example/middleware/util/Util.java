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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Util {

    @Getter
    private static List<Review> zoomReviews = new ArrayList<>();
    @Getter
    private static List<Review> firefoxReviews= new ArrayList<>();
    @Getter
    private static List<Review> webexReviews = new ArrayList<>();



    @Getter
    private static List<Feature> zoomFeatures = new ArrayList<>();
    @Getter
    private static List<Feature> firefoxFeatures = new ArrayList<>();
    @Getter
    private static List<Feature> webexFeatures = new ArrayList<>();

    @Getter
    @Setter
    private static boolean loaded = false;

    public static List<String[]> readCSVLines(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }


    private static List<Review> retrieveZoomReviews() throws IOException, CsvException {
        if(isLoaded()) {
            Util.zoomReviews = new ArrayList<>();
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

    private static List<Review> retrieveFirefoxReviews() throws IOException, CsvException {
        if(isLoaded()) {
            Util.firefoxReviews = new ArrayList<>();
        }
        List<Review> retReviews = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String csvPath = currentPath + File.separator + "firefox-reviews.csv";

        List<String[]> data = Util.readCSVLines(csvPath);
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = (long)i;
            String username = row[2];
            String comment = row[3];
            Integer score = Integer.parseInt(row[4]);
            String version = row[8];
            Timestamp timestamp;
            try {
                timestamp = Timestamp.valueOf(row[7]);
            } catch (Exception e) {
                timestamp = null;
            }
            Review review = new Review(id, username, score, comment, version, timestamp);
            retReviews.add(review);
        }
        return retReviews;
    }

    private static List<Review> retrieveWebexReviews() throws IOException, CsvException {
        if(isLoaded()) {
            Util.zoomReviews = new ArrayList<>();
        }
        List<Review> retReviews = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String csvPath = currentPath + File.separator + "webex-reviews.csv";

        List<String[]> data = Util.readCSVLines(csvPath);
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = (long)i;
            String username = row[1];
            String comment = row[3];
            Integer score = Integer.parseInt(row[4]);
            String version = row[6];
            String r7 = row[7];
            Timestamp timestamp;
            try {
                timestamp = Timestamp.valueOf(row[7]);
            } catch (Exception e) {
                timestamp = null;
            }
            Review review = new Review(id, username, score, comment, version, timestamp);
            retReviews.add(review);
        }
        return retReviews;
    }

    public static List<Feature> retrieveZoomFeatures() throws IOException, CsvException {
        if(isLoaded()) {
            Util.zoomFeatures = new ArrayList<>();
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

    public static List<Feature> retrieveFirefoxFeatures() throws IOException, CsvException {
        if(isLoaded()) {
            Util.firefoxFeatures = new ArrayList<>();
        }
        List<Feature> retFeats = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String path = currentPath + File.separator + "firefox-features.csv";
        List<String[]> data = Util.readCSVLines(path);

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = (long) i;
            String feature = row[0];
            java.sql.Date releaseDate;
            // one row has no release date
            // tried to look at the KB on zoom's site but couldn't find anything
            // so I just set it to null here (for this row)
            if(row[1] == null || row[1].isEmpty()) {
                releaseDate = null;
            } else {
                try {
                    releaseDate = java.sql.Date.valueOf(row[1]);
                } catch (Exception e) {
                    releaseDate = null;
                }
            }
            Feature feat = new Feature(id, feature, null, releaseDate, null);
            retFeats.add(feat);
        }
        return retFeats;
    }

    public static List<Feature> retrieveWebexFeatures() throws IOException, CsvException {
        if(isLoaded()) {
            Util.webexFeatures = new ArrayList<>();
        }
        List<Feature> retFeats = new ArrayList<>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String path = currentPath + File.separator + "webex-features.csv";
        List<String[]> data = Util.readCSVLines(path);

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            Long id = (long) (i-1);
            String feature = row[0];
            String description = row[0];
            java.sql.Date releaseDate;
            // one row has no release date
            // tried to look at the KB on zoom's site but couldn't find anything
            // so I just set it to null here (for this row)
            if(row[1] == null || row[1].isEmpty()) {
                releaseDate = null;
            } else {
                try {
                    releaseDate = java.sql.Date.valueOf(row[1]);
                } catch (Exception e) {
                    releaseDate = null;
                }
            }
            String version = row[2].replace("version ", "").trim();
            version = version.replaceAll(" \\(.*\\)", "");

            Feature feat = new Feature(id, feature, description, releaseDate, version);
            retFeats.add(feat);
        }
        return retFeats;
    }

    public static Feature findZoomFeatureById(Long id) {
        for (Feature feature : zoomFeatures) {
            if (feature.getId().equals(id)) {
                return feature;
            }
        }
        return null;
    }

    public static Review findZoomReviewById(Long id) {
        for (Review review : zoomReviews) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

    public static List<Review> findZoomReviewsByProductVersion(String version) {
        List<Review> ret = new ArrayList<>();
        for (Review review : zoomReviews) {
            if (review.getProductVersion().equals(version)) {
                ret.add(review);
            }
        }
        return ret;
    }

    public static void load() throws IOException, CsvException {
        // Simple multi-threading to load reviews and zoomFeatures
        // in parallel
        // This is not the best way to do this, but it works
        Thread thread1 = new Thread(() -> {
            try {
                Util.zoomReviews = retrieveZoomReviews();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Util.zoomFeatures = retrieveZoomFeatures();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                Util.firefoxReviews = retrieveFirefoxReviews();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                Util.firefoxFeatures = retrieveFirefoxFeatures();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread5 = new Thread(() -> {
            try {
                Util.webexReviews = retrieveWebexReviews();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread6 = new Thread(() -> {
            try {
                Util.webexFeatures = retrieveWebexFeatures();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loaded = true;
    }
}