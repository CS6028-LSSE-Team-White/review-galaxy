package com.example.middleware.repo;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.middleware.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> getReviewById(@NotNull Long id);
    Iterable<Review> getAllByUsername(@NotNull String username);
    Iterable<Review> getAllByProductVersion(String productVersion);
}