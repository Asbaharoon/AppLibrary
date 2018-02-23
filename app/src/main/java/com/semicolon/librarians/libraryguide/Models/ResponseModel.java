package com.semicolon.librarians.libraryguide.Models;

import java.io.Serializable;

/**
 * Created by Delta on 20/02/2018.
 */

public class ResponseModel implements Serializable {

    String response;

    public ResponseModel() {
    }

    public ResponseModel(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
