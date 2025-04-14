package com.example.middleware.controller;


import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;
import com.example.middleware.model.Review;
import com.example.middleware.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ObjectMapper objectMapper;

    @GetMapping("/reviews")
    public APIResponse getReviews() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getReviews())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/review/{id}")
    public APIResponse getReviewById(@NotNull @PathVariable Long id) {
        try {
            Review review = Util.findReviewById(id);
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.findReviewById(id))
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/reviews/version/{version}")
    public APIResponse getReviewsByProductVersion(@NotNull @PathVariable String version) {
        try {
            List<Review> revs = Util.findReviewsByProductVersion(version);
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(revs)
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}
