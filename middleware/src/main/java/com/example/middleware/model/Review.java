package com.example.middleware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Data
public class Review {
    private Long id;
    private Integer rating;
    private String username;
    @JsonProperty("release_version")
    private String productVersion;
    private String comment;
    private Timestamp timestamp;

    public Review(Long id, String username, Integer rating, @Nullable String comment, String version, Timestamp timestamp) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.productVersion = version;
        this.comment = comment;
        this.timestamp = timestamp;
    }
}