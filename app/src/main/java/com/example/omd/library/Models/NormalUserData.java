package com.example.omd.library.Models;

import java.io.Serializable;

/**
 * Created by Delta on 16/12/2017.
 */

public class NormalUserData implements Serializable {
    private String userId;
    private String userName;
    private String userEmail;
    private String userCountry;
    private String userPhone;
    private String userJob;
    private String userInterests;
    private String userPhoto;

    public NormalUserData() {
    }

    public NormalUserData(String userName, String userEmail, String userCountry, String userPhone, String userJob, String userInterests, String userPhoto) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCountry = userCountry;
        this.userPhone = userPhone;
        this.userJob = userJob;
        this.userInterests = userInterests;
        this.userPhoto = userPhoto;
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

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(String userInterests) {
        this.userInterests = userInterests;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
