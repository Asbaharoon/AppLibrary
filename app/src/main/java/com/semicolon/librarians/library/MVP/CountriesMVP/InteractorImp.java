package com.semicolon.librarians.library.MVP.CountriesMVP;

import com.semicolon.librarians.library.Models.CountriesModel;
import com.semicolon.librarians.library.Services.Service;

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
    public void getCountryData(final onCompleteListener listener) {
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
                        listener.onCountryDataFailed("Error something went haywire");
                    }
            }

            @Override
            public void onFailure(Call<List<CountriesModel>> call, Throwable t) {
                listener.onCountryDataFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
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
