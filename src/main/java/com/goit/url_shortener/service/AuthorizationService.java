package com.goit.url_shortener.service;

import com.goit.url_shortener.security.JwtTokenProvider;
import com.goit.url_shortener.user.User;
import com.goit.url_shortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling user authorization and token validation.
 */
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    private final JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Extracts the authorized user from the provided authorization header.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return An Optional containing the authenticated user if the token is valid, or an empty Optional otherwise.
     */
    public Optional<User> getAuthorizedUser(String authorizationHeader) {
        String token = tokenProvider.extractTokenFromHeader(authorizationHeader);
        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.extractUsernameFromToken(token);
            return userRepository.findByUsername(username);
        }
        return Optional.empty();
    }
}
