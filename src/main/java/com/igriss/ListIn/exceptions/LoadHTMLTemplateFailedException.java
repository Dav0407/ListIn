package com.igriss.ListIn.exceptions;

import java.io.IOException;

public class LoadHTMLTemplateFailedException extends RuntimeException {
    public LoadHTMLTemplateFailedException(String message) {
        super(message);
    }
}
