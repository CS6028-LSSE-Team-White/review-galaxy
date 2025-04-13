package com.example.middleware;

import com.example.middleware.dto.FeatureDTO;
import com.example.middleware.model.Review;
import com.example.middleware.service.FeatureService;
import com.example.middleware.service.ReviewService;
import com.example.middleware.util.Util;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class MiddlewareApplication {
    private final ReviewService reviewService;
    private final FeatureService featureService;

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleData() throws IOException, CsvException {
        loadFeatures();
        loadReviews();
    }

    private void loadReviews() throws IOException, CsvException {
        String currentPath = Paths.get("").toAbsolutePath().toString();
        String csvPath = currentPath + File.separator + "zoom-reviews.csv";

        List<String[]> data = Util.readSampleReviews(csvPath);

        List<Review> chunk = new ArrayList<>();
        int chunkSize = 1000;

        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);

            try {
                String username = row[2];
                String comment = row[3];
                Integer score = Integer.parseInt(row[4]);
                String version = row[8];
                int lastDot = version.lastIndexOf('.');

                version = version.substring(0, lastDot);

                Timestamp timestamp = Timestamp.valueOf(row[7]);

                Review review = new Review(username, score, comment, version, timestamp);
                chunk.add(review);

                if (chunk.size() >= chunkSize) {
                    reviewService.createReviewsBulk(chunk);
                    chunk.clear();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!chunk.isEmpty()) {
            reviewService.createReviewsBulk(chunk);
        }
    }

    private void loadFeatures() {
      try {
          String currentPath = Paths.get("").toAbsolutePath().toString();
          String path = currentPath + File.separator + "zoom-features.csv";
  
          List<String[]> features = Util.readZoomFeatures(path);
  
          for (int i = 1; i < features.size(); i++) {
              String[] row = features.get(i);
  
              String feature = row[0];
              String description = row[1];
              java.sql.Date releaseDate = java.sql.Date.valueOf(row[2]);
              String version = row[3].replace("version ", "").trim();
              version = version.replaceAll(" \\(.*\\)", "");
  
              featureService.createFeature(new FeatureDTO(feature, description, releaseDate, version));
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  
}
  
