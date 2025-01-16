package com.igriss.ListIn.handler;


import com.igriss.ListIn.exceptions.EmailVerificationFailedException;
import com.igriss.ListIn.exceptions.RateLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.igriss.ListIn.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> handleException(RateLimitExceededException exception) {
        return ResponseEntity
                .status(TOO_MANY_REQUESTS)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(TOO_MANY_ATTEMPTS.getCode())
                                .businessErrorDescription(TOO_MANY_ATTEMPTS.getDescription())
                                .errorMessage(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(USER_UNAUTHORIZED.getCode())
                                .businessErrorDescription(USER_UNAUTHORIZED.getDescription())
                                .errorMessage(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EmailVerificationFailedException.class)
    public ResponseEntity<ExceptionResponse> handleException(EmailVerificationFailedException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(EMAIL_CONFIRMATION_FAILED.getCode())
                                .businessErrorDescription(EMAIL_CONFIRMATION_FAILED.getDescription())
                                .errorMessage(exception.getMessage())
                                .build()
                );
    }
}
