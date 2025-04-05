package com.igriss.ListIn.exceptions;

public class Oauth2VerificationFailedException extends RuntimeException {
    public Oauth2VerificationFailedException(String message) {
        super(message);
    }
}
