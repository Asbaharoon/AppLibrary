package com.semicolon.librarians.library.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 14/02/2018.
 */

public class MessageModel implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("sent")
    private String date;
    private String seen;

    public MessageModel() {

    }

    public MessageModel(String message, String from, String to, String date, String seen) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
        this.seen = seen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }
}
