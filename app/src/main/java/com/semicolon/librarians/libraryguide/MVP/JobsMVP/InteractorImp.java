package com.semicolon.librarians.libraryguide.MVP.JobsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.JobsModel;
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

/**
 * Created by Delta on 22/01/2018.
 */

public class InteractorImp implements Interactor {


    @Override
    public void getJobsData(final Context context , final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<JobsModel>> call = service.getJobsData();
        call.enqueue(new Callback<List<JobsModel>>() {
            @Override
            public void onResponse(Call<List<JobsModel>> call, Response<List<JobsModel>> response) {
                if (response.isSuccessful())
                {
                    List<JobsModel> jobsModelList = response.body();
                    if (jobsModelList.size()>0)
                    {
                        listener.onJobDataSuccess(jobsModelList);
                        listener.hideProgressBar();

                    }else
                        {
                            listener.hideProgressBar();
                        }
                }else
                    {
                        listener.hideProgressBar();
                        listener.onJobDataFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<List<JobsModel>> call, Throwable t) {
                listener.onJobDataFailed(context.getString(R.string.something_haywire));
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
