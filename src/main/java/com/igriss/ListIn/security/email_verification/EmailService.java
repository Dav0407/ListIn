package com.igriss.ListIn.security.email_verification;

import com.igriss.ListIn.security.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.UserHasAccountException;

public interface EmailService {
    String verifyEmail(EmailVerificationRequestDTO verificationRequestDTO, String code) throws UserHasAccountException;
    EmailVerificationRequestDTO sendEmail(EmailVerificationRequestDTO verificationRequestDTO)  throws EmailNotFoundException;
}
