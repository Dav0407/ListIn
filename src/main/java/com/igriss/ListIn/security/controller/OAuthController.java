package com.igriss.ListIn.security.controller;

import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.service.GoogleTokenVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final GoogleTokenVerificationService googleTokenVerificationService;

    @PostMapping("/google/mobile")
    public ResponseEntity<AuthenticationResponseDTO> authenticateWithGoogleMobile(
            @RequestParam String idToken, @RequestParam String email) {
        return ResponseEntity.ok(googleTokenVerificationService.authenticateUser(idToken, email));
    }
}
