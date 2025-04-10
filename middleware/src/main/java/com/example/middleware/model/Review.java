package com.example.middleware.model;

import com.example.middleware.dto.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "reviews")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @Min(0)
    @Max(5)
    Integer rating;

    @NotNull
    String username;

    @NotNull
    @Column(name="product_version")
    @JsonProperty("product_version")
    String productVersion;

    @Nullable
    @Column(length = 2000)
    String comment;

    @NotNull
    Timestamp timestamp;

    public Review(String username, Integer rating, @Nullable String comment, String version, Timestamp timestamp) {
        this.username = username;
        this.rating = rating;
        this.productVersion = version;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public Review(ReviewDTO dto) {
        this.rating = dto.getRating();
        this.username = dto.getUsername();
        this.productVersion = dto.getProduct_version();
        this.comment = dto.getComment();
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}