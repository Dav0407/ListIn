package com.igriss.ListIn.security.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.igriss.ListIn.exceptions.Oauth2VerificationFailedException;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleTokenVerificationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    public AuthenticationResponseDTO authenticateUser(String idToken, String email) {

        //todo -> To be added a Google idToken verification, know we do not have enough experience ;(😭😭😭
//        try {
//            verifyGoogleIdToken(idToken, email);
//        } catch (Exception e) {
//            log.error("Google token verification failed for email {}: {}", email, e.getMessage());
//            throw new Oauth2VerificationFailedException(e.getMessage());
//        }

        log.info("Google authentication successful for email: {}", email);

        AuthenticationResponseDTO.AuthenticationResponseDTOBuilder
                authenticationResponseDTO = AuthenticationResponseDTO.builder();

        Optional<User> user = userService.findUserByEmail(email);
        if(user.isPresent()){
            var accessToken = jwtService.generateToken(user.get());
            var refreshToken = jwtService.generateRefreshToken(user.get());

            authenticationResponseDTO
                    .accessToken(accessToken)
                    .refreshToken(refreshToken);
        }

        return authenticationResponseDTO.build();
    }

    private void verifyGoogleIdToken(String idToken, String email) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        log.info(idToken);
        GoogleIdToken googleIdToken = verifier.verify(idToken);
        log.info("Came here:::::::");
        if (googleIdToken != null) {
            Payload payload = googleIdToken.getPayload();

            String tokenEmail = payload.getEmail();
            if (tokenEmail == null || !tokenEmail.equalsIgnoreCase(email)) {
                log.error("Email mismatch: token email={}, claimed email={}", tokenEmail, email);
                throw new IOException("Email in token doesn't match the provided email");
            }

        } else {
            log.error("Invalid Google ID token");
            throw new IOException("Invalid Google ID token");
        }
    }
}