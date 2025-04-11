package com.letsride.lets_ride_backend.dto;

import com.letsride.lets_ride_backend.entity.enums.TripPostType;
import jakarta.validation.constraints.Future; // For date validation
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive; // For price and seats

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Using records for concise DTOs (requires Java 16+)
// If using older Java, create a standard class with private fields, constructor, getters.
public record CreateTripRequest(
    @NotNull(message = "Post type cannot be null")
    TripPostType postType, // NEED_RIDE or OFFER_RIDE

    @NotBlank(message = "Origin address cannot be blank")
    String originAddress,

    @NotBlank(message = "Destination address cannot be blank")
    String destinationAddress,

    // Add lat/lon fields if you plan to capture them immediately
    // Double originLat,
    // Double originLon,
    // Double destinationLat,
    // Double destinationLon,

    @NotNull(message = "Departure time cannot be null")
    @Future(message = "Departure time must be in the future") // Basic validation
    LocalDateTime departureTime,

    @NotNull(message = "Fixed price cannot be null")
    @Positive(message = "Fixed price must be positive")
    BigDecimal fixedPrice,

    // Optional: Only required/used if postType is OFFER_RIDE
    @Positive(message = "Seats available must be positive if provided")
    Integer seatsAvailable
) {}