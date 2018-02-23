package com.semicolon.librarians.libraryguide.MVP.Update_UserStatue_MVP;

import android.content.Context;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.Tes;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.HashMap;
import java.util.Map;
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
    public void UpdateUserData(String user_username, String lat, String lng, Context context, final onCompleteListener listener) {

       Log.e("sssssssss",user_username+"  "+lat+"   "+lng+"   ");

       Map<String,String > map = new HashMap<>();
        map.put("user_username",user_username);
        map.put("user_token","");
        map.put("user_google_lat",lat);
        map.put("user_google_lng",lng);

        Retrofit retrofit = setUpRetrofit();

        Service service = retrofit.create(Service.class);
        Call<Tes> call = service.UpdateUserStatue(map);
        call.enqueue(new Callback<Tes>() {
            @Override
            public void onResponse(Call<Tes> call, Response<Tes> response) {
                if (response.isSuccessful())
                {
                    Tes tes = response.body();
                    Log.e("response",tes.getUser_username()+"  "+"  "+tes.getUser_google_lat()+"  "+tes.getUser_google_lng());
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<Tes> call, Throwable t) {
                Log.e("error",t.getMessage());
                listener.onFailed();

            }
        });
    }
}
