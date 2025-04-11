package com.letsride.lets_ride_backend.controller;
import java.util.List;
import com.letsride.lets_ride_backend.dto.CreateTripRequest;
import com.letsride.lets_ride_backend.entity.ScheduledTrip;
import com.letsride.lets_ride_backend.service.ScheduledTripService;
import jakarta.validation.Valid; // For validating request body
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // To get logged-in user later
import org.springframework.web.bind.annotation.*;
import com.letsride.lets_ride_backend.dto.TripResponse; // Import the DTO
// Import custom UserPrincipal or UserDetails implementation later for auth.getPrincipal()


@RestController
@RequestMapping("/api/trips") // Base path for all trip-related endpoints
public class ScheduledTripController {

    private final ScheduledTripService tripService;

    @Autowired
    public ScheduledTripController(ScheduledTripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    // @PreAuthorize("isAuthenticated()") // Add security later
    public ResponseEntity<?> createTrip(
            @Valid @RequestBody CreateTripRequest createTripRequest
            // Authentication authentication // Inject Authentication object later
            ) {

        // --- TEMPORARY: Get User ID (Replace with actual logged-in user ID) ---
        // In a real app, you get the user ID from the Authentication principal
        // For now, we'll hardcode user ID 1 - CREATE THIS USER IN H2 CONSOLE FIRST!
        Long hardcodedUserId = 1L;
        System.out.println("Attempting to create trip for hardcoded user ID: " + hardcodedUserId);
        // --- END TEMPORARY ---

        try {
             // Long userId = ((YourUserDetailsClass) authentication.getPrincipal()).getId(); // Example
             ScheduledTrip createdTrip = tripService.createTrip(createTripRequest, hardcodedUserId /* replace with userId */);
            // Return the created trip details (consider mapping to a TripResponse DTO)
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (IllegalArgumentException e) {
             // Handle cases like user not found or invalid input more gracefully
             return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
             // Generic error handler
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating trip: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllActiveTrips() { // Return DTO list
        List<TripResponse> activeTrips = tripService.findActiveTrips();
        return ResponseEntity.ok(activeTrips); // Return the list of DTOs
    }

    // --- Add other endpoints here later ---
    // e.g., GET /api/trips (search/list)
    // e.g., GET /api/trips/{id} (get details)

}