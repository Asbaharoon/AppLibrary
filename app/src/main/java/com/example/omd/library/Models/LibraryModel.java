package com.example.omd.library.Models;

import java.io.Serializable;

/**
 * Created by Delta on 06/01/2018.
 */

public class LibraryModel implements Serializable {
    private String lib_id;
    private String user_type;
    private String lib_name;
    private String lib_email;
    private String lib_commission;
    private String lib_country;
    private String lib_expertise;
    private String lib_type;
    private String lib_password;

    public LibraryModel() {
    }


    public LibraryModel(String lib_name, String lib_email, String lib_commission, String lib_country, String lib_expertise, String lib_type, String lib_password) {
        this.lib_name = lib_name;
        this.lib_email = lib_email;
        this.lib_commission = lib_commission;
        this.lib_country = lib_country;
        this.lib_expertise = lib_expertise;
        this.lib_type = lib_type;
        this.lib_password = lib_password;
    }

    public String getLib_id() {
        return lib_id;
    }

    public void setLib_id(String lib_id) {
        this.lib_id = lib_id;
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

    public String getLib_commission() {
        return lib_commission;
    }

    public void setLib_commission(String lib_commission) {
        this.lib_commission = lib_commission;
    }

    public String getLib_country() {
        return lib_country;
    }

    public void setLib_country(String lib_country) {
        this.lib_country = lib_country;
    }

    public String getLib_expertise() {
        return lib_expertise;
    }

    public void setLib_expertise(String lib_expertise) {
        this.lib_expertise = lib_expertise;
    }

    public String getLib_type() {
        return lib_type;
    }

    public void setLib_type(String lib_type) {
        this.lib_type = lib_type;
    }

    public String getLib_password() {
        return lib_password;
    }

    public void setLib_password(String lib_password) {
        this.lib_password = lib_password;
    }

}
