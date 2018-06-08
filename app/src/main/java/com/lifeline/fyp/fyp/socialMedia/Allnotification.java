package com.lifeline.fyp.fyp.socialMedia;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.NotificationOuterResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Allnotification extends AppCompatActivity {

    Retrofit retrofit;
    ArrayList<String> uniqueNotification;
    ArrayList<NotificationStructure> notificationpost;
    RecyclerView notifications;
    RecyclerView.Adapter notificationadapter;
    LinearLayoutManager dpnoti;
    Integer memberId;
    RelativeLayout layout;
    Call<NotificationOuterResponse> notificationOuterResponseCall;
    TextView allnoti,newnoti;

    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_allnotification );


        uniqueNotification = new ArrayList<>();
        notificationpost = new ArrayList<>();
        layout = (RelativeLayout) findViewById( R.id.layout );
        notifications = (RecyclerView) findViewById( R.id.notification );
        dpnoti = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        allnoti = (TextView) findViewById( R.id.allnotification );
        newnoti = (TextView) findViewById( R.id.newnotification );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            memberId = extras.getInt( "memberId" ); // information contains boolean+gender+age+lifestylecategory.

        }

        gettingNotification( memberId );
    }


    private void gettingNotification(final Integer memberId) {
        final ProgressDialog dialog = new ProgressDialog( Allnotification.this );
        dialog.setCancelable( false );
        dialog.setMessage( "Loading..." );
        dialog.show();
        notificationOuterResponseCall
                = api.notificationresponse( memberId );

        notificationOuterResponseCall.enqueue( new Callback<NotificationOuterResponse>() {
            @Override
            public void onResponse(Call<NotificationOuterResponse> call, Response<NotificationOuterResponse> response) {


                for (int i = 0; i < response.body().getNotificaitionObjectList().size(); i++) {


                    Log.d( "LALlalLAL", String.valueOf( response.body().getNotificaitionObjectList().get( i ).isSeen() ) );
                            if (!uniqueNotification.contains( response.body().getNotificaitionObjectList().get( i ).getContent() )) {

                                if(!response.body().getNotificaitionObjectList().get( i ).isSeen()){
                                    uniqueNotification.add( response.body().getNotificaitionObjectList().get( i ).getContent() );
                                    notificationpost.add( new NotificationStructure(
                                            response.body().getNotificaitionObjectList().get( i ).getId(),
                                            response.body().getNotificaitionObjectList().get( i ).getContent(),
                                            response.body().getNotificaitionObjectList().get( i ).getTime(),
                                            response.body().getNotificaitionObjectList().get( i ).getPostId(),
                                            response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getFirstName() + " " +
                                                    response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getLastName(),
                                            response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getEmail(),
                                            response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getProfilePicture(),
                                            response.body().getNotificaitionObjectList().get( i ).getNotificationType(),
                                            response.body().getNotificaitionObjectList().get( i ).isSeen() ) );




                                }

                    }

                }

                if (notificationpost.size() == 0){
                    notifications.setVisibility( View.INVISIBLE );
                    allnoti.setVisibility( View.INVISIBLE );
                    newnoti.setVisibility( View.VISIBLE );

                }

                notificationadapter = new NotificationAdapter( notificationpost, Allnotification.this,memberId );
                notifications.setAdapter( notificationadapter );
                notifications.setLayoutManager( dpnoti );
                Log.d( "ALQLWLLQ", String.valueOf( notificationadapter.getItemCount() ) );
                dialog.hide();
                layout.setVisibility( View.VISIBLE );
//
            }


            @Override
            public void onFailure(Call<NotificationOuterResponse> call, Throwable t) {

            }
        } );


    }

}


