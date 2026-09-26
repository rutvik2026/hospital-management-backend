package com.hospital.system.hospitalmanagementsystem.config;

import com.hospital.system.hospitalmanagementsystem.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("https://hospital-management-frontend-oegc.vercel.app")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup"
                        ).permitAll()

                        // Admin
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // Doctor
                        .requestMatchers("/api/doctor/**")
                        .hasRole("DOCTOR")

                        // Patient
                        .requestMatchers("/api/patient/**")
                        .hasRole("PATIENT")

                        // Employee + Admin
                        .requestMatchers("/api/emp/**")
                        .hasAnyRole("EMPLOYEE", "ADMIN")

                        // Tasks
                        .requestMatchers("/api/task/**")
                        .hasAnyRole("DOCTOR", "ADMIN")

                        // Common user APIs
                        .requestMatchers("/api/any/**")
                        .hasAnyRole(
                                "DOCTOR",
                                "ADMIN",
                                "PATIENT",
                                "EMPLOYEE"
                        )

                        // Appointment
                        .requestMatchers("/api/appointment/**")
                        .hasAnyRole("DOCTOR", "PATIENT")

                        // Everything else
                        .anyRequest()
                        .authenticated()
                )

                // IMPORTANT
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
