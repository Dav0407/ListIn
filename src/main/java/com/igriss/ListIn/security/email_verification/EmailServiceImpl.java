package com.igriss.ListIn.security.email_verification;

import com.igriss.ListIn.security.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.EmailVerificationFailedException;
import com.igriss.ListIn.exceptions.UserHasAccountException;
import com.igriss.ListIn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final EmailVerificationService emailVerification;

    @Override
    public String verifyEmail(EmailVerificationRequestDTO verificationRequestDTO, String code) throws UserHasAccountException {

        if (emailVerification.verifyCode(verificationRequestDTO.getEmail(), code)) {

            if (userRepository.findByEmail(verificationRequestDTO.getEmail()).isPresent()) {

                throw new UserHasAccountException("User already has an account with that email");
            }

            return "Email verification successful";
        } else {
            var exception = new EmailVerificationFailedException("Failed to verify email. Invalid or expired verification code.");
            log.error("Failed to verify email. Invalid or expired verification code.", exception);
            throw exception;
        }
    }

    @Override
    public EmailVerificationRequestDTO sendEmail(EmailVerificationRequestDTO verificationRequestDTO) throws EmailNotFoundException {

        emailVerification.sendVerificationEmail(verificationRequestDTO);

        return verificationRequestDTO;
    }
}
