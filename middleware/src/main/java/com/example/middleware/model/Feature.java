package com.example.middleware.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String feature;

    @Column(length = 3000)
    private String description;

    private Date releaseDate;

    private String releaseVersion;

    public Feature(String feature, String description, Date releaseDate, String releaseVersion) {
        this.feature = feature;
        this.description = description;
        this.releaseDate = releaseDate;
        this.releaseVersion = releaseVersion;
    }
}
