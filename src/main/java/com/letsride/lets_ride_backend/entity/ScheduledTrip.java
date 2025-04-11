package com.letsride.lets_ride_backend.entity;

import com.letsride.lets_ride_backend.entity.enums.TripPostType;
import com.letsride.lets_ride_backend.entity.enums.TripStatus;
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
@Table(name = "scheduled_trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Link to the User who posted this
    @JoinColumn(name = "posted_by_user_id", nullable = false)
    private User postedByUser;

    @Enumerated(EnumType.STRING) // Store enum as string in DB
    @Column(nullable = false)
    private TripPostType postType; // NEED_RIDE or OFFER_RIDE

    @Column(nullable = false)
    private String originAddress;

    @Column(nullable = false)
    private String destinationAddress;

    // Optional coordinates - good for future features
    private Double originLat;
    private Double originLon;
    private Double destinationLat;
    private Double destinationLon;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status = TripStatus.ACTIVE; // Default status

    @Column(nullable = false, precision = 10, scale = 2) // Good for currency
    private BigDecimal fixedPrice;

    // Relevant for OFFER_RIDE type
    private Integer seatsAvailable;

    @CreationTimestamp // Automatically set on creation
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically set on update
    private LocalDateTime updatedAt;

    // We might add a relationship back to Bookings later (@OneToMany) if needed
}