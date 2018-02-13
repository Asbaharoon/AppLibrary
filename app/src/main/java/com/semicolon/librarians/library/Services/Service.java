package com.semicolon.librarians.library.Services;

import com.semicolon.librarians.library.Models.CommonUsersData;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.CountriesModel;
import com.semicolon.librarians.library.Models.JobsModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.LibraryServices_Model;
import com.semicolon.librarians.library.Models.LibraryType_Model;
import com.semicolon.librarians.library.Models.NewsModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
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
    Call<NormalUserData> UploadUserDataWithFacebook(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/gmaillogin")
    Call<NormalUserData> UploadUserDataWithGoogle(@FieldMap Map<String,String> map);

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

    //////////////////////----------Jobs-------------///////////////////////////
    @GET("api/service/jobs")
    Call<List<JobsModel>> getJobsData();

    //////////////////////----------News-------------///////////////////////////
    @GET("api/service/library_news")
    Call<List<NewsModel>> getNewsData();

    //////////////////////----------Publisher-------////////////////////////////
    @GET("api/service/publishers")
    Call<List<PublisherModel>> getPublisherData();

    @FormUrlEncoded
    @POST("api/publisher/")
    Call<List<PublisherModel>> getPublisherSearch_Data(@FieldMap Map<String,String> map);

    ////////////////////------------Countries-------///////////////////////////
    @GET("api/service/countries")
    Call<List<CountriesModel>> getCountryData();
    ///////////////////-------------Companies-------///////////////////////////

    @GET("api/service/companies")
    Call<List<CompanyModel>> getCompanyData();

    //////////////////-------------Universities-----///////////////////////////
    @GET("api/service/universities")
    Call<List<UniversityModel>> getUniversityData();

    @FormUrlEncoded
    @POST("api/universties")
    Call<List<UniversityModel>> getUniversitySearch_Data(@FieldMap Map<String,String> map);


    /////////////////--------------LibraryTypes-----///////////////////////////
    @GET("api/service/lib_types")
    Call<List<LibraryType_Model>> getLibraryTypeData();

    /////////////////--------------LibraryTypes-----///////////////////////////
    @GET("api/service/lib_services")
    Call<List<LibraryServices_Model>> getLibraryServicesData();

    @FormUrlEncoded
    @POST("api/searchlibrary")
    Call<List<LibraryModel>> getLibrarySearch_Data(@FieldMap Map<String,String> map);
    ////////////////-----------DisplayAllUsersData----////////////////////////
    @FormUrlEncoded
    @POST("api/getuserbyid/")
    Call<NormalUserData>DisplayUserById(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/getuserbyid/")
    Call<PublisherModel>DisplayPublisherById(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/getuserbyid/")
    Call<LibraryModel>DisplayLibraryById(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/getuserbyid/")
    Call<UniversityModel>DisplayUniversityById(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("api/getuserbyid/")
    Call<CompanyModel>DisplayCompanyById(@FieldMap Map<String,String> map);

    ////////////////---------updateFB_GM_userData------/////////////////////////
    @FormUrlEncoded
    @POST("/api/updateuser")
    Call<NormalUserData>Update_FB_GM_UserData(@FieldMap Map<String ,String> map);

    /////////////////////-------CreateChatRoom---------/////////////////////////

    @FormUrlEncoded
    @POST("/api/chatroom")
    Call<ResponseBody>CreateChatRoom(@FieldMap Map<String,String> map);

    ////////////////////--------DisplayAllRooms--------////////////////////////

    @FormUrlEncoded
    @POST("/api/getallchatrooms/")
    Call<List<CommonUsersData>>DisplayAllRooms(@Field("friend_id_fk1") String currUserId);
}
