package com.igriss.ListIn.service_impl.security;

import com.igriss.ListIn.dto.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.EmailVerificationFailedException;
import com.igriss.ListIn.exceptions.UserHasAccountException;
import com.igriss.ListIn.repository.UserRepository;
import com.igriss.ListIn.security.verification.EmailVerification;
import com.igriss.ListIn.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private final EmailVerification emailVerification;

    @Override
    public String verifyEmail(EmailVerificationRequestDTO verificationRequestDTO, String code) throws UserHasAccountException{

        if (emailVerification.verifyMail(code)) {
            if (userRepository.findByEmail(verificationRequestDTO.getEmail()).isPresent())
               throw new UserHasAccountException("User has an account with that email");
            else
                return "Email verification successful";
        }else
            throw new EmailVerificationFailedException("Failed to verify email");
    }

    @Override
    public EmailVerificationRequestDTO sendEmail(EmailVerificationRequestDTO verificationRequestDTO) throws EmailNotFoundException {
        emailVerification.sendMail(verificationRequestDTO);
        return verificationRequestDTO;
    }

}
