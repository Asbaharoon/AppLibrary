package com.semicolon.librarians.library.MVP.Search_University_MVP;


import com.semicolon.librarians.library.Models.UniversityModel;
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
    public void getUniversityData(String uni_name,String country,final onCompleteListener listener) {

                Map<String,String> uni_Map = new HashMap<>();
                uni_Map.put("university_name",uni_name);
                uni_Map.put("university_country",country);
                Retrofit retrofit = setUpRetrofit();
                Service service = retrofit.create(Service.class);
                Call<List<UniversityModel>> call = service.getUniversitySearch_Data(uni_Map);
                call.enqueue(new Callback<List<UniversityModel>>() {
                    @Override
                    public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                        if (response.isSuccessful())
                        {
                            List<UniversityModel> universityModelList = response.body();
                            if (universityModelList.size()>0)
                            {
                                listener.onUniversityDataSuccess(universityModelList);

                            }else
                            {
                                listener.showNoResults();
                            }
                        }else
                        {

                            listener.onUniversityDataFailed("Error Something went haywire 1");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                        listener.onUniversityDataFailed("Error Something went haywire 2");
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
