package com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Registration;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.ErrorUtils;
import com.semicolon.librarians.libraryguide.Services.NetworkConnection;
import com.semicolon.librarians.libraryguide.Services.Service;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 24/12/2017.
 */

public class Model_InteractorImp implements Model_Interactor {

    private boolean isConnected =false;


    @Override
    public void NormalUserRegistration(String userType,String photo, String first_name, String last_name, String email, String country, String phone, String username, String password,String lat,String lng,String token, onCompleteListener listener, Context context) {
        Log.e("Regest user data",userType+photo+first_name+last_name+email+phone+country+username+password+lat+lng+token);

        if (TextUtils.isEmpty(photo))
        {
            listener.setNormalUserPhoto_Error();
        }
        else if (TextUtils.isEmpty(first_name))
        {
            listener.setNormalUserFirstName_Error();
        }
        else if (!first_name.matches(Tags.name_Regex))
        {
            listener.setNormalUser_invalidFirstName_Error();
        }
        else if (TextUtils.isEmpty(last_name))
        {
            listener.setNormalUserLastName_Error();
        }
        else if (!last_name.matches(Tags.name_Regex))
        {
            listener.setNormalUser_invalidLastName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setNormalUserEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setNormalUser_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(phone))
        {
            listener.setNormalUserPhone_Error();
        }
        else if (TextUtils.isEmpty(country))
        {
            listener.setNormalUserCountry_Error();
        }
        else if (TextUtils.isEmpty(username))
        {
            listener.setNormalUser_username_Error();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setNormalUser_invalidUsername_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setNormalUserPassword_Error();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setNormalUser_invalidPassword_Error();
        }
        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                listener.showProgress_Dialog();
                Registration_NormalUser(userType,photo,first_name,last_name, email,country,phone,username,password,lat,lng, token,context, listener);

            }else
            {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.network_connection));
            }
        }
    }

    @Override
    public void PublisherRegistration(String userType,String photo, String first_name, String last_name, String email, String country, String password, String phone, String username, String address, String town, String website_url, String lat, String lng,String token, onCompleteListener listener, Context context) {
        Log.e("Regest pubdata",userType+photo+first_name+last_name+email+phone+country+town+username+password+address+lat+lng+token);

        if (TextUtils.isEmpty(photo))
        {
            listener.setPublisherPhoto_Error();
        }
        else if (TextUtils.isEmpty(first_name))
        {
            listener.setPublisherFirstName_Error();
        }
        else if (!first_name.matches(Tags.name_Regex))
        {
            listener.setPublisher_invalidFirstName_Error();
        }
        else if (TextUtils.isEmpty(last_name))
        {
            listener.setPublisherLastName_Error();
        }
        else if (!last_name.matches(Tags.name_Regex))
        {
            listener.setPublisher_invalidLastName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setPublisherEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setPublisher_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(phone))
        {
            listener.setPublisherPhone_Error();
        }
        else if (TextUtils.isEmpty(country))
        {
            listener.setPublisherCountry_Error();
        }
        else if (TextUtils.isEmpty(address))
        {
            listener.setPublisherAddress_Error();
        }
        else if (TextUtils.isEmpty(town))
        {
            listener.setPublisherTown_Error();
        }
        else if (TextUtils.isEmpty(website_url))
        {
            listener.setPublisher_site_Error();
        }
        else if (!website_url.matches(Tags.url_Regex))
        {
            listener.setPublisher_invalidSite_Error();
        }
        else if (TextUtils.isEmpty(username))
        {
            listener.setPublisher_username_Error();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setPublisher_invalidUsername_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setPublisherPassword_Error();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setPublisher_invalidPassword_Error();
        }
        else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
        {
            listener.setPublisherLat_LngError();
        }

        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected) {
                listener.showProgress_Dialog();
                Registration_PublisherData(userType,photo,first_name,last_name,email,country,password,phone,username,address,town,website_url,lat,lng,token,context, listener);
            }else
            {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.network_connection));
            }
        }
    }


    @Override
    public void LibraryRegistration(String userType,String photo, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libUsername, String libPassword, String lat, String lng,String token, onCompleteListener listener, Context context) {

        Log.e("Regest libdata",userType+photo+libName+libEmail+libPhone+libCountry+libAddress+libType+libUsername+libPassword+lat+lng+token);
        if (TextUtils.isEmpty(photo))
        {
            listener.setLibraryPhoto_Error();
        }
        else if (TextUtils.isEmpty(libName))
        {
            listener.setLibraryName_Error();
        }
        else if (!libName.matches(Tags.name_Regex))
        {
            listener.setLibrary_invalidName_Error();
        }
        else if (TextUtils.isEmpty(libType))
        {
            listener.setLibraryType_Error();

        }
        else if (TextUtils.isEmpty(libEmail))
        {
            listener.setLibraryEmail_Error();
        }
        else if (!libEmail.matches(Tags.email_Regex))
        {
            listener.setLibrary_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(libPhone))
        {
            listener.setLibraryPhone_Error();
        }
        else if (TextUtils.isEmpty(libCountry))
        {
            listener.setLibraryCountry_Error();
        }
        else if (TextUtils.isEmpty(libAddress))
        {
            listener.setLibraryAddress_Error();
        }


        else if (TextUtils.isEmpty(libUsername))
        {
            listener.setLibraryUsername_Error();

        }else if (!libUsername.matches(Tags.username_Regex))
        {
            listener.setLibrary_invalidUsername_Error();
        }
        else if (TextUtils.isEmpty(libPassword))
        {
            listener.setLibraryPassword_Error();
        }else if (!libPassword.matches(Tags.pass_Regex))
        {
            listener.setLibrary_invalidPassword_Error();
        }
        else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                listener.hideProgress_Dialog();
                listener.setLibraryLat_lng_Error();

            }else
            {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.network_connection));
            }
        }
        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                listener.showProgress_Dialog();
                Registration_LibraryData(userType,photo,libName,libEmail,libPhone,libCountry,libAddress,libType,libUsername,libPassword,lat,lng,token,context,listener);

            }else
            {
                listener.hideProgress_Dialog();
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
    }

    @Override
    public void UniversityRegistration(String userType, String photo,String name, String email, String country, String phone, String username, String password, String major, String address, String site, String lat, String lng,String token, onCompleteListener listener, Context context) {
        Log.e("Regest unidata",userType+photo+name+email+phone+country+username+password+major+address+lat+lng+site+token);

        if (TextUtils.isEmpty(photo))
        {
            listener.setUniversityPhoto_Error();
        }
        else if (TextUtils.isEmpty(name))
        {
            listener.setUniversityName_Error();
        }
        else if (!name.matches(Tags.name_Regex))
        {
            listener.setUniversity_invalidName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setUniversityEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setUniversity_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(phone))
        {
            listener.setUniversityPhone_Error();
        }
        else if (TextUtils.isEmpty(major))
        {
            listener.setUniversityMajor_Error();
        }
        else if (TextUtils.isEmpty(country))
        {
            listener.setUniversityCountry_Error();
        }
        else if (TextUtils.isEmpty(address))
        {
            listener.setUniversityAddress_Error();
        }
        else if (TextUtils.isEmpty(site))
        {
            listener.setUniversitySite_Error();
        }
        else if (!site.matches(Tags.url_Regex))
        {
            listener.setUniversity_invalidSite_Error();
        }
        else if (TextUtils.isEmpty(username))
        {
            listener.setUniversityUsername_Error();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setUniversity_invalidUsername_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setUniversityPassword_Error();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setUniversity_invalidPassword_Error();
        }
        else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
        {
            listener.setUniversityLat_Lng_Error();
        }
        else
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    listener.showProgress_Dialog();
                    Registration_UniversityData(userType,photo,name,email,country,phone,username,password,major,address,site,lat,lng,token,context,listener);
                }else
                {
                    listener.hideProgress_Dialog();
                    listener.onFailed(context.getString(R.string.network_connection));
                }
            }
    }

    @Override
    public void CompanyRegistration(String userType, String photo,String name, String email, String country, String phone, String username, String password, String address, String town, String site,String lat,String lng,String token, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(photo))
        {
            listener.setCompanyPhoto_Error();
        }
        else if (TextUtils.isEmpty(name))
        {
            listener.setCompanyName_Error();
        }
        else if (!name.matches(Tags.name_Regex))
        {
            listener.setCompany_invalidName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setCompanyEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setCompany_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(phone))
        {
            listener.setCompanyPhone_Error();
        }

        else if (TextUtils.isEmpty(country))
        {
            listener.setCompanyCountry_Error();
        }
        else if (TextUtils.isEmpty(address))
        {
            listener.setCompanyAddress_Error();
        }
        else if (TextUtils.isEmpty(town))
        {
            listener.setCompanyTown_Error();
        }
        else if (TextUtils.isEmpty(site))
        {
            listener.setCompanySite_Error();
        }
        else if (!site.matches(Tags.url_Regex))
        {
            listener.setCompany_invalidSite_Error();
        }
        else if (TextUtils.isEmpty(username))
        {
            listener.setCompanyUsername_Error();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setCompany_invalidUsername_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setCompanyPassword_Error();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setCompany_invalidPassword_Error();
        }else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
        {
            listener.setCompanyLat_Lng_Error();
        }

        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                listener.showProgress_Dialog();
                Registration_CompanyData(userType,photo,name,email,country,phone,username,password,address,town,site,lat,lng,token,context,listener);
            }else
            {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.network_connection));
            }
        }
    }


    private void Registration_NormalUser(String userType, String photo, String first_name, String last_name, String email, String country , String phone, String username, String password, String lat, String lng, String token, final Context context, final onCompleteListener listener)
    {

        Map<String,String> userMap = new HashMap<>();

        userMap.put("user_token",token);
        userMap.put("user_photo",photo);
        userMap.put("user_type","user");
        userMap.put("user_name",first_name+" "+last_name);
        userMap.put("user_email",email);
        userMap.put("user_country",country);
        userMap.put("user_phone",phone);
        //userMap.put("user_photo",photo);
        userMap.put("user_username",username);
        userMap.put("user_pass",password);
        userMap.put("user_google_lat",lat);
        userMap.put("user_google_lng",lng);


        final Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<NormalUserData> userDataCall = service.NormalUserRegistration(userMap);
        userDataCall.enqueue(new Callback<NormalUserData>() {
            @Override
            public void onResponse(Call<NormalUserData> call, final Response<NormalUserData> response) {
                if (response.isSuccessful())
                {
                    Log.e("user_register","1");
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.hideProgress_Dialog();

                            listener.onNormalUserDataSuccess(response.body());

                        }
                    },500);

                }
                else {
                    Log.e("user_register","0");

                    listener.hideProgress_Dialog();
                    Converter<ResponseBody,ErrorUtils> converter = retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                    try {
                        ErrorUtils errorUtils = converter.convert(response.errorBody());
                        listener.onFailed(errorUtils.getErrorMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<NormalUserData> call, Throwable t) {
                Log.e("user_register","2");

                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });
    }
    private void Registration_PublisherData(String userType, String photo, String first_name, String last_name, String email, String country, String password, String phone, String username, String address, String town, String website_url, String lat, String lng, String token, final Context context, final onCompleteListener listener)
    {
        Map<String,String> pubMap = new HashMap<>();

        pubMap.put("user_token",token);
        pubMap.put("user_type","publisher");
        pubMap.put("user_photo",photo);
        pubMap.put("user_name",first_name+" "+last_name);
        pubMap.put("user_email",email);
        pubMap.put("user_country",country);
        pubMap.put("user_phone",phone);
        pubMap.put("user_username",username);
        pubMap.put("user_pass",password);
        pubMap.put("user_google_lat",lat);
        pubMap.put("user_google_lng",lng);

        pubMap.put("publisher_name",first_name+" "+last_name);
        pubMap.put("publisher_country",country);
        pubMap.put("publisher_address",address);
        pubMap.put("publisher_phone",phone);
        pubMap.put("publisher_email",email);
        pubMap.put("publisher_site",website_url);
        pubMap.put("publisher_town",town);
        pubMap.put("publisher_google_lat",lat);
        pubMap.put("publisher_google_lng",lng);
        pubMap.put("user_type",userType);

        final Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<PublisherModel> publisherModelCall = service.PublisherRegistration(pubMap);
        publisherModelCall.enqueue(new Callback<PublisherModel>() {
            @Override
            public void onResponse(Call<PublisherModel> call, final Response<PublisherModel> response) {
                if (response.isSuccessful())
                {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.hideProgress_Dialog();

                            listener.onPublisherDataSuccess(response.body());

                        }
                    },500);
                }else {

                    listener.hideProgress_Dialog();
                    Converter<ResponseBody,ErrorUtils> converter = retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                    try {
                        ErrorUtils errorUtils = converter.convert(response.errorBody());
                        listener.onFailed(errorUtils.getErrorMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<PublisherModel> call, Throwable t) {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });
    }
    private void Registration_LibraryData(String userType, String photo, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libUsername, String libPassword, String lat, String lng, String token, final Context context, final onCompleteListener listener)
    {
        Map<String,String> libMap = new HashMap<>();

        libMap.put("user_token",token);
        libMap.put("user_type","library");
        libMap.put("user_photo",photo);
        libMap.put("user_name",libName);
        libMap.put("user_email",libEmail);
        libMap.put("user_country",libCountry);
        libMap.put("user_phone",libPhone);
        libMap.put("user_username",libUsername);
        libMap.put("user_pass",libPassword);
        libMap.put("user_google_lat",lat);
        libMap.put("user_google_lng",lng);

        libMap.put("library_name",libName);
        libMap.put("library_address",libAddress);
        libMap.put("library_country",libCountry);
        libMap.put("library_email",libEmail);
        libMap.put("library_phone",libPhone);
        libMap.put("library_type",libType);
        libMap.put("library_google_lat",lat);
        libMap.put("library_google_lng",lng);


        final Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<LibraryModel> libraryModelCall = service.LibraryRegistration(libMap);
        libraryModelCall.enqueue(new Callback<LibraryModel>() {
            @Override
            public void onResponse(Call<LibraryModel> call, final Response<LibraryModel> response) {
                if (response.isSuccessful())
                {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.hideProgress_Dialog();

                            listener.onLibraryDataSuccess(response.body());

                        }
                    },500);
                }else {
                    listener.hideProgress_Dialog();
                    Converter<ResponseBody,ErrorUtils> converter = retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                    try {
                        ErrorUtils errorUtils = converter.convert(response.errorBody());
                        listener.onFailed(errorUtils.getErrorMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<LibraryModel> call, Throwable t) {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
    private void Registration_UniversityData(String userType, String photo, String name, String email, String country, String phone, String username, String password, String major, String address, String site, String lat, String lng, String token, final Context context, final onCompleteListener listener)
    {
        Map<String,String> uniMap = new HashMap<>();

        uniMap.put("user_token",token);
        uniMap.put("user_type","university");
        uniMap.put("user_photo",photo);
        uniMap.put("user_name",name);
        uniMap.put("user_email",email);
        uniMap.put("user_country",country);
        uniMap.put("user_phone",phone);
        uniMap.put("user_username",username);
        uniMap.put("user_pass",password);
        uniMap.put("user_google_lat",lat);
        uniMap.put("user_google_lng",lng);

        uniMap.put("university_name",name);
        uniMap.put("university_email",email);
        uniMap.put("university_phone",phone);
        uniMap.put("university_site",site);
        uniMap.put("university_major",major);
        uniMap.put("university_address",address);
        uniMap.put("university_google_lat",lat);
        uniMap.put("university_google_lng",lng);

        final Retrofit retrofit =setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<UniversityModel> universityModelCall = service.UniversityRegistration(uniMap);
        universityModelCall.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, final Response<UniversityModel> response) {
                if (response.isSuccessful())
                {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.hideProgress_Dialog();

                            listener.onUniversityDataSuccess(response.body());

                        }
                    },500);

                }
                else {
                    listener.hideProgress_Dialog();
                    Converter<ResponseBody,ErrorUtils> converter = retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                    try {
                        ErrorUtils errorUtils = converter.convert(response.errorBody());
                        listener.onFailed(errorUtils.getErrorMessage());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    }
            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable t) {
                listener.hideProgress_Dialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
    private void Registration_CompanyData(String userType, String photo , String name, String email, String country, String phone, String username, String password, String address, String town, String site, String lat, String lng, String token, final Context context, final onCompleteListener listener)
    {
        Map<String,String> compMap = new HashMap<>();

        compMap.put("user_token",token);
        compMap.put("user_type","company");
        compMap.put("user_photo",photo);
        compMap.put("user_name",name);
        compMap.put("user_email",email);
        compMap.put("user_country",country);
        compMap.put("user_phone",phone);
        compMap.put("user_username",username);
        compMap.put("user_pass",password);
        compMap.put("user_google_lat",lat);
        compMap.put("user_google_lng",lng);



       compMap.put("company_name",name);
       compMap.put("company_address",address);
       compMap.put("company_email",email);
       compMap.put("company_town",town);
       compMap.put("company_site",site);
       compMap.put("company_phone",phone);
       compMap.put("company_country",country);
       compMap.put("company_google_lat",lat);
        compMap.put("company_google_lng",lng);

       final Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
       Service service = retrofit.create(Service.class);
       Call<CompanyModel> companyModelCall = service.CompanyRegistration(compMap);
       companyModelCall.enqueue(new Callback<CompanyModel>() {
           @Override
           public void onResponse(Call<CompanyModel> call, final Response<CompanyModel> response) {
               if (response.isSuccessful())
               {

                   new android.os.Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {

                           listener.hideProgress_Dialog();
                           listener.onCompanyDataSuccess(response.body());
                       }
                   },500);
               }else {
                   listener.hideProgress_Dialog();
                   Converter<ResponseBody,ErrorUtils> converter = retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                   try {
                       ErrorUtils errorUtils = converter.convert(response.errorBody());
                       listener.onFailed(errorUtils.getErrorMessage());

                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               }
           }

           @Override
           public void onFailure(Call<CompanyModel> call, Throwable t) {
               listener.hideProgress_Dialog();
               listener.onFailed(context.getString(R.string.something_haywire));
           }
       });
    }

    private Retrofit setUP_Retrofit(String baseUrl)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
