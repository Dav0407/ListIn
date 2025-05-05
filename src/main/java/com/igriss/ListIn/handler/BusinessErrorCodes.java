package com.igriss.ListIn.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {

    TOO_MANY_ATTEMPTS(429, TOO_MANY_REQUESTS, "Too many attempts, try again later."),
    USER_UNAUTHORIZED(401, UNAUTHORIZED, "User data not found"),
    EMAIL_CONFIRMATION_FAILED(400, BAD_REQUEST, "Invalid otp code"),
    EMAIL_NOT_FOUND(404, NOT_FOUND, "Email not found"),
    EMAIL_TEMPLATE_LOAD_FAILED(503, SERVICE_UNAVAILABLE, "Email template load failed"),
    ATTRIBUTES_NOT_FOUND(500,INTERNAL_SERVER_ERROR, "Category attributes not found"),
    SEARCH_QUERY_FAILED(400, BAD_REQUEST, "Search query failed"),
    USER_HAS_ACCOUNT(409, CONFLICT, "User has account"),
    ATTRIBUTE_VALUES_NOT_FOUND(500, INTERNAL_SERVER_ERROR, "Validation failed"),
    NO_PUBLICATION(204,NO_CONTENT,"Publication does not exist"),
    USER_NOT_FOUND(404,NOT_FOUND,"Wrong credentials");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

}
