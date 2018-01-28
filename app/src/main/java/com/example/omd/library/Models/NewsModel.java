package com.example.omd.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 22/01/2018.
 */

public class NewsModel implements Serializable {
    @SerializedName("library_news_title")
    private String news_title;
    @SerializedName("library_news_photo")
    private String news_photo;
    @SerializedName("library_news_date")
    private String news_date;
    @SerializedName("library_news_details")
    private String news_details;


    public NewsModel() {
    }

    public NewsModel(String news_title, String news_photo, String news_date, String news_details) {
        this.news_title = news_title;
        this.news_photo = news_photo;
        this.news_date = news_date;
        this.news_details = news_details;

    }

    public String getnews_title() {
        return news_title;
    }

    public void setnews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getnews_photo() {
        return news_photo;
    }

    public void setnews_photo(String news_photo) {
        this.news_photo = news_photo;
    }


    public String getnews_date() {
        return news_date;
    }

    public void setJobStartData(String news_date) {
        this.news_date = news_date;
    }


    public String getnews_details() {
        return news_details;
    }

    public void setnews_details(String news_details) {
        this.news_details = news_details;
    }

}