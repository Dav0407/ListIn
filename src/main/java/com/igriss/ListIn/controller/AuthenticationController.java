package com.igriss.ListIn.controller;

import com.igriss.ListIn.dto.security_dto.AuthenticationRequestDTO;
import com.igriss.ListIn.dto.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.dto.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.dto.security_dto.RegisterRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.UserHasAccountException;
import com.igriss.ListIn.service.AuthenticationService;
import com.igriss.ListIn.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
    @PostMapping("/send/mail")
    public ResponseEntity<EmailVerificationRequestDTO> sendEmail(EmailVerificationRequestDTO verificationRequestDTO) throws  EmailNotFoundException {
        return ResponseEntity.ok(emailService.sendEmail(verificationRequestDTO));
    }

    @PostMapping("/verify/email")
    public ResponseEntity<String> verifyEmail(EmailVerificationRequestDTO verificationRequestDTO, @RequestParam String code)  throws UserHasAccountException{
        return ResponseEntity.ok(emailService.verifyEmail(verificationRequestDTO,code));
    }
}
