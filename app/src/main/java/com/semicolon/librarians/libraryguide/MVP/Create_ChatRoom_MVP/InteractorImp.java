package com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP;


import android.content.Context;
import android.util.Log;

import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.io.IOException;
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


public class InteractorImp implements Interactor {




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

    @Override
    public void Create_ChatRoom(String currUser_userName, String chatUser_userName, final Context context, final Interactor.onCompleteListener listener) {

       Log.e("creatchat room",currUser_userName+chatUser_userName);
        Map<String,String> map = new HashMap<>();
        map.put("friend_id_fk1",currUser_userName);
        map.put("friend_id_fk2",chatUser_userName);
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.CreateChatRoom(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try {
                        listener.onChatRoom_CreatedSuccessfully(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else
                    {
                        Log.e("error","response error");
                        listener.onFailed(context.getString(R.string.error));
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error",t.getMessage());
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
}
