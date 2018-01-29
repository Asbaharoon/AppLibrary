package com.example.omd.library.MVP.LibraryTypeMVP;

import com.example.omd.library.Models.LibraryType_Model;
import com.example.omd.library.Services.Service;

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
    public void getLibraryTypeData(final onCompleteListener listener) {


        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryType_Model>> call = service.getLibraryTypeData();
        call.enqueue(new Callback<List<LibraryType_Model>>() {
            @Override
            public void onResponse(Call<List<LibraryType_Model>> call, Response<List<LibraryType_Model>> response) {
                if (response.isSuccessful())
                {
                    List<LibraryType_Model> libraryTypeModelList = response.body();
                    if (libraryTypeModelList.size()>0)
                    {
                        listener.onLibraryTypeDataSuccess(libraryTypeModelList);
                        listener.hideProgressBar();
                    }else
                        {
                            listener.hideProgressBar();
                        }
                }
            }

            @Override
            public void onFailure(Call<List<LibraryType_Model>> call, Throwable t) {
                listener.onLibraryTypeDataFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
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
