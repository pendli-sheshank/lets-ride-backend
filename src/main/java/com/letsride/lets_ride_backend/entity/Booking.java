package com.letsride.lets_ride_backend.entity;

import com.letsride.lets_ride_backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduled_trip_id", nullable = false)
    private ScheduledTrip scheduledTrip; // The trip this booking relates to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_user_id", nullable = false)
    private User rider; // The rider involved in this booking

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_user_id", nullable = false)
    private User driver; // The driver involved in this booking

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal agreedPrice; // The fixed price agreed upon for this booking

    @Column(nullable = false)
    private Integer bookedSeats = 1; // Default to 1 seat booked

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.CONFIRMED; // Or start as REQUESTED if needed

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}