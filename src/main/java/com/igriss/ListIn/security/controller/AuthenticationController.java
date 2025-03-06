package com.igriss.ListIn.security.controller;

import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.UserHasAccountException;
import com.igriss.ListIn.exceptions.UserNotFoundException;
import com.igriss.ListIn.security.email_verification.EmailService;
import com.igriss.ListIn.security.security_dto.AuthenticationRequestDTO;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;
import com.igriss.ListIn.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO request, @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) throws UserHasAccountException {
        return ResponseEntity.ok(authenticationService.register(request, language));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO request) throws UserNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
    @PostMapping("/send/mail")
    public ResponseEntity<EmailVerificationRequestDTO> sendEmail(@RequestBody EmailVerificationRequestDTO verificationRequestDTO) throws  EmailNotFoundException {
        return ResponseEntity.ok(emailService.sendEmail(verificationRequestDTO));
    }

    @PostMapping("/verify/email")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailVerificationRequestDTO verificationRequestDTO, @RequestParam String code)  throws UserHasAccountException{
        return ResponseEntity.ok(emailService.verifyEmail(verificationRequestDTO,code));
    }
}
