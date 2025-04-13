package com.example.middleware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class FeatureDTO {
    private String feature;
    private String description;
    private Date releaseDate;
    private String releaseVersion;
}
