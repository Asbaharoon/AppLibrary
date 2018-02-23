package com.semicolon.librarians.libraryguide.MVP.NewsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.NewsModel;
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



public class InteractorImp implements com.semicolon.librarians.libraryguide.MVP.NewsMVP.Interactor {


    @Override
    public void getNewsData(final Context context, final onCompleteListener listener) {


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
                            listener.onNewsDataSuccess(newsModelList);

                            listener.hideProgressBar();

                        }
                }else
                    {
                        listener.onNewsDataFailed(context.getString(R.string.something_haywire));

                    }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                listener.onNewsDataFailed(context.getString(R.string.something_haywire));
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
