package com.semicolon.librarians.library.Models;

import java.io.Serializable;

/**
 * Created by Delta on 15/02/2018.
 */

public class RefreshToken implements Serializable {

    private String token;

    public RefreshToken() {
    }

    public RefreshToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
