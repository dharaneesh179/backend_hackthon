package com.example.hackthon.Subscription.service;

import com.example.hackthon.Subscription.model.User;
import com.example.hackthon.Subscription.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword());

        // Strip ROLE_ if present, but typically role enum shouldn't have prefix
        String role = user.getRole().name();
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        builder.roles(role);  // Spring Security will add "ROLE_" prefix internally
        return builder.build();
    }
}
