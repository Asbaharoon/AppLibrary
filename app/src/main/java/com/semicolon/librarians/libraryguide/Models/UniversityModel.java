package com.semicolon.librarians.libraryguide.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 11/01/2018.
 */

public class UniversityModel implements Serializable {
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("university_img")
    private String user_photo;
    @SerializedName("university_name")
    private String uni_name;
    @SerializedName("user_email")
    private String uni_email;
    @SerializedName("user_phone")
    private String uni_phone;
    @SerializedName("university_major")
    private String uni_major;
    @SerializedName("user_country")
    private String uni_country;
    @SerializedName("university_address")
    private String uni_address;
    @SerializedName("university_site")
    private String uni_site;
    @SerializedName("user_username")
    private String uni_username;
    private String uni_password;
    @SerializedName("university_google_lat")
    private String uni_lat;
    @SerializedName("university_google_lng")
    private String uni_lng;
    private String user_token;


    public UniversityModel() {
    }

    public UniversityModel(String user_type, String user_photo, String uni_name, String uni_email, String uni_phone, String uni_major, String uni_country, String uni_address, String uni_site, String uni_username, String uni_password, String uni_lat, String uni_lng) {
        this.user_type = user_type;
        this.user_photo = user_photo;
        this.uni_name = uni_name;
        this.uni_email = uni_email;
        this.uni_phone = uni_phone;
        this.uni_major = uni_major;
        this.uni_country = uni_country;
        this.uni_address = uni_address;
        this.uni_site = uni_site;
        this.uni_username = uni_username;
        this.uni_password = uni_password;
        this.uni_lat = uni_lat;
        this.uni_lng = uni_lng;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUni_name() {
        return uni_name;
    }

    public void setUni_name(String uni_name) {
        this.uni_name = uni_name;
    }

    public String getUni_email() {
        return uni_email;
    }

    public void setUni_email(String uni_email) {
        this.uni_email = uni_email;
    }

    public String getUni_phone() {
        return uni_phone;
    }

    public void setUni_phone(String uni_phone) {
        this.uni_phone = uni_phone;
    }

    public String getUni_major() {
        return uni_major;
    }

    public void setUni_major(String uni_major) {
        this.uni_major = uni_major;
    }

    public String getUni_country() {
        return uni_country;
    }

    public void setUni_country(String uni_country) {
        this.uni_country = uni_country;
    }

    public String getUni_address() {
        return uni_address;
    }

    public void setUni_address(String uni_address) {
        this.uni_address = uni_address;
    }

    public String getUni_site() {
        return uni_site;
    }

    public void setUni_site(String uni_site) {
        this.uni_site = uni_site;
    }

    public String getUni_username() {
        return uni_username;
    }

    public void setUni_username(String uni_username) {
        this.uni_username = uni_username;
    }

    public String getUni_password() {
        return uni_password;
    }

    public void setUni_password(String uni_password) {
        this.uni_password = uni_password;
    }

    public String getUni_lat() {
        return uni_lat;
    }

    public void setUni_lat(String uni_lat) {
        this.uni_lat = uni_lat;
    }

    public String getUni_lng() {
        return uni_lng;
    }

    public void setUni_lng(String uni_lng) {
        this.uni_lng = uni_lng;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
