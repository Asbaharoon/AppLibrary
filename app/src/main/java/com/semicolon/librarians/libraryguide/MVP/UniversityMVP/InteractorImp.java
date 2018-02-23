package com.semicolon.librarians.libraryguide.MVP.UniversityMVP;

import android.content.Context;
import android.text.TextUtils;

import com.semicolon.librarians.libraryguide.Models.UniversityModel;
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
    public void getUniversityData(final String userName, final Context context, final onCompleteListener listener) {


        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<UniversityModel>> call = service.getUniversityData();
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                if (response.isSuccessful())
                {
                    if (TextUtils.isEmpty(userName))
                    {
                        List<UniversityModel> universityModelList = response.body();
                        if (universityModelList.size()>0)
                        {
                            listener.onUniversityDataSuccess(universityModelList);
                            listener.hideProgressBar();
                        }else
                        {
                            listener.onUniversityDataSuccess(universityModelList);

                            listener.hideProgressBar();
                        }
                    }
                    else
                        {
                            List<UniversityModel> universityModelList = response.body();
                            List<UniversityModel> universityModelList2 = new ArrayList<>();

                            if (universityModelList.size()>0)
                            {
                                for (UniversityModel universityModel :universityModelList)
                                {
                                    if (!universityModel.getUni_username().equals(userName))
                                    {
                                        universityModelList2.add(universityModel);
                                    }
                                }
                                if (universityModelList2.size()>0)
                                {
                                    listener.onUniversityDataSuccess(universityModelList2);
                                    listener.hideProgressBar();
                                }else
                                    {
                                        listener.onUniversityDataSuccess(universityModelList2);
                                        listener.hideProgressBar();
                                    }

                            }else
                            {
                                listener.hideProgressBar();
                            }
                        }

                }
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                listener.onUniversityDataFailed(context.getString(R.string.something_haywire));
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
