package com.letsride.lets_ride_backend.service;
import java.util.List;
import com.letsride.lets_ride_backend.dto.CreateTripRequest; // Import DTO
import com.letsride.lets_ride_backend.entity.ScheduledTrip;
import com.letsride.lets_ride_backend.entity.User;
import com.letsride.lets_ride_backend.entity.enums.TripStatus; // Import enum
import com.letsride.lets_ride_backend.repository.ScheduledTripRepository;
import com.letsride.lets_ride_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.letsride.lets_ride_backend.dto.TripResponse;

import java.time.LocalDateTime; // Make sure this is imported

@Service
public class ScheduledTripService {

    private final ScheduledTripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScheduledTripService(ScheduledTripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ScheduledTrip createTrip(CreateTripRequest request, Long posterUserId) {
        // 1. Find the user who is posting this trip
        //    Handle case where user might not be found (though should be logged in)
        User poster = userRepository.findById(posterUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + posterUserId));

        // 2. Create a new ScheduledTrip entity from the request DTO
        ScheduledTrip newTrip = new ScheduledTrip();
        newTrip.setPostedByUser(poster);
        newTrip.setPostType(request.postType());
        newTrip.setOriginAddress(request.originAddress());
        newTrip.setDestinationAddress(request.destinationAddress());
        newTrip.setDepartureTime(request.departureTime());
        newTrip.setFixedPrice(request.fixedPrice());
        newTrip.setSeatsAvailable(request.seatsAvailable()); // Will be null if not provided
        newTrip.setStatus(TripStatus.ACTIVE); // Set initial status

        // Add lat/lon if capturing them
        // newTrip.setOriginLat(request.originLat());
        // ... etc ...

        // 3. Save the new trip using the repository
        return tripRepository.save(newTrip);
    }
    @Transactional(readOnly = true)
    public List<TripResponse> findActiveTrips() {
        List<ScheduledTrip> activeTrips = tripRepository.findByStatusOrderByDepartureTimeAsc(TripStatus.ACTIVE);

        // Map the list of entities to a list of DTOs
        return activeTrips.stream()
                .map(this::mapToTripResponse).collect(Collectors.toList());
    }

    // Helper method to map an entity to a DTO
    private TripResponse mapToTripResponse(ScheduledTrip trip) {
        // Explicitly get needed data from the potentially lazy-loaded User proxy
        Long userId = (trip.getPostedByUser() != null) ? trip.getPostedByUser().getId() : null;
        String userName = (trip.getPostedByUser() != null) ? trip.getPostedByUser().getName() : "Unknown";

        return new TripResponse(
                trip.getId(),
                trip.getPostType(),
                trip.getOriginAddress(),
                trip.getDestinationAddress(),
                trip.getDepartureTime(),
                trip.getStatus(),
                trip.getFixedPrice(),
                trip.getSeatsAvailable(),
                userId,      // Add user ID
                userName     // Add user name
        );
    }



    // --- Add other methods here later ---
    // e.g., searchTrips(..), getTripById(..)
}