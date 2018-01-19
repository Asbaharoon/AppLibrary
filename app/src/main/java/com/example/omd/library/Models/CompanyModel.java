package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 12/01/2018.
 */

public class CompanyModel implements Serializable {
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("company_name")
    private String comp_name;
    @SerializedName("user_email")
    private String comp_email;
    @SerializedName("company_phone")
    private String comp_phone;
    @SerializedName("company_country")
    private String comp_country;
    @SerializedName("company_address")
    private String comp_address;
    @SerializedName("company_town")
    private String comp_town;
    @SerializedName("company_site")
    private String comp_site;
    @SerializedName("user_username")
    private String comp_username;
    @SerializedName("user_pass")
    private String comp_password;
    @SerializedName("company_google_lat")
    private String comp_lat;
    @SerializedName("company_google_lng")
    private String comp_lng;

    public CompanyModel() {
    }

    public CompanyModel(String user_type, String comp_name, String comp_email, String comp_phone, String comp_country, String comp_address, String comp_town, String comp_site, String comp_username, String comp_password, String comp_lat, String comp_lng) {
        this.user_type = user_type;
        this.comp_name = comp_name;
        this.comp_email = comp_email;
        this.comp_phone = comp_phone;
        this.comp_country = comp_country;
        this.comp_address = comp_address;
        this.comp_town = comp_town;
        this.comp_site = comp_site;
        this.comp_username = comp_username;
        this.comp_password = comp_password;
        this.comp_lat = comp_lat;
        this.comp_lng = comp_lng;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_email() {
        return comp_email;
    }

    public void setComp_email(String comp_email) {
        this.comp_email = comp_email;
    }

    public String getComp_phone() {
        return comp_phone;
    }

    public void setComp_phone(String comp_phone) {
        this.comp_phone = comp_phone;
    }

    public String getComp_country() {
        return comp_country;
    }

    public void setComp_country(String comp_country) {
        this.comp_country = comp_country;
    }

    public String getComp_address() {
        return comp_address;
    }

    public void setComp_address(String comp_address) {
        this.comp_address = comp_address;
    }

    public String getComp_town() {
        return comp_town;
    }

    public void setComp_town(String comp_town) {
        this.comp_town = comp_town;
    }

    public String getComp_site() {
        return comp_site;
    }

    public void setComp_site(String comp_site) {
        this.comp_site = comp_site;
    }

    public String getComp_username() {
        return comp_username;
    }

    public void setComp_username(String comp_username) {
        this.comp_username = comp_username;
    }

    public String getComp_password() {
        return comp_password;
    }

    public void setComp_password(String comp_password) {
        this.comp_password = comp_password;
    }

    public String getComp_lat() {
        return comp_lat;
    }

    public void setComp_lat(String comp_lat) {
        this.comp_lat = comp_lat;
    }

    public String getComp_lng() {
        return comp_lng;
    }

    public void setComp_lng(String comp_lng) {
        this.comp_lng = comp_lng;
    }
}
