package com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.MessageModel;
import com.semicolon.librarians.libraryguide.R;
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
    public void getMessages(String currUser_userName, String chatUser_userName, final Context context, final onCompleteListener listener) {
        Map<String ,String> map = new HashMap<>();
        map.put("senderid",currUser_userName);
        map.put("receiverid",chatUser_userName);
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<MessageModel> call = service.getLastMsg(map);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {

                if (response.isSuccessful())
                {
                    listener.onMessagesSuccess(response.body());
                }
                else
                    {
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });
    }
}
