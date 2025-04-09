package com.example.middleware.service;

import com.example.middleware.model.Review;
import com.example.middleware.repo.ReviewRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public Review createReview(String username, Integer rating, String comment, String version, Timestamp timestamp) {
            Review review = new Review(username, rating, comment, version, timestamp);
            return reviewRepository.save(review);
    }

    public Iterable<Review> getReviewsByUsername(String username) {
        return reviewRepository.getAllByUsername(username);
    }

    public Iterable<Review> getAllReviews() throws IllegalArgumentException {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow();
    }
}
