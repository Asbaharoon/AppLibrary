package com.semicolon.librarians.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 29/01/2018.
 */

public class LibraryServices_Model implements Serializable {
    @SerializedName("service_id")
    private String lib_service_id;
    @SerializedName("service_title")
    private String lib_service_title;

    public LibraryServices_Model() {
    }

    public LibraryServices_Model(String lib_service_id, String lib_service_title) {
        this.lib_service_id = lib_service_id;
        this.lib_service_title = lib_service_title;
    }

    public String getLib_service_id() {
        return lib_service_id;
    }

    public void setLib_service_id(String lib_service_id) {
        this.lib_service_id = lib_service_id;
    }

    public String getLib_service_title() {
        return lib_service_title;
    }

    public void setLib_service_title(String lib_service_title) {
        this.lib_service_title = lib_service_title;
    }
}
