package com.letsride.lets_ride_backend.repository;

import com.letsride.lets_ride_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Example custom queries:
    List<Booking> findByRider_IdOrderByScheduledTrip_DepartureTimeDesc(Long riderId);

    List<Booking> findByDriver_IdOrderByScheduledTrip_DepartureTimeDesc(Long driverId);

    List<Booking> findByScheduledTrip_Id(Long tripId);

}