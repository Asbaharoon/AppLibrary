package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 29/01/2018.
 */

public class LibraryType_Model implements Serializable {
    @SerializedName("type_id_pk")
    private String lib_type_id;
    @SerializedName("type_title")
    private String lib_type_title;

    public LibraryType_Model() {
    }

    public LibraryType_Model(String lib_type_id, String lib_type_title) {
        this.lib_type_id = lib_type_id;
        this.lib_type_title = lib_type_title;
    }

    public String getLib_type_id() {
        return lib_type_id;
    }

    public void setLib_type_id(String lib_type_id) {
        this.lib_type_id = lib_type_id;
    }

    public String getLib_type_title() {
        return lib_type_title;
    }

    public void setLib_type_title(String lib_type_title) {
        this.lib_type_title = lib_type_title;
    }
}
