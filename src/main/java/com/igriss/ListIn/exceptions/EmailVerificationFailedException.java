package com.igriss.ListIn.exceptions;

public class EmailVerificationFailedException extends RuntimeException {
    public EmailVerificationFailedException(String message) {
        super(message);
    }
}
