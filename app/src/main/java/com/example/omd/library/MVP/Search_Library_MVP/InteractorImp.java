package com.example.omd.library.MVP.Search_Library_MVP;


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
    public void getLibraryData(String lib_name,String country_id,String service_id,final onCompleteListener listener) {

        ////////eeeeeeeeeeeeeeeeee//////////////////
        Map<String,String> lib_Map = new HashMap<>();
        lib_Map.put("library_name",lib_name);
        lib_Map.put("library_country",country_id);
        lib_Map.put("library_service",service_id);

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryModel>> call = service.getLibrarySearch_Data(lib_Map);
        call.enqueue(new Callback<List<LibraryModel>>() {
            @Override
            public void onResponse(Call<List<LibraryModel>> call, Response<List<LibraryModel>> response) {
                if (response.isSuccessful())
                {
                    List<LibraryModel> libraryModelList = response.body();
                    if (libraryModelList.size()>0)
                    {
                        listener.onLibraryDataSuccess(libraryModelList);
                        listener.hideProgress();
                    }else
                        {
                           listener.onLibraryDataFailed("Error something went haywire");
                        }
                }else
                    {
                        listener.onLibraryDataFailed("Error Something went haywire ");
                        listener.hideProgress();
                    }
            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {
                listener.onLibraryDataFailed("Error Something went haywire ");
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
