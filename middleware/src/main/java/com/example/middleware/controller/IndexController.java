package com.example.middleware.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.Instant;
import org.springframework.web.bind.annotation.RestController;
import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class IndexController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/")
    public APIResponse home() {
        return new APIResponse(APIStatus.SUCCESS, "Welcome to the Middleware API");
    }

    @GetMapping("/database-health")
    public APIResponse databaseHealth() {
        try {
					// convert instant to local date time
					Instant instant = entityManager.createQuery("SELECT NOW()", Instant.class).getSingleResult();
					LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault());
            return new APIResponse(APIStatus.SUCCESS, "Database is up and running", localDateTime);
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}