package com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InteractorImp implements Interactor {

    @Override
    public void getNormalUserData(String userType, String userId, final Context context, final onCompleteListener listener) {
        Map <String,String> map = new HashMap<>();
        map.put("user_type",userType);
        map.put("user_username",userId);
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<NormalUserData> call = service.DisplayUserById(map);
        call.enqueue(new Callback<NormalUserData>() {
            @Override
            public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                if (response.isSuccessful())
                {
                    NormalUserData userData = response.body();
                    listener.onNormalUserDataSuccess(userData);

                }
                else
                    {
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<NormalUserData> call, Throwable t) {
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });
    }

    @Override
    public void getPublisherData(String userType, String userId,Context context, final onCompleteListener listener) {
        Map <String,String> map = new HashMap<>();
        map.put("user_type",userType);
        map.put("user_username",userId);
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<PublisherModel> call = service.DisplayPublisherById(map);
        call.enqueue(new Callback<PublisherModel>() {
            @Override
            public void onResponse(Call<PublisherModel> call, Response<PublisherModel> response) {
                if (response.isSuccessful())
                {
                    PublisherModel publisherModel = response.body();
                    listener.onPublisherDataSuccess(publisherModel);
                }else
                    {
                        listener.onFailed("Error Something went haywire");

                    }
            }

            @Override
            public void onFailure(Call<PublisherModel> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });
    }

    @Override
    public void getLibraryData(String userType, String userId,Context context, final onCompleteListener listener) {

        Map <String,String> map = new HashMap<>();
        map.put("user_type",userType);
        map.put("user_username",userId);
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<LibraryModel> call = service.DisplayLibraryById(map);
        call.enqueue(new Callback<LibraryModel>() {
            @Override
            public void onResponse(Call<LibraryModel> call, Response<LibraryModel> response) {
                if (response.isSuccessful())
                {
                    LibraryModel libraryModel = response.body();
                    listener.onLibraryDataSuccess(libraryModel);
                }else
                    {
                        listener.onFailed("Error Something went haywire");

                    }
            }

            @Override
            public void onFailure(Call<LibraryModel> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });

    }

    @Override
    public void getUniversityData(String userType, String userId,Context context, final onCompleteListener listener) {
        Map <String,String> map = new HashMap<>();
        map.put("user_type",userType);
        map.put("user_username",userId);
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<UniversityModel> call = service.DisplayUniversityById(map);
        call.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, Response<UniversityModel> response) {
                if (response.isSuccessful())
                {
                    UniversityModel universityModel = response.body();
                    listener.onUniversityDataSuccess(universityModel);
                }
                else
                    {
                        listener.onFailed("Error Something went haywire");

                    }
            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });
    }

    @Override
    public void getCompanyData(String userType, String userId,Context context, final onCompleteListener listener) {
        Map <String,String> map = new HashMap<>();
        map.put("user_type",userType);
        map.put("user_username",userId);
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<CompanyModel> call = service.DisplayCompanyById(map);
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {

                if (response.isSuccessful())
                {
                    CompanyModel companyModel = response.body();
                    listener.onCompanyDataSuccess(companyModel);
                }else
                    {
                        listener.onFailed("Error Something went haywire");

                    }
            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });

    }
    private Retrofit setUpRetrofit(Context context)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://librarians.liboasis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit;
    }
}
