package com.semicolon.librarians.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 27/01/2018.
 */

public class CountriesModel implements Serializable {
    @SerializedName("id")
    private String country_id;
    @SerializedName("flag")
    private String country_flag;
    @SerializedName("title")
    private String country_title;

    public CountriesModel() {
    }

    public CountriesModel(String country_id, String country_flag, String country_title) {
        this.country_id = country_id;
        this.country_flag = country_flag;
        this.country_title = country_title;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String getCountry_title() {
        return country_title;
    }

    public void setCountry_title(String country_title) {
        this.country_title = country_title;
    }
}
