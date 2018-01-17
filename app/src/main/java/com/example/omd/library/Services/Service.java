package com.example.omd.library.Services;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    Call<JsonObject> login(@FieldMap Map<String,String> map);
}
