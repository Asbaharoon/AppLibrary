package com.semicolon.librarians.libraryguide.MVP.PublisherMVP;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.PublisherModel;
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



public class InteractorImp implements com.semicolon.librarians.libraryguide.MVP.PublisherMVP.Interactor {


    @Override
    public void getPublisherData(final String pub_userName, final Context context, final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<PublisherModel>> call = service.getPublisherData();
        call.enqueue(new Callback<List<PublisherModel>>() {
            @Override
            public void onResponse(Call<List<PublisherModel>> call, Response<List<PublisherModel>> response) {
                if (response.isSuccessful())
                {
                    if (TextUtils.isEmpty(pub_userName))
                    {
                        List<PublisherModel> publisherModelList = response.body();
                        if (publisherModelList.size()>0)
                        {
                            listener.onPublisherDataSuccess(publisherModelList);
                            listener.hideProgressBar();
                        }else
                        {
                            listener.onPublisherDataSuccess(publisherModelList);

                            listener.hideProgressBar();
                        }
                    }else
                        {
                            List<PublisherModel> publisherModelList = response.body();
                            List<PublisherModel> publisherModelList2= new ArrayList<>();
                            if (publisherModelList.size()>0)
                            {
                                for (PublisherModel publisherModel:publisherModelList)
                                {
                                    if (!publisherModel.getPub_username().equals(pub_userName))
                                    {
                                        publisherModelList2.add(publisherModel);
                                        Log.e("pppppp",""+publisherModel.getPub_username());

                                    }
                                }
                                if (publisherModelList2.size()>0)
                                {
                                    listener.onPublisherDataSuccess(publisherModelList2);
                                    listener.hideProgressBar();
                                }else
                                    {
                                        listener.onPublisherDataSuccess(publisherModelList2);
                                        listener.hideProgressBar();
                                    }

                            }else
                            {
                                listener.hideProgressBar();
                                listener.onPublisherDataFailed(context.getString(R.string.something_haywire));

                            }
                        }

                }
            }

            @Override
            public void onFailure(Call<List<PublisherModel>> call, Throwable t) {
                listener.onPublisherDataFailed(context.getString(R.string.something_haywire));
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
