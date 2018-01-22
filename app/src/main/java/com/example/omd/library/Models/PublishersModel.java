package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 06/01/2018.
 */

public class PublishersModel implements Serializable {

    @SerializedName("publisher_name")
    private String pub_name;
    @SerializedName("publisher_email")
    private String pub_email;
    @SerializedName("publisher_phone")
    private String pub_phone;
    @SerializedName("publisher_country")
    private String pub_country;
    @SerializedName("publisher_site")
    private String pub_website;


    public PublishersModel() {
    }


    public PublishersModel( String pub_name, String pub_email, String pub_phone, String pub_country, String pub_website) {
        this.pub_name = pub_name;
        this.pub_email = pub_email;
        this.pub_phone = pub_phone;
        this.pub_country = pub_country;
        this.pub_website = pub_website;
    }



    public static String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public static String getPub_email() {
        return pub_email;
    }

    public void setPub_email(String pub_email) {
        this.pub_email = pub_email;
    }

    public static String getPub_phone() {
        return pub_phone;
    }

    public void setPub_phone(String pub_phone) {
        this.pub_phone = pub_phone;
    }

    public static String getPub_country() {
        return pub_country;
    }

    public void setPub_country(String pub_country) {
        this.pub_country = pub_country;
    }

    public static String getPub_website() {
        return pub_website;
    }

    public void setPub_website(String pub_website) {
        this.pub_website = pub_website;
    }
}
