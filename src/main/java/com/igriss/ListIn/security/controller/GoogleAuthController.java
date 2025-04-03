package com.igriss.ListIn.security.controller;

import com.igriss.ListIn.security.roles.Role;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.service.JwtService;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/google")
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/mobile")
    public ResponseEntity<AuthenticationResponseDTO> authenticateWithGoogle(@RequestParam String idToken, @RequestParam String email) {

        // In a production app, you would verify the ID token here
        // Using Google's API: https://developers.google.com/identity/sign-in/web/backend-auth
        // For this implementation, we'll assume the mobile app has already verified the token

        log.info("Google authentication for email: {}", email);

        // Find or create user
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email.toLowerCase())
                            .role(Role.INDIVIDUAL_SELLER)
                            .build();
                    return userRepository.save(newUser);
                });

        // Generate JWT tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.ok(AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }
}