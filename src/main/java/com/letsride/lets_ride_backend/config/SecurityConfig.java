package com.letsride.lets_ride_backend.config; // Make sure this matches your package!

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console; // Helper for H2 path

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    // Bean to provide a password encoder (BCrypt is recommended)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (Cross-Site Request Forgery) - Common for stateless APIs
            // Important: CSRF must be disabled for H2 console to work
            .csrf(csrf -> csrf.disable())

            // Configure authorization rules
            .authorizeHttpRequests(authz -> authz
                // Allow access to H2 console - IMPORTANT!
                .requestMatchers(toH2Console()).permitAll()
                // Allow access to health check endpoint
                .requestMatchers("/api/health").permitAll()
                // Allow access to authentication endpoints (register/login) later
                .requestMatchers("/api/auth/**").permitAll()
                // Any other request must be authenticated
                .anyRequest().permitAll()
            )

            // Configure session management to be stateless (for APIs using tokens like JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

            // Special configuration for H2 Console to work within frames
            http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
             );

        // We will add JWT filter configuration here later

        return http.build();
    }
}