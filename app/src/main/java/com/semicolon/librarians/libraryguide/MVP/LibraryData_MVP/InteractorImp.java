package com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.ArrayList;
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
    public void getLibraryData(final String lib_userName, final Context context , final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryModel>> call = service.getLibraryData();
        call.enqueue(new Callback<List<LibraryModel>>() {
            @Override
            public void onResponse(Call<List<LibraryModel>> call, Response<List<LibraryModel>> response) {
                if (response.isSuccessful())
                {
                    if (TextUtils.isEmpty(lib_userName))
                    {
                        List<LibraryModel> libraryModelList = response.body();
                        if (libraryModelList.size()>0)
                        {
                            listener.onLibraryDataSuccess(libraryModelList);
                            listener.hideProgressBar();
                        }else
                        {
                            listener.hideProgressBar();
                        }
                    }else
                        {
                            List<LibraryModel> libraryModelList = response.body();
                            List<LibraryModel> libraryModelList2= new ArrayList<>();
                            if (libraryModelList.size()>0)
                            {
                                for (LibraryModel libraryModel:libraryModelList)
                                {
                                    if (!libraryModel.getLib_username().equals(lib_userName))
                                    {
                                        libraryModelList2.add(libraryModel);
                                        Log.e("pppppp",""+libraryModel.getLib_username());

                                    }
                                }
                                if (libraryModelList2.size()>0)
                                {
                                    listener.onLibraryDataSuccess(libraryModelList2);
                                    listener.hideProgressBar();
                                }else
                                    {
                                        listener.onLibraryDataSuccess(libraryModelList2);
                                        listener.hideProgressBar();
                                    }

                            }else
                            {
                                listener.hideProgressBar();
                            }
                        }




                }else
                    {
                        listener.hideProgressBar();
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {
                listener.onFailed(context.getString(R.string.something_haywire));
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
