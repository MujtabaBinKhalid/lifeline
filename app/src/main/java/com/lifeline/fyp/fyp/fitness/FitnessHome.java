package com.lifeline.fyp.fyp.fitness;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.diet.DietSession;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FitnessHome extends AppCompatActivity {

    FloatingActionButton btn;
    String email;
    Api api;
    Retrofit retrofit;
    FirebaseAuth auth;
    FirebaseUser user;
    Call<MemberObject> call;
    Call<FitnessResponseObject> call2;
    TextView tip;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fitness_home );


        tip = (TextView) findViewById( R.id.tipnutri );
        // firebase and retrofit.

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        // setting retrofit.
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        btn = (FloatingActionButton) findViewById( R.id.fabcreate );

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity();
            }
        } );

        CheckingPlanStatus();
    }


    private void newActivity(){
        Intent intent = new Intent( FitnessHome.this, FitnessPlan.class );
        startActivity( intent );

    }

    // if only a dietplan , not linked with fitness plan is active or not.
    private void CheckingPlanStatus() {



        Runnable run = new Runnable() {
            @Override
            public void run() {

                call = api.fetchingUser( email );


                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                        if (response.isSuccessful()) {

                            id = response.body().getData().getMemberId();

                            call2 = api.checkingfitnessplan( id );
                            call2.enqueue( new Callback<FitnessResponseObject>() {
                                @Override
                                public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {

                                    try {
                                        String status = response.body().getData().getActiveStatus();

                                        if (status.equals( "active" )) {
                                            Log.d( "ssss", String.valueOf( "Balay44 balay" ) );

                                            Intent intent = new Intent( FitnessHome.this,FitnessSession.class );
                                            startActivity(intent);
                                        }

                                        else if (!status.equals( "active" )){

                                            Log.d( "ssss", String.valueOf( "Balay55 balay" ) );
//////////////////////////////////////////////////////////////////////////////////////////////////////////

                                            Call <ResponsePlanArray> call2 = api.checkingplan( id );
                                            call2.enqueue( new Callback<ResponsePlanArray>() {
                                                @Override
                                                public void onResponse(Call<ResponsePlanArray> call, Response<ResponsePlanArray> response) {

                                                    try {
                                                        List<SendingPlan> users = response.body().getData();
                                                        String[] status = new String[users.size()];


                                                        for (int i = 0; i < users.size(); i++) {
                                                            status[i] = users.get( i ).getActiveStatus();
                                                        }

                                                        try {
                                                            if (status[status.length - 1].equals( "noactive" )) {
                                                                Log.d( "ssss", String.valueOf( "Balayaa balay" ) );
                                                                tip.setText( "Add plan and start working !" );
                                                                tip.setVisibility( View.VISIBLE );
                                                                btn.setVisibility( View.VISIBLE );

                                                                /// tu me yahan option de dnga .
                                                            }
                                                            // plan not set.
                                                            else if (!status[status.length - 1].equals( "active" )) {
                                                                Log.d( "ssss", String.valueOf( "Balaybb balay" ) );
                                                                tip.setText( "Add plan and start working !" );
                                                                tip.setVisibility( View.VISIBLE );
                                                                btn.setVisibility( View.VISIBLE );


                                                            }
                                                            //plan set.
                                                            else if (status[status.length - 1].equals( "active" )) {
                                                                Log.d( "ssss", String.valueOf( "Balaycc balay" ) );
                                                                tip.setText( "Diet plan is in progress." );
                                                                tip.setVisibility( View.VISIBLE );
                                                                btn.setVisibility( View.INVISIBLE );

                                                            }


                                                        } catch (IndexOutOfBoundsException e) {

                                                            Log.d( "ssss", String.valueOf( "Balaydd balay" ) );
                                                            tip.setText( "Add plan and start working !" );
                                                            tip.setVisibility( View.VISIBLE );
                                                            btn.setVisibility( View.VISIBLE );


                                                        }

                                                    } catch (NullPointerException e) {
                                                        Log.d( "ssss", String.valueOf( "Balayee balay" ) );

                                                    }



                                                }

                                                @Override
                                                public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

                                                } // failure ending
                                            } ); // call response ending inner response ending



////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        } //  END OF ELSE-IF.

                                        } // try end.


                                    // Other catch
                                         catch (NullPointerException e) {
                                             Log.d( "ssss", String.valueOf( "Balay66 balay" ) );
                                             tip.setText( "Add plan and start working !" );
                                             tip.setVisibility( View.VISIBLE );
                                             btn.setVisibility( View.VISIBLE );

                                         }



                                }

                                @Override
                                public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                                } // failure ending
                            } ); // call response ending inner response ending


                        }


                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {
                            // /// THE FAILURE METHOD OF THE OUTER MOST CaALL WHICH IS USED FOR THE ACESSING MEMBER.
                    }
                } ); // outer .


          //      handler.sendEmptyMessage( 0 );
            }
        };

        Thread runnable = new Thread( run );
        runnable.start();



    }

}


