// src/main/java/com/reviewgalaxy/middleware/HealthController.java

package com.reviewgalaxy.middleware;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "message", "connected",
            "time", Instant.now().toString()
        );
    }
}
