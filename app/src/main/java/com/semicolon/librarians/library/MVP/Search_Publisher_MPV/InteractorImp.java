package com.semicolon.librarians.library.MVP.Search_Publisher_MPV;


import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Services.Service;

import java.util.HashMap;
import java.util.List;
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
    public void getPublisherData(String pub_name,String country,final onCompleteListener listener) {

        Map<String,String> pub_Map = new HashMap<>();
        pub_Map.put("publisher_name",pub_name);
        pub_Map.put("publisher_country",country);
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<PublisherModel>> call = service.getPublisherSearch_Data(pub_Map);
        call.enqueue(new Callback<List<PublisherModel>>() {
            @Override
            public void onResponse(Call<List<PublisherModel>> call, Response<List<PublisherModel>> response) {
                if (response.isSuccessful())
                {
                    List<PublisherModel> publisherModelList = response.body();
                    if (publisherModelList.size()>0)
                    {
                        listener.onPublisherDataSuccess(publisherModelList);
                        listener.hideNoResults();
                    }else
                        {
                            listener.showNoResults();
                        }
                }else
                    {
                        listener.onPublisherDataFailed("Error Something went haywire ");
                        listener.hideNoResults();
                    }
            }

            @Override
            public void onFailure(Call<List<PublisherModel>> call, Throwable t) {
                listener.onPublisherDataFailed("Error Something went haywire ");
                listener.hideNoResults();
            }
        });
    }

   private Retrofit setUpRetrofit()
   {
       ////////////////////////////////////////////Error
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
