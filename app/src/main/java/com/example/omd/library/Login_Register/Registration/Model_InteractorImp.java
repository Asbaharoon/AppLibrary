package com.example.omd.library.Login_Register.Registration;

import android.content.Context;
import android.text.TextUtils;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Services.NetworkConnection;
import com.example.omd.library.Services.Service;
import com.example.omd.library.Services.Tags;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 24/12/2017.
 */

public class Model_InteractorImp implements Model_Interactor {

    private boolean isConnected =false;


    @Override
    public void NormalUserRegistration(String userType, String photoName, String photo, String first_name, String last_name, String email, String country, String phone, String username, String password, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(first_name))
        {
            listener.setNormalUserFirstName_Error();
        }
        else if (TextUtils.isEmpty(last_name))
        {
            listener.setNormalUserLastName_Error();
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
        else if (TextUtils.isEmpty(password))
        {
            listener.setNormalUserPassword_Error();
        }
        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                Registration_NormalUser(userType,photoName,photo,first_name,last_name, email,country,phone,username,password, listener);

            }else
            {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
    }

    @Override
    public void PublisherRegistration(String userType, String first_name, String last_name, String email, String country, String password, String phone, String username, String address, String town, String website_url, String lat, String lng, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(first_name))
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
            listener.setLibraryLat_lng_Error();
        }

        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected) {
                Registration_PublisherData(userType,first_name,last_name,email,country,password,phone,username,address,town,website_url,lat,lng, listener);
            }else
            {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
    }


    @Override
    public void LibraryRegistration(String userType, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libOtherType, String libUsername, String libPassword, String lat, String lng, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(libName))
        {
            listener.setLibraryName_Error();
        }
        else if (TextUtils.isEmpty(libEmail))
        {
            listener.setLibraryEmail_Error();
        }
        else if (!libEmail.matches(Tags.email_Regex))
        {
            listener.setLibraryEmail_Error();
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

        else if (libType.equals("Other"))
        {
            if (TextUtils.isEmpty(libOtherType))
            {
                listener.setLibraryOtherType_Error();
            }
            else if (TextUtils.isEmpty(libType))
            {
                listener.setLibraryOtherType_Error();
            }
            else if (TextUtils.isEmpty(libUsername))
            {
                listener.setLibraryUsername_Error();

            }
            else if (TextUtils.isEmpty(libPassword))
            {
                listener.setLibraryPassword_Error();
            }
            else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    listener.setLibraryLat_lng_Error();

                }else
                {
                    listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                }
            }

            else
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    Registration_LibraryData(userType,libName,libEmail,libPhone,libCountry,libAddress,libType,libOtherType,libUsername,libPassword,lat,lng,listener);
                }else
                {
                    listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                }
            }
        }
        else if (TextUtils.isEmpty(libType))
        {
            listener.setLibraryOtherType_Error();

        }
        else if (TextUtils.isEmpty(libUsername))
        {
            listener.setLibraryUsername_Error();

        }
        else if (TextUtils.isEmpty(libPassword))
        {
            listener.setLibraryPassword_Error();
        }
        else if (TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng))
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                listener.setLibraryLat_lng_Error();

            }else
            {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                Registration_LibraryData(userType,libName,libEmail,libPhone,libCountry,libAddress,libType,libOtherType,libUsername,libPassword,lat,lng,listener);

            }else
            {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
    }

    @Override
    public void UniversityRegistration(String userType, String name, String email, String country, String phone, String username, String password, String major, String address, String site, String lat, String lng, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(name))
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
                    Registration_UniversityData(userType,name,email,country,phone,username,password,major,address,site,lat,lng,listener);
                }else
                {
                    listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                }
            }
    }

    @Override
    public void CompanyRegistration(String userType, String name, String email, String country, String phone, String username, String password, String address, String town, String site, onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(name))
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
        }

        else
        {
            isConnected = new NetworkConnection(context).CheckConnection();
            if (isConnected)
            {
                Registration_CompanyData(userType,name,email,country,phone,username,password,address,town,site,listener);
            }else
            {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        }
    }


    private void Registration_NormalUser(String userType, String photoName, String photo, String first_name, String last_name, String email, String country , String phone, String username, String password, final onCompleteListener listener)
    {

          Map<String,String> userMap = new HashMap<>();

        userMap.put("user_type",userType);
        userMap.put("user_name",first_name+" "+last_name);
        userMap.put("user_email",email);
        userMap.put("user_country",country);
        userMap.put("user_phone",phone);
        userMap.put("user_username",username);
        userMap.put("user_pass",password);

        Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<NormalUserData> userDataCall = service.NormalUserRegistration(userMap);
        userDataCall.enqueue(new Callback<NormalUserData>() {
            @Override
            public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                listener.onNormalUserDataSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NormalUserData> call, Throwable t) {
                listener.onFailed(t.getMessage());
            }
        });
        listener.onNormalUserDataSuccess(new NormalUserData());
    }
    private void Registration_PublisherData(String userType, String first_name, String last_name, String email, String country, String password, String phone, String username, String address, String town, String website_url, String lat, String lng, onCompleteListener listener)
    {
        listener.onPublisherDataSuccess(new PublisherModel());
    }
    private void Registration_LibraryData(String userType, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libOtherType, String libUsername, String libPassword, String lat, String lng, final onCompleteListener listener)
    {
        Map<String,String> libMap = new HashMap<>();

        libMap.put("user_type",userType);
        libMap.put("user_name",libName);
        libMap.put("user_email",libEmail);
        libMap.put("user_country",libCountry);
        libMap.put("user_phone",libPhone);
        libMap.put("user_username",libUsername);
        libMap.put("user_pass",libPassword);

        libMap.put("library_name",libName);
        libMap.put("library_address",libAddress);
        libMap.put("library_country",libCountry);
        libMap.put("library_email",libEmail);
        libMap.put("library_phone",libPhone);
        libMap.put("library_type",libType);
        libMap.put("library_google_lat",lat);
        libMap.put("library_google_lng",lng);


        Retrofit retrofit = setUP_Retrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<LibraryModel> libraryModelCall = service.LibraryRegistration(libMap);
        libraryModelCall.enqueue(new Callback<LibraryModel>() {
            @Override
            public void onResponse(Call<LibraryModel> call, Response<LibraryModel> response) {
                listener.onLibraryDataSuccess(response.body());

            }

            @Override
            public void onFailure(Call<LibraryModel> call, Throwable t) {

            }
        });

    }
    private void Registration_UniversityData(String userType, String name, String email, String country, String phone, String username, String password, String major, String address, String site, String lat, String lng, onCompleteListener listener)
    {

    }
    private void Registration_CompanyData(String userType, String name, String email, String country, String phone, String username, String password, String address, String town, String site, onCompleteListener listener){}

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
