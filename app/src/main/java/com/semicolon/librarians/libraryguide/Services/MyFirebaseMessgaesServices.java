package com.semicolon.librarians.libraryguide.Services;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Delta on 07/02/2018.
 */

public class MyFirebaseMessgaesServices extends FirebaseMessagingService {

   public static String curr_username;
    public static String chat_username;

    public MyFirebaseMessgaesServices() {
    }

    public MyFirebaseMessgaesServices(String curr_username, String chat_username) {
        this.curr_username = curr_username;
        this.chat_username = chat_username;
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map= remoteMessage.getData();

                Manager_Notification manager_notification = new Manager_Notification();
                manager_notification.CreateNotification(map,MyFirebaseMessgaesServices.this,remoteMessage.getSentTime(),curr_username,chat_username);

                Log.e("ccccccccccccccc",curr_username+"            "+chat_username);
                for (String key :map.keySet())
                {
                    Log.e("key",key);


                    Log.e("not"," : "+map.get(key));
                }
            }
        },1000);



    }



}
