package com.example.omd.library.Services;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.JobsModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.PublishersModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.Models.newsModel;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Delta on 23/12/2017.
 */

public interface Service {
    @FormUrlEncoded
    @POST("api/facebooklogin")
    Call<JsonObject> UploadUserDataWithFacebook(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/gmaillogin")
    Call<JsonObject> UploadUserDataWithGoogle(@FieldMap Map<String,String> map);

    /////////////////////---------Registration-----////////////////////////

    @FormUrlEncoded
    @POST("api/registration")
    Call<NormalUserData>NormalUserRegistration(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/registration")
    Call<PublisherModel>PublisherRegistration(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/registration")
    Call<LibraryModel>LibraryRegistration(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/registration")
    Call<UniversityModel>UniversityRegistration(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/registration")
    Call<CompanyModel>CompanyRegistration(@FieldMap Map<String,String> map);

    /////////////////////---------Login-----////////////////////////
    @FormUrlEncoded
    @POST("api/auth")
    Call<NormalUserData> loginUser(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("api/auth")
    Call<PublisherModel> loginPublisher(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("api/auth")
    Call<LibraryModel> loginLibrary(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("api/auth")
    Call<UniversityModel> loginUniversity(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("api/auth")
    Call<CompanyModel> loginCompany(@FieldMap Map<String,String> map);

    /////////////////////---------nearby-----////////////////////////

    @FormUrlEncoded
    @POST("api/nearby")
    Call<List<NormalUserData>>NearbyUsers(@Field("user_type") String userType);

    @FormUrlEncoded
    @POST("api/nearby")
    Call<List<PublisherModel>>NearbyPublishers(@Field("user_type") String userType);

    @FormUrlEncoded
    @POST("api/nearby")
    Call<List<LibraryModel>>NearbyLibraries(@Field("user_type") String userType);

    @FormUrlEncoded
    @POST("api/nearby")
    Call<List<UniversityModel>>NearbyUniversities(@Field("user_type") String userType);

    @FormUrlEncoded
    @POST("api/nearby")
    Call<List<CompanyModel>>NearbyCompanies(@Field("user_type") String userType);

    //////////////////////----------Jobs-------////////////////////////////
    @GET("api/service/jobs")
    Call<List<JobsModel>> getJobsData();

    //////////////////////----------News-------////////////////////////////
    @GET("api/service/library_news")
    Call<List<newsModel>> getnewsData();

    //////////////////////----------Publisher-------////////////////////////////
    @GET("api/service/publishers")
    Call<List<PublishersModel>> getPublisherData();
}
