package com.example.middleware.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.Instant;
import org.springframework.web.bind.annotation.RestController;
import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class IndexController {


    @GetMapping("/api/middleware-health")
    public APIResponse databaseHealth() {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            return new APIResponse(APIStatus.SUCCESS, "Middleware is up and running", localDateTime);
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}