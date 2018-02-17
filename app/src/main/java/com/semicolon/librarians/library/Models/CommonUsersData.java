package com.semicolon.librarians.library.Models;

import java.io.Serializable;

/**
 * Created by Delta on 12/02/2018.
 */

public class CommonUsersData implements Serializable {

    private String user_type;
    private String user_photo;
    private String photo_link;
    private String user_name;
    private String user_username;
    private String user_token;

    public CommonUsersData() {
    }

    public CommonUsersData(String user_type, String user_photo, String photo_link, String user_name, String user_username, String user_token) {
        this.user_type = user_type;
        this.user_photo = user_photo;
        this.photo_link = photo_link;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_token = user_token;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_photo_link() {
        return photo_link;
    }

    public void setUser_photo_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

}
