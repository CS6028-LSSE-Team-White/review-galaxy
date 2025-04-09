package com.example.middleware;

import com.example.middleware.service.ReviewService;
import com.example.middleware.util.Util;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class MiddlewareApplication {
  private final ReviewService reviewService;

  public static void main(String[] args) {
    SpringApplication.run(MiddlewareApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  private void loadSampleData() throws IOException, CsvException {
    // load csv
    String currentPath = Paths.get("").toAbsolutePath().toString();
    String csvPath = currentPath + File.separator + "zoom-reviews.csv";
    List<String[]> data = Util.readSampleReviews(csvPath);
    for(int i = 1; i < data.size(); i++) {
      String[] row = data.get(i);
      String username = row[1];
      String comment = row[3];
      Integer score = Integer.parseInt(row[4]);
      String version = row[6];
      Timestamp timestamp = Timestamp.valueOf(row[7]);
      reviewService.createReview(username, score, comment, version, timestamp);
    }
  }

}
