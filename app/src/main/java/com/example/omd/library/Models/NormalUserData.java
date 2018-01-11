package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 16/12/2017.
 */

public class NormalUserData implements Serializable {
    @SerializedName("user_username")
    private String userId;
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


    public NormalUserData() {
    }

    public NormalUserData(String userName, String userEmail, String userCountry, String userPhone, String userPhoto) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCountry = userCountry;
        this.userPhone = userPhone;

        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
}
