package com.igriss.ListIn.exceptions;

public class TokenIsBlacklistedException extends RuntimeException {
    public TokenIsBlacklistedException(String message) {
        super(message);
    }
}
