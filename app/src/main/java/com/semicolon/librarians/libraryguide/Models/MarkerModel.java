package com.semicolon.librarians.libraryguide.Models;

import java.io.Serializable;

/**
 * Created by Delta on 19/01/2018.
 */

public class MarkerModel implements Serializable {
    private String user_type;
    private int position;

    public MarkerModel() {
    }

    public MarkerModel(String user_type, int position) {
        this.user_type = user_type;
        this.position = position;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
