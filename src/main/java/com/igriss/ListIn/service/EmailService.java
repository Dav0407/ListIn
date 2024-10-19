package com.igriss.ListIn.service;

import com.igriss.ListIn.dto.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.UserHasAccountException;

import java.io.IOException;

public interface EmailService {
    String verifyEmail(EmailVerificationRequestDTO verificationRequestDTO, String code) throws UserHasAccountException;
    EmailVerificationRequestDTO sendEmail(EmailVerificationRequestDTO verificationRequestDTO)  throws EmailNotFoundException;

}
