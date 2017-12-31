package com.example.omd.library.Services;

import com.example.omd.library.Models.User;

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
    Call<User> UploadUserDataWithFacebook(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/gmaillogin")
    Call<User> UploadUserDataWithGoogle(@FieldMap Map<String,String> map);

}
