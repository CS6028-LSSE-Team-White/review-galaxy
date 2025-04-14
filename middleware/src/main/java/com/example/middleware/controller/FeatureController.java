package com.example.middleware.controller;

import com.example.middleware.dto.FeatureDTO;
import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;
import com.example.middleware.service.FeatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
public class FeatureController {
    private final FeatureService featureService;
    private final ObjectMapper objectMapper;

    @GetMapping("/features")
    public APIResponse getFeatures() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(featureService.getAllFeatures()));
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/feature/{id}")
    public APIResponse getFeatureById(@NotNull @PathVariable Long id) {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(featureService.getFeatureById(id)));
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @PostMapping("/feature")
    public APIResponse createFeature(@RequestBody FeatureDTO featureDTO) {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(
                            featureService.createFeature(featureDTO)));
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}
