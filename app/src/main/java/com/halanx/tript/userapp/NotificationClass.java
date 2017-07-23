package com.halanx.tript.userapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.halanx.tript.userapp.Activities.HomeActivity;

import static android.R.attr.id;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by samarthgupta on 24/03/17.
 */

public class NotificationClass {

        NotificationCompat.Builder notif;
        NotificationManager notificationManager;
        Context c;

        public void sendNotif(Context context,String title, String text, String ticker){
            c=context;
            notif = new NotificationCompat.Builder(context);
            notif.setAutoCancel(true);

            long pattern[] = {0,2000,0};
            notif.setWhen(System.currentTimeMillis());
           notif.setSmallIcon(R.drawable.halanx_launcher);
            notif.setTicker(ticker);
            notif.setContentTitle(title);
            notif.setVibrate(pattern);
            notif.setContentText(text);


            //Intent defines which activity to open on giving notification

            //////////////////////////////////// To be changed
            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notif.setContentIntent(pendingIntent);

            notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(id,notif.build());

        }


    /*  Using method
    NotificationClass notif = new NotificationClass();
                notif.sendNotif(getApplicationContext(),"Notification","HEHEH","LOL"); */


    }


