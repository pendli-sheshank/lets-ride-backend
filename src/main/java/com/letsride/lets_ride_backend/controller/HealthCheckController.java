// src/main/java/com/letsride/backend/controller/HealthCheckController.java
package com.letsride.lets_ride_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/health") // Base path for this controller
public class HealthCheckController {

    @GetMapping
    public Map<String, String> checkHealth() {
        // Return a simple JSON object indicating status is OK
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend service is running!");
        return response;
    }
}