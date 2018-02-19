package com.semicolon.librarians.libraryguide.Services;

import java.io.Serializable;

/**
 * Created by Delta on 12/01/2018.
 */

public class ErrorUtils implements Serializable {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
