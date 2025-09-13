package com.example.hackthon.Subscription.config;

import com.example.hackthon.Subscription.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication needed)
                        .requestMatchers("/users/register", "/users/login").permitAll()

                        // Admin only endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // User only endpoints
                        .requestMatchers("/users/**").hasRole("USER")

                        // Shared endpoints accessible by both ADMIN and USER
                        .requestMatchers("/api/subscriptions/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/plans/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/discounts/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/audit/**").hasRole("ADMIN") // example: audit logs only for admin

                        // All other requests need to be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic()
                .and()
                .formLogin(form -> form.disable());

        return http.build();
    }
}
