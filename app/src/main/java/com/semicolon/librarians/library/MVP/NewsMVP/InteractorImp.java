package com.semicolon.librarians.library.MVP.NewsMVP;

import com.semicolon.librarians.library.Models.NewsModel;
import com.semicolon.librarians.library.Services.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class InteractorImp implements com.semicolon.librarians.library.MVP.NewsMVP.Interactor {


    @Override
    public void getNewsData(final onCompleteListener listener) {


        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<NewsModel>> call = service.getNewsData();
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful())
                {
                    List<NewsModel> newsModelList = response.body();
                    if (newsModelList.size()>0)
                    {
                        listener.onNewsDataSuccess(newsModelList);
                        listener.hideProgressBar();
                    }else
                        {
                            listener.hideProgressBar();
                        }
                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                listener.onNewsDataFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                listener.hideProgressBar();

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
