package com.letsride.lets_ride_backend.service;

import com.letsride.lets_ride_backend.repository.BookingRepository;
import com.letsride.lets_ride_backend.repository.ScheduledTripRepository; // Need other repos
import com.letsride.lets_ride_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduledTripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ScheduledTripRepository tripRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    // --- Add methods here later ---
    // e.g., requestBooking(..), offerToDrive(..), confirmBooking(..), cancelBooking(..)
}