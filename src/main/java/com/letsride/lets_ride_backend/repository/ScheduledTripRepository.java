package com.letsride.lets_ride_backend.repository;

import com.letsride.lets_ride_backend.entity.ScheduledTrip;
import com.letsride.lets_ride_backend.entity.enums.TripStatus; // If you need to query by status
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledTripRepository extends JpaRepository<ScheduledTrip, Long> {

    // Example custom queries (add more as needed for searching):
    List<ScheduledTrip> findByStatusOrderByDepartureTimeAsc(TripStatus status);

    List<ScheduledTrip> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find trips posted by a specific user
    List<ScheduledTrip> findByPostedByUser_Id(Long userId);

}