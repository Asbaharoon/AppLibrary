package com.semicolon.librarians.libraryguide.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 06/01/2018.
 */

public class LibraryModel implements Serializable {
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("library_photo")
    private String user_photo;
    @SerializedName("library_name")
    private String lib_name;
    @SerializedName("library_email")
    private String lib_email;
    @SerializedName("library_country")
    private String lib_country;
    @SerializedName("library_address")
    private String lib_address;
    @SerializedName("library_phone")
    private String lib_phone;
    @SerializedName("type_title")
    private String lib_type;
    @SerializedName("user_username")
    private String lib_username;
    private String lib_password;
    @SerializedName("library_google_lat")
    private String lat;
    @SerializedName("library_google_lng")
    private String lng;

    private String user_token;


    public LibraryModel() {
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public LibraryModel(String user_type, String user_photo, String lib_name, String lib_email, String lib_country, String lib_address, String lib_phone, String lib_type, String lib_username, String lib_password, String lat, String lng) {
        this.user_type = user_type;
        this.user_photo = user_photo;

        this.lib_name = lib_name;
        this.lib_email = lib_email;
        this.lib_country = lib_country;
        this.lib_address = lib_address;
        this.lib_phone = lib_phone;
        this.lib_type = lib_type;
        this.lib_username = lib_username;
        this.lib_password = lib_password;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public String getLib_email() {
        return lib_email;
    }

    public void setLib_email(String lib_email) {
        this.lib_email = lib_email;
    }

    public String getLib_country() {
        return lib_country;
    }

    public void setLib_country(String lib_country) {
        this.lib_country = lib_country;
    }

    public String getLib_address() {
        return lib_address;
    }

    public void setLib_address(String lib_address) {
        this.lib_address = lib_address;
    }

    public String getLib_phone() {
        return lib_phone;
    }

    public void setLib_phone(String lib_phone) {
        this.lib_phone = lib_phone;
    }

    public String getLib_type() {
        return lib_type;
    }

    public void setLib_type(String lib_type) {
        this.lib_type = lib_type;
    }

    public String getLib_username() {
        return lib_username;
    }

    public void setLib_username(String lib_username) {
        this.lib_username = lib_username;
    }

    public String getLib_password() {
        return lib_password;
    }

    public void setLib_password(String lib_password) {
        this.lib_password = lib_password;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
