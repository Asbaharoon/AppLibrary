package com.example.omd.library.newsMVP;

import com.example.omd.library.Models.newsModel;
import com.example.omd.library.Services.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class InteractorImp implements com.example.omd.library.newsMVP.Interactor {


    @Override
    public void getNewsData(final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<newsModel>> call = service.getnewsData();
        call.enqueue(new Callback<List<newsModel>>() {
            @Override
            public void onResponse(Call<List<newsModel>> call, Response<List<newsModel>> response) {
                if (response.isSuccessful())
                {
                    List<newsModel> newsModelList = response.body();
                    listener.onNewsDataSuccess(newsModelList);
                }
            }

            @Override
            public void onFailure(Call<List<newsModel>> call, Throwable t) {
                listener.onNewsDataFailed(t.getMessage());
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
