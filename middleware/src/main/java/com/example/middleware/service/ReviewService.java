package com.example.middleware.service;

import com.example.middleware.dto.ReviewDTO;
import com.example.middleware.model.Review;
import com.example.middleware.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Review createReview(ReviewDTO dto) {
        Review review = new Review(dto);
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

    public Iterable<Review> getReviewsByProductVersion(String version) {
        return reviewRepository.getAllByProductVersion(version);
    }

    public ReviewDTO toDTO(Review review) {
       return new ReviewDTO(review.getRating(), review.getUsername(), review.getProductVersion(), review.getComment());
    }

    public Review toReview(ReviewDTO dto) {
        return new Review(dto);
    }

    public void createReviewsBulk(List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

}
