package com.semicolon.librarians.libraryguide.Services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.R;

import java.util.Map;

/**
 * Created by Delta on 07/02/2018.
 */

public class MyFirebaseMessgaesServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> map= remoteMessage.getData();
        CreateNotificationBuilder(map.get("sender_name"),"message");
        for (String key :map.keySet())
        {
            Log.e("key",key);


            Log.e("not"," : "+map.get(key));
        }


    }
    public void CreateNotificationBuilder(String title,String body)
    {


        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String currClass = am.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.e("currclass",currClass);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(100,builder.build());

    }
}
