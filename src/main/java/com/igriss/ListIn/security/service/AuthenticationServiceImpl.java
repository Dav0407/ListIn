package com.igriss.ListIn.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igriss.ListIn.security.security_dto.AuthenticationRequestDTO;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var user = User.builder()
                .nickName(request.getNickName())
                .enableCalling(request.getEnableCalling())
                .phoneNumber(request.getPhoneNumber())
                .fromTime(request.getFromTime())
                .toTime(request.getToTime())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRoles())
                .isGrantedForPreciseLocation(request.getIsGrantedForPreciseLocation())
                .locationName(request.getLocationName())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);

        //user should not have an access refreshing token with access token
        if (!jwtService.isRefreshToken(refreshToken)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();

            //if it is not, then we can go and create new access token using refresh token
            if (jwtService.isValidRefreshToken(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);

                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
