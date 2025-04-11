package com.letsride.lets_ride_backend.dto;

import com.letsride.lets_ride_backend.entity.enums.TripPostType;
import com.letsride.lets_ride_backend.entity.enums.TripStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Using a record for a simple, immutable DTO
public record TripResponse(
        Long id,
        TripPostType postType,
        String originAddress,
        String destinationAddress,
        LocalDateTime departureTime,
        TripStatus status,
        BigDecimal fixedPrice,
        Integer seatsAvailable,
        Long posterUserId, // Include user ID
        String posterUserName // Include user name
        // Add other fields as needed for the response
) {}