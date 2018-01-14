package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 06/01/2018.
 */

public class PublisherModel implements Serializable {
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("publisher_name")
    private String pub_name;
    @SerializedName("publisher_email")
    private String pub_email;
    @SerializedName("publisher_phone")
    private String pub_phone;
    @SerializedName("publisher_country")
    private String pub_country;
    @SerializedName("publisher_address")
    private String pub_address;
    private String pub_town;
    private String pub_website;
    @SerializedName("user_username")
    private String pub_username;
    private String pub_password;

    public PublisherModel() {
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public String getPub_email() {
        return pub_email;
    }

    public void setPub_email(String pub_email) {
        this.pub_email = pub_email;
    }

    public String getPub_phone() {
        return pub_phone;
    }

    public void setPub_phone(String pub_phone) {
        this.pub_phone = pub_phone;
    }

    public String getPub_country() {
        return pub_country;
    }

    public void setPub_country(String pub_country) {
        this.pub_country = pub_country;
    }

    public String getPub_address() {
        return pub_address;
    }

    public void setPub_address(String pub_address) {
        this.pub_address = pub_address;
    }

    public String getPub_town() {
        return pub_town;
    }

    public void setPub_town(String pub_town) {
        this.pub_town = pub_town;
    }

    public String getPub_website() {
        return pub_website;
    }

    public void setPub_website(String pub_website) {
        this.pub_website = pub_website;
    }

    public String getPub_username() {
        return pub_username;
    }

    public void setPub_username(String pub_username) {
        this.pub_username = pub_username;
    }

    public String getPub_password() {
        return pub_password;
    }

    public void setPub_password(String pub_password) {
        this.pub_password = pub_password;
    }
}
