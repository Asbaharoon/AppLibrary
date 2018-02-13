package com.semicolon.librarians.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 16/12/2017.
 */

public class NormalUserData implements Serializable {
    @SerializedName("user_username")
    private String userId;
    @SerializedName("user_photo")
    private String user_photo;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_country")
    private String userCountry;
    @SerializedName("user_phone")
    private String userPhone;
    @SerializedName("photo_link")
    private String userPhoto;
    @SerializedName("user_type")
    private String userType;
    @SerializedName("user_google_lat")
    private String user_google_lat;
    @SerializedName("user_google_lng")
    private String user_google_lng;


    public NormalUserData() {
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public NormalUserData(String userName, String user_photo, String userEmail, String userCountry, String userPhone, String userPhoto, String userType, String user_google_lat, String user_google_lng) {
        this.userName = userName;
        this.user_photo = user_photo;

        this.userEmail = userEmail;
        this.userCountry = userCountry;
        this.userPhone = userPhone;
        this.userPhoto = userPhoto;
        this.userType = userType;
        this.user_google_lat = user_google_lat;
        this.user_google_lng = user_google_lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
