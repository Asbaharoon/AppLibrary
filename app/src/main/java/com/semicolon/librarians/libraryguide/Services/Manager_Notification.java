package com.semicolon.librarians.libraryguide.Services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;

import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Models.ID;
import com.semicolon.librarians.libraryguide.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Delta on 23/02/2018.
 */

public class Manager_Notification {
    String curr_username;

    public Manager_Notification() {
        EventBus.getDefault().register(this);
    }



    public void CreateNotification(Map<String,String> map, final Context context, long sentTime)
    {

        final Target target;
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.custom_notification);
        String encodeMsg = map.get("message");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm aa");
        Date date = new Date(sentTime);
        String time = dateFormat.format(date);
        try {
            byte[] bytes = Base64.decode(encodeMsg,Base64.DEFAULT);
            String decodeMsg = new String(bytes,"UTF-8");
            remoteViews.setTextViewText(R.id.userName,map.get("sender_name"));
            remoteViews.setTextViewText(R.id.msg,decodeMsg);
            remoteViews.setTextViewText(R.id.date,time);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                remoteViews.setImageViewBitmap(R.id.userImage,bitmap);
                Log.e("loaded","loaded");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        final String image = map.get("sender_photo");

        if (image.startsWith("http"))
        {
            Handler  handler = new Handler(context.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(context).load(image).into(target);

                }
            },1);
        }else
            {
                Handler  handler = new Handler(context.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(context).load(Tags.image_path+image).into(target);

                    }
                },1);

            }



        Preferences pref = new Preferences(context);
        String soundState = pref.getSoundState();
        String vibrateState=pref.getVibrateState();
        String toneUri = pref.getRingtone();
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String currClass = am.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.e("currclass",currClass);

        if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.Chat_Activity"))
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContent(remoteViews);
            if (soundState.equals("on"))
            {
                if (TextUtils.isEmpty(toneUri)|| toneUri==null)
                {
                    builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                }else
                {
                    builder.setSound(Uri.parse(toneUri));


                }

                if (vibrateState.equals("on"))
                {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                }
            }else if (soundState.equals("off"))
            {
                if (vibrateState.equals("on"))
                {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                }
            }



            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.mipmap.ic_launcher_round);

            /*Notification notification = builder.build();
            notification.contentView = remoteViews;*/
            NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            manager.notify(100,builder.build());
        }






    }


    public void setCurr_username(String curr_username) {
        this.curr_username = curr_username;
        Log.e("currrrrrrr",curr_username);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCurruserName(ID id)
    {
        Log.e("currrrrrrr222",id.getUsername());

    }

}
