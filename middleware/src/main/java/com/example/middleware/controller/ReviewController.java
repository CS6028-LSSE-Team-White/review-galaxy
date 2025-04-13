package com.example.middleware.controller;


import com.example.middleware.dto.ReviewDTO;
import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;
import com.example.middleware.model.Review;
import com.example.middleware.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    @GetMapping("/reviews")
    public APIResponse getReviews() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(reviewService.getAllReviews())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/review/{id}")
    public APIResponse getReviewById(@NotNull @PathVariable Long id) {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(reviewService.getReviewById(id))
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/reviews/version/{version}")
    public APIResponse getReviewsByProductVersion(@NotNull @PathVariable String version) {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(reviewService.getReviewsByProductVersion(version))
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @PostMapping("/review")
    public APIResponse createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
           return new APIResponse(
                   APIStatus.SUCCESS,
                   objectMapper.writeValueAsString(
                       reviewService.createReview(reviewDTO))
           );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

}
