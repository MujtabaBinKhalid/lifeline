package com.lifeline.fyp.fyp.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.lifeline.fyp.fyp.socialMedia.Allnotification;

/**
 * Created by Mujtaba_khalid on 5/13/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent( FirebaseMessagingService.this,Allnotification.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity( this,0,intent,PendingIntent.FLAG_ONE_SHOT );
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this );
        notificationBuilder.setContentTitle( "LifeLine" );
        notificationBuilder.setContentText( remoteMessage.getNotification().getBody() );
        notificationBuilder.setAutoCancel( true );
        notificationBuilder.setContentIntent( pendingIntent );

        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify( 0,notificationBuilder.build() );




    }
}
