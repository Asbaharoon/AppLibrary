package com.semicolon.librarians.libraryguide.Models;

import java.io.Serializable;

/**
 * Created by Delta on 21/02/2018.
 */

public class Tes implements Serializable{
    String user_username;
    String user_token;
    String user_google_lat;
    String user_google_lng;

    public Tes() {
    }

    public Tes(String user_username, String user_token, String user_google_lat, String user_google_lng) {
        this.user_username = user_username;
        this.user_token = user_token;
        this.user_google_lat = user_google_lat;
        this.user_google_lng = user_google_lng;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_google_lat() {
        return user_google_lat;
    }

    public void setUser_google_lat(String user_google_lat) {
        this.user_google_lat = user_google_lat;
    }

    public String getUser_google_lng() {
        return user_google_lng;
    }

    public void setUser_google_lng(String user_google_lng) {
        this.user_google_lng = user_google_lng;
    }
}
