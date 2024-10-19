package com.igriss.ListIn.exceptions;

import jakarta.mail.MessagingException;

public class EmailNotFoundException extends MessagingException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
