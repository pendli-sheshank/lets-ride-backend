package com.letsride.lets_ride_backend.repository;

import com.letsride.lets_ride_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA derives query from method name:
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Add other custom queries if needed later
}