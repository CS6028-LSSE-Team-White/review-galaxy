package com.example.middleware;

import com.example.middleware.util.Util;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


import java.io.IOException;

@RequiredArgsConstructor
@SpringBootApplication
public class MiddlewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadUtil() throws IOException, CsvException {
        try {
            System.out.println("Loading util");
            if (!Util.isLoaded()) {
                Util.load();
                Util.setLoaded(true);
            }
            System.out.println("Util Loaded");
        } catch (Exception e) {
            System.err.println("Failed to load util");
            e.printStackTrace();
        }
    }
}
  
