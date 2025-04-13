package com.example.middleware.service;

import com.example.middleware.dto.FeatureDTO;
import com.example.middleware.model.Feature;
import com.example.middleware.repo.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    public Feature getFeatureById(Long id) {
        return featureRepository.findById(id).orElseThrow();
    }

    public Feature createFeature(FeatureDTO dto) {
        Feature feature = new Feature(
            dto.getFeature(),
            dto.getDescription(),
            dto.getReleaseDate(),
            dto.getReleaseVersion()
        );
        return featureRepository.save(feature);
    }
}

