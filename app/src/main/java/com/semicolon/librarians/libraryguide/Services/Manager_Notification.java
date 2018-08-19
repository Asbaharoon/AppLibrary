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
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;

import com.semicolon.librarians.libraryguide.Activities.ChooserSingin;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Models.MessageModel;
import com.semicolon.librarians.libraryguide.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Delta on 23/02/2018.
 */

public class Manager_Notification {

    public String mMsg="";


    public void CreateNotification(final Map<String,String> map, final Context context, final long sentTime, final String curr_username, final String chat_username)
    {


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                final String from_id =map.get("from_sender");
                final String to_receiver = map.get("to_receiver");

                final Target target;
                final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.custom_notification);
                String encodeMsg = map.get("message");
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                Date date = new Date(sentTime);
                String time = dateFormat.format(date);
                final String d = map.get("sent_date");


                try {
                    byte[] bytes = Base64.decode(encodeMsg,Base64.DEFAULT);
                    String decodeMsg = new String(bytes,"UTF-8");
                    mMsg = decodeMsg;
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
                final String soundState = pref.getSoundState();
                final String vibrateState=pref.getVibrateState();
                final String toneUri = pref.getRingtone();
                ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                final String currClass = am.getRunningTasks(1).get(0).topActivity.getClassName();
                Log.e("currclass",currClass);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.ChooserSingin"))
                        {

                        }else if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.FB_Gmail_UpdateProfile"))
                        {

                        }else if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.LoginActivity"))
                        {

                        }
                        else if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.MapsActivity"))
                        {

                        }
                        else if (currClass.equals("com.semicolon.librarians.libraryguide.Activities.Chat_Activity"))
                        {
                            Log.e("11111111111111111","11111111111111111");
                            Log.e("11111111111111111",from_id+"  from "+chat_username+"    "+" to "+curr_username);

                            if (from_id.equals(curr_username))
                                {
                                    Log.e("777777","7777777777777");
                                    /*new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("mmmmmmmssssssssssss",mMsg);
                                            EventBus.getDefault().post(new MessageModel(mMsg,chat_username,curr_username,d,""));
                                        }
                                    },100);*/
                            }
                            else if(from_id.equals(chat_username))
                            {
                                Log.e("22222222222222222","22222222222222222");

                                new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new MessageModel(mMsg,chat_username,curr_username,d,""));
                                    }
                                },100);
                            }
                            else {
                                Log.e("444444444444444","4444444444444444");


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



                                Intent intent = new Intent(context, ChooserSingin.class);
                                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                builder.setSmallIcon(R.mipmap.note_icon);

                                /*Notification notification = builder.build();
                                notification.contentView = remoteViews;*/
                                NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                                manager.notify(100,builder.build());
                            }


                        }





                        else
                        {


                            Log.e("5555555555555555","555555555555555555555");


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
                            builder.setSmallIcon(R.mipmap.note_icon);

            /*Notification notification = builder.build();
            notification.contentView = remoteViews;*/
                            NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                            manager.notify(100,builder.build());
                        }

                    }
                },100);




            }
        },1000);




    }


}
