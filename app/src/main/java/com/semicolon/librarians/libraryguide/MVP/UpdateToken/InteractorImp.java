package com.semicolon.librarians.libraryguide.MVP.UpdateToken;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 22/01/2018.
 */

public class InteractorImp implements Interactor {




   private Retrofit setUpRetrofit()
   {
       OkHttpClient client = new OkHttpClient.Builder()
               .connectTimeout(1, TimeUnit.MINUTES)
               .writeTimeout(20,TimeUnit.SECONDS)
               .readTimeout(20,TimeUnit.SECONDS)
               .retryOnConnectionFailure(true).build();
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://librarians.liboasis.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .client(client).build();
       return retrofit;
   }


    @Override
    public void UpdateUserData(String user_username, String token, Context context, final onCompleteListener listener) {
        Map<String ,String> map = new HashMap<>();
        map.put("user_username",user_username);
        map.put("user_token",token);
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.UpdateToken(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
