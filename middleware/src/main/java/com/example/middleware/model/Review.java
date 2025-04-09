package com.example.middleware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
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
        this.comment = comment;
        this.rating = rating;
        this.productVersion = version;
        this.comment = comment;
        this.timestamp = timestamp;
    }

}