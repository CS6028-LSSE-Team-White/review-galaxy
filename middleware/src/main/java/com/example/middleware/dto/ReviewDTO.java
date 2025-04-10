package com.example.middleware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReviewDTO {
    Integer rating;
    String username;
    String product_version;
    String comment;
}
