package com.semicolon.librarians.libraryguide.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Delta on 07/02/2018.
 */

public class MyFirebaseMessgaesServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> map= remoteMessage.getData();

        Manager_Notification manager_notification = new Manager_Notification();
        manager_notification.CreateNotification(map,this,remoteMessage.getSentTime());

        for (String key :map.keySet())
        {
            Log.e("key",key);


            Log.e("not"," : "+map.get(key));
        }


    }



}
