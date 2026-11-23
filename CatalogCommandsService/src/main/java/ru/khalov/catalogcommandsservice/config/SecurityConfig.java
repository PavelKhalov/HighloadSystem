package ru.khalov.catalogcommandsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http){
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/api/inventory/**").hasRole("SELLER")
                        .anyRequest().authenticated())
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(o -> o.jwt(jwt -> {}))
                .build();

    }
}
