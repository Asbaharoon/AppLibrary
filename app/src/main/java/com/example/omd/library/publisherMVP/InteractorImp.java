package com.example.omd.library.publisherMVP;


import com.example.omd.library.Models.PublishersModel;
import com.example.omd.library.Services.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class InteractorImp implements com.example.omd.library.publisherMVP.Interactor {


    @Override
    public void getpublisherData(final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<PublishersModel>> call = service.getPublisherData();
        call.enqueue(new Callback<List<PublishersModel>>() {
            @Override
            public void onResponse(Call<List<PublishersModel>> call, Response<List<PublishersModel>> response) {
                if (response.isSuccessful())
                {
                    List<PublishersModel> publisherModelList = response.body();
                    listener.onpublisherDataSuccess(publisherModelList);
                }
            }

            @Override
            public void onFailure(Call<List<PublishersModel>> call, Throwable t) {
                listener.onpublisherDataFailed(t.getMessage());
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
