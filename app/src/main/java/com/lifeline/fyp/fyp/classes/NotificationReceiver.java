package com.lifeline.fyp.fyp.classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

/**
 * Created by Mujtaba_khalid on 4/26/2018.
 */


    public class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub


            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE); //  tell the user that something has happened in the background.

            Intent notificationIntent = new Intent(context, UserProfile.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // making it as the root activity.

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // USED FOR DESIGNING NOTFICATION LAYOUT.
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon( R.drawable.ic_launcher_background)
                    .setContentTitle("Life Line")
                    .setContentText(NotificationService.notificationcontent).setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationManager.notify(0, mNotifyBuilder.build());


        }

    }
