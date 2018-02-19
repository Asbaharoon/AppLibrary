package com.semicolon.librarians.libraryguide.MVP.CountriesMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InteractorImp implements Interactor {


    @Override
    public void getCountryData(final Context context, final onCompleteListener listener) {
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<CountriesModel>> call = service.getCountryData();
        call.enqueue(new Callback<List<CountriesModel>>() {
            @Override
            public void onResponse(Call<List<CountriesModel>> call, Response<List<CountriesModel>> response) {
                if (response.isSuccessful())
                {
                    List<CountriesModel> countriesModels = response.body();
                     listener.onCountryDataSuccess(countriesModels);
                }else
                    {
                        listener.onCountryDataFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<List<CountriesModel>> call, Throwable t) {
                listener.onCountryDataFailed(context.getString(R.string.network_connection));
            }
        });
    }

   private Retrofit setUpRetrofit()
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
