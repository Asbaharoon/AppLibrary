package com.semicolon.librarians.libraryguide.MVP.Messages_MVP;


import android.content.Context;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.MessageModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;

import java.util.HashMap;
import java.util.List;
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
    public void getMessages(String currUser_userName, String chatUser_userName, final Context context, final onCompleteListener listener) {
        Map<String ,String> map = new HashMap<>();
        map.put("senderid",currUser_userName);
        map.put("receiverid",chatUser_userName);
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<MessageModel>> call = service.DisplayMessages(map);
        call.enqueue(new Callback<List<MessageModel>>() {
            @Override
            public void onResponse(Call<List<MessageModel>> call, Response<List<MessageModel>> response) {

                if (response.isSuccessful())
                {
                    List<MessageModel> messageModelList = response.body();
                    listener.onMessagesSuccess(messageModelList);
                }
                else
                    {
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<List<MessageModel>> call, Throwable t) {
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });
    }

    @Override
    public void sendMessage(String senderid, String receiverid, String sender_name, String receiver_name, String message,String chat_user_token, String sender_photo, final Context context, final onCompleteListener listener) {


       Log.e("msg",message+"  "+chat_user_token);

       Map<String ,String> map = new HashMap<>();

        map.put("senderid",senderid);
        map.put("receiverid",receiverid);
        map.put("user_token",chat_user_token);
        map.put("sender_name",sender_name);
        map.put("receiver_name",receiver_name);
        map.put("sender_photo",sender_photo);
        map.put("message",message);





        Log.e("data",senderid+"\n"+receiverid+"\n"+sender_name+"\n"+receiver_name+"\n"+sender_photo+"\n"+message);

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.SendMessages(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    listener.onMessageSendSuccess();
                }else
                    {
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
}
