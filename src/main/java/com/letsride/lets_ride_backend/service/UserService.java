package com.letsride.lets_ride_backend.service;

import com.letsride.lets_ride_backend.entity.User;
import com.letsride.lets_ride_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Optional for single operations, good practice

import java.util.Optional;

@Service // Marks this as a Spring service component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection: Spring automatically provides the beans
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     * @param name User's full name
     * @param email User's email (must be unique)
     * @param password User's plain text password
     * @param role User's role (e.g., "RIDER", "DRIVER")
     * @return The saved User entity
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional // Ensures the operation is performed within a database transaction
    public User registerNewUser(String name, String email, String password, String role) {
        // 1. Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email address already in use: " + email);
        }

        // 2. Create new User entity
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        // 3. IMPORTANT: Encode the password before saving!
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role); // Consider validating role input later

        // 4. Save the user using the repository
        return userRepository.save(newUser);
    }

    /**
     * Finds a user by email. (Used for Login checks later)
     * @param email User's email
     * @return Optional containing the User if found, empty otherwise
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // --- Add other user-related business logic methods here ---
    // For example: Login logic (would typically compare hashed passwords),
    // update profile, get user details etc.

    // Example Login Check (Password check happens in Security layer usually)
    // public User validateUserCredentials(String email, String rawPassword) {
    //    Optional<User> userOpt = findUserByEmail(email);
    //    if (userOpt.isPresent()) {
    //        User user = userOpt.get();
    //        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
    //            return user;
    //        }
    //    }
    //    throw new IllegalArgumentException("Invalid email or password");
    // }

}