package com.igriss.ListIn.exceptions;

public class UserHasAccountException extends RuntimeException {
    public UserHasAccountException(String message) {
        super(message);
    }
}
