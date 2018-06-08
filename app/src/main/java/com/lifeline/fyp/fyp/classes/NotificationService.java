package com.lifeline.fyp.fyp.classes;

import java.security.Provider;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/26/2018.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.NotificaitionObject;
import com.lifeline.fyp.fyp.retrofit.responses.NotificationOuterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationService extends Service {
    Retrofit retrofit;
    Api api;
    Calendar rightNow;
    static String notificationcontent;


    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        gettingId();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d( "HELLOO","HELLOOO" );
        super.onTaskRemoved( rootIntent );
    }

    private  void gettingNotification(Integer memberId){
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );
        Call<NotificationOuterResponse> notificationOuterResponseCall
                = api.notificationresponse( memberId );

        notificationOuterResponseCall.enqueue( new Callback<NotificationOuterResponse>() {
            @Override
            public void onResponse(Call<NotificationOuterResponse> call, Response<NotificationOuterResponse> response) {

                Log.d("NOTIFICATIONRESPONSE", String.valueOf( response ) );
                Log.d("NOTIFICATIONRESPONSE", String.valueOf( response.body().getDataCount() ) );

                if(Integer.parseInt( response.body().getDataCount() )>0){
                    rightNow = Calendar.getInstance();

                    List<NotificaitionObject> notificationOuterResponses = response.body().getNotificaitionObjectList();
                    notificationcontent = response.body().getNotificaitionObjectList().get( notificationOuterResponses.size()-1 ).getContent();

                    Log.d("ALAALLQQQQ", String.valueOf( "TRUE" ) );
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,  rightNow.get(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE,  rightNow.get(Calendar.MINUTE));
                    calendar.set(Calendar.SECOND, 0);
                    Intent intent1 = new Intent(NotificationService.this, NotificationReceiver.class);
                    intent1.putExtra( "content",notificationcontent );
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationService.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager am = (AlarmManager) NotificationService.this.getSystemService(NotificationService.this.ALARM_SERVICE); // These allow you to schedule your application to be run at some point in the future.
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                }
            }

            @Override
            public void onFailure(Call<NotificationOuterResponse> call, Throwable t) {

            }
        } );
    }

    private void gettingId(){
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Call<MemberObject> memberObjectCall = api.fetchingUser(  FirebaseAuth.getInstance().getCurrentUser().getEmail());

        memberObjectCall.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

                try{
                    gettingNotification( response.body().getData().getMemberId() );

                } catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }
        } );


    }
}
