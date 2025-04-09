package com.example.middleware.controller;


import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;
import com.example.middleware.model.Review;
import com.example.middleware.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    @GetMapping("/reviews")
    public APIResponse getReviews() {
        try {
            return new APIResponse(APIStatus.SUCCESS, objectMapper.writeValueAsString(reviewService.getAllReviews()));
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

    @PostMapping("/review")
    public APIResponse createReview(@RequestBody Review review) {
        try {
           return new APIResponse(
                   APIStatus.SUCCESS,
                   objectMapper.writeValueAsString(
                       reviewService.createReview(
                               review.getUsername(), review.getRating(),
                               review.getComment(), review.getProductVersion(),
                               review.getTimestamp()))
           );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

}
