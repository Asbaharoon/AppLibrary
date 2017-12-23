package com.example.omd.library.Services;

import com.example.omd.library.Models.NormalUserData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Delta on 23/12/2017.
 */

public interface UserClient {
    @FormUrlEncoded
    @POST("api/facebooklogin")
    Call<NormalUserData> UploadUserDataWithFacebook(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/gmaillogin")
    Call<NormalUserData> UploadUserDataWithGoogle(@FieldMap Map<String,String> map);

}
