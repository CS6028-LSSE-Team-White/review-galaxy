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

    @GetMapping("/api/reviews")
    public APIResponse getReviews() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getZoomReviews())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/firefox/reviews")
    public APIResponse getFirefoxReviews() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getFirefoxReviews())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/webex/reviews")
    public APIResponse getWebexReviews() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getWebexReviews())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }



    @GetMapping("/api/review/{id}")
    public APIResponse getReviewById(@NotNull @PathVariable Long id) {
        try {
            Review review = Util.findZoomReviewById(id);
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.findZoomReviewById(id))
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/reviews/version/{version}")
    public APIResponse getReviewsByProductVersion(@NotNull @PathVariable String version) {
        try {
            List<Review> revs = Util.findZoomReviewsByProductVersion(version);
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(revs)
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}
