package com.igriss.ListIn.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igriss.ListIn.security.roles.Role;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.service.JwtService;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            log.info("Authenticated OAuth2 user logged in: [{}], Email: [{}], Name: [{}]",
                    authentication.getAuthorities().toString(), email, name);

            // Find existing user or create new one
            assert email != null;
            User user = userRepository.findByEmail(email.toLowerCase())
                    .orElseGet(() -> createNewUser(email, name));

            // Generate JWT tokens
            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            // Return tokens in the response
            response.setContentType("application/json");
            AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            objectMapper.writeValue(response.getOutputStream(), authResponse);
        }
    }

    private User createNewUser(String email, String name) {
        User newUser = User.builder()
                .email(email.toLowerCase())
                .nickName(name)
                .role(Role.INDIVIDUAL_SELLER) // Default role
                .build();

        return userRepository.save(newUser);
    }
}