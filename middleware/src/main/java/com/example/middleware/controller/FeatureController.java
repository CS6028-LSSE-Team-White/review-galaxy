package com.example.middleware.controller;

import com.example.middleware.model.APIResponse;
import com.example.middleware.model.APIStatus;
import com.example.middleware.model.Feature;
import com.example.middleware.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FeatureController {
    private final ObjectMapper objectMapper;

    @GetMapping("/api/features")
    public APIResponse getFeatures() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getZoomFeatures())
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/firefox/features")
    public APIResponse getFirefoxFeatures() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getFirefoxFeatures())
            );

        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/webex/features")
    public APIResponse getWebexFeatures() {
        try {
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(Util.getWebexFeatures())
            );

        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }

    @GetMapping("/api/feature/{id}")
    public APIResponse getFeatureById(@NotNull @PathVariable Long id) {
        try {
            Feature feature = Util.findZoomFeatureById(id);
            if (feature == null) {
                return new APIResponse(APIStatus.ERROR, "Feature not found");
            }
            return new APIResponse(
                    APIStatus.SUCCESS,
                    objectMapper.writeValueAsString(feature)
            );
        } catch (Exception e) {
            return new APIResponse(APIStatus.ERROR, e.getMessage());
        }
    }
}
