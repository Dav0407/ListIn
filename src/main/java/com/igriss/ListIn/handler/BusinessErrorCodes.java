package com.igriss.ListIn.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {

    TOO_MANY_ATTEMPTS(429, TOO_MANY_REQUESTS, "Too many attempts, try again later."),
    USER_UNAUTHORIZED(401, UNAUTHORIZED, "User data not found"),
    EMAIL_CONFIRMATION_FAILED(400, BAD_REQUEST, "Invalid otp code");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

}
