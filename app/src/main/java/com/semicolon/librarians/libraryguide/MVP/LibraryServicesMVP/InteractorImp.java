package com.semicolon.librarians.libraryguide.MVP.LibraryServicesMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryServices_Model;
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
    public void getLibraryServicesData(final Context context, final onCompleteListener listener) {


        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryServices_Model>> call = service.getLibraryServicesData();
        call.enqueue(new Callback<List<LibraryServices_Model>>() {
            @Override
            public void onResponse(Call<List<LibraryServices_Model>> call, Response<List<LibraryServices_Model>> response) {
                if (response.isSuccessful())
                {
                    List<LibraryServices_Model> libraryServicesModelList = response.body();
                    if (libraryServicesModelList.size()>0)
                    {
                        listener.onLibraryServicesDataSuccess(libraryServicesModelList);
                        listener.hideProgressBar();
                    }else
                        {
                            listener.onLibraryServicesDataSuccess(libraryServicesModelList);

                            listener.hideProgressBar();
                        }
                }else
                    {
                        listener.hideProgressBar();
                        listener.onLibraryServicesDataFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<List<LibraryServices_Model>> call, Throwable t) {
                listener.onLibraryServicesDataFailed(context.getString(R.string.something_haywire));
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
