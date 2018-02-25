package com.semicolon.librarians.libraryguide.Models;

import java.io.Serializable;

/**
 * Created by Delta on 23/02/2018.
 */

public class ChatUserID implements Serializable {
    private String username;

    public ChatUserID() {
    }

    public ChatUserID(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
