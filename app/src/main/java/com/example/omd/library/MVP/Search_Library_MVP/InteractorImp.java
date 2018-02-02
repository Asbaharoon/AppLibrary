package com.example.omd.library.MVP.Search_Library_MVP;


import android.util.Log;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Services.Service;

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
    public void getLibraryData(String lib_name,String lib_type,String country_id,String service_id,final onCompleteListener listener) {

        Log.e("dadadadad", "getLibraryData: "+lib_name+"\n"+lib_type+"\n"+country_id+"\n"+service_id);
        Map<String,String> lib_Map = new HashMap<>();
        lib_Map.put("library_name",lib_name);
        lib_Map.put("library_country",country_id);
        lib_Map.put("library_service",service_id);
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
                           listener.onLibraryDataFailed("empty data");
                           listener.hideProgress();
                        }
                }else
                    {
                        Log.e("not success","not success");
                        listener.onLibraryDataFailed("Error Something went haywire 1");
                        listener.hideProgress();
                    }
            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {
                Log.e("failed","failed");

                listener.onLibraryDataFailed(t.getMessage());
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
