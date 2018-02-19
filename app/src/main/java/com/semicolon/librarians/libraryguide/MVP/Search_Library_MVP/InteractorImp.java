package com.semicolon.librarians.libraryguide.MVP.Search_Library_MVP;


import android.content.Context;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

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
    public void getLibraryData(String lib_name, String lib_type, String country_id, final Context context, final onCompleteListener listener) {

        Log.e("dadadadad", "getLibraryData: "+lib_name+"\n"+lib_type+"\n"+country_id+"\n");
        Map<String,String> lib_Map = new HashMap<>();
        lib_Map.put("library_name",lib_name);
        lib_Map.put("library_country",country_id);
        lib_Map.put("library_type",lib_type);

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryModel>> call = service.getLibrarySearch_Data(lib_Map);
        call.enqueue(new Callback<List<LibraryModel>>() {
            @Override
            public void onResponse(Call<List<LibraryModel>> call, Response<List<LibraryModel>> response) {
                if (response.isSuccessful())
                {
                    Log.e("success","success");
                    List<LibraryModel> libraryModelList = response.body();
                    if (libraryModelList.size()>0)
                    {
                        Log.e("success","success >0");
                        listener.onLibraryDataSuccess(libraryModelList);
                        listener.hideProgress();
                    }else
                        {
                            Log.e("success","success<0");
                           listener.onLibraryDataFailed(context.getString(R.string.empty_field));
                           listener.hideProgress();
                        }
                }else
                    {
                        Log.e("not success","not success");
                        listener.onLibraryDataFailed(context.getString(R.string.something_haywire));
                        listener.hideProgress();
                    }
            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {
                Log.e("failed","failed");

                listener.onLibraryDataFailed(context.getString(R.string.something_haywire));
                listener.hideProgress();
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
