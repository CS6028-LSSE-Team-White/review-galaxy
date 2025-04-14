package com.example.middleware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;

import java.sql.Date;

@Data
@Getter
@Setter
public class Feature {
    private Long id;
    private String feature;
    private String description;
    @JsonProperty("release_data")
    private Date releaseDate;
    @JsonProperty("release_version")
    private String releaseVersion;

    public Feature(Long id, String feature, String description, @Nullable Date releaseDate, String releaseVersion) {
        this.id = id;
        this.feature = feature;
        this.description = description;
        this.releaseDate = releaseDate;
        this.releaseVersion = releaseVersion;
    }
}
