package com.example.omd.library.Models;

import java.io.Serializable;

/**
 * Created by Delta on 06/01/2018.
 */

public class PublisherModel implements Serializable {
    private String pub_id;
    private String user_type;
    private String pub_firstName;
    private String pub_lastName;
    private String pub_email;
    private String pub_phone;
    private String pub_country;
    private String pub_expertise;
    private String pub_website;
    private String pub_password;

    public PublisherModel() {
    }

    public PublisherModel(String pub_firstName, String pub_lastName, String pub_email, String pub_phone, String pub_country, String pub_expertise, String pub_website, String pub_password) {
        this.pub_firstName = pub_firstName;
        this.pub_lastName = pub_lastName;
        this.pub_email = pub_email;
        this.pub_phone = pub_phone;
        this.pub_country = pub_country;
        this.pub_expertise = pub_expertise;
        this.pub_website = pub_website;
        this.pub_password = pub_password;
    }
    public String getPub_id() {
        return pub_id;
    }

    public void setPub_id(String pub_id) {
        this.pub_id = pub_id;
    }
    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPub_firstName() {
        return pub_firstName;
    }

    public void setPub_firstName(String pub_firstName) {
        this.pub_firstName = pub_firstName;
    }

    public String getPub_lastName() {
        return pub_lastName;
    }

    public void setPub_lastName(String pub_lastName) {
        this.pub_lastName = pub_lastName;
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

    public String getPub_expertise() {
        return pub_expertise;
    }

    public void setPub_expertise(String pub_expertise) {
        this.pub_expertise = pub_expertise;
    }

    public String getPub_website() {
        return pub_website;
    }

    public void setPub_website(String pub_website) {
        this.pub_website = pub_website;
    }

    public String getPub_password() {
        return pub_password;
    }

    public void setPub_password(String pub_password) {
        this.pub_password = pub_password;
    }
}
