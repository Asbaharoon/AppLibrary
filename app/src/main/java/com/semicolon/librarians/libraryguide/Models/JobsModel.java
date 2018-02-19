package com.semicolon.librarians.libraryguide.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Delta on 22/01/2018.
 */

public class JobsModel implements Serializable {
    @SerializedName("job_title")
    private String jobTitle;
    @SerializedName("job_img")
    private String jobImage;
    private String jobDate;
    @SerializedName("job_date")
    private String jobStartDate;
    @SerializedName("job_end_date")
    private String jobEndDate;
    @SerializedName("company_name")
    private String job_companyName;
    @SerializedName("job_requires")
    private String jobRequirements;
    @SerializedName("job_phone")
    private String jobPhone;
    @SerializedName("job_email")
    private String jobEmail;

    public JobsModel() {
    }

    public JobsModel(String jobTitle, String jobImage, String jobDate, String jobStartDate, String jobEndDate, String job_companyName, String jobRequirements, String jobPhone, String jobEmail) {
        this.jobTitle = jobTitle;
        this.jobImage = jobImage;
        this.jobDate = jobDate;
        this.jobStartDate = jobStartDate;
        this.jobEndDate = jobEndDate;
        this.job_companyName = job_companyName;
        this.jobRequirements = jobRequirements;
        this.jobPhone = jobPhone;
        this.jobEmail = jobEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobImage() {
        return jobImage;
    }

    public void setJobImage(String jobImage) {
        this.jobImage = jobImage;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartData(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public String getJobEndDate() {
        return jobEndDate;
    }

    public void setJobEndDate(String jobEndDate) {
        this.jobEndDate = jobEndDate;
    }

    public String getJob_companyName() {
        return job_companyName;
    }

    public void setJob_companyName(String job_companyName) {
        this.job_companyName = job_companyName;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getJobPhone() {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
        this.jobPhone = jobPhone;
    }

    public String getJobEmail() {
        return jobEmail;
    }

    public void setJobEmail(String jobEmail) {
        this.jobEmail = jobEmail;
    }
}
