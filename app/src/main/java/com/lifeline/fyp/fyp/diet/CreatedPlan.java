package com.lifeline.fyp.fyp.diet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.FaceShapeQuestion;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.models.FitnessDietPlanResponse;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.PlanObject;
import com.lifeline.fyp.fyp.models.SendingFDObject;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.OuterFitnessResponse;
import com.lifeline.fyp.fyp.retrofit.responses.PlanResponse;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class CreatedPlan extends AppCompatActivity {

    String info, state;
    RelativeLayout cv;
    String[] sep, wgt, hgt;
    double activityvalue;
    Retrofit retrofit;
    Api api;
    RelativeLayout layout;
    String planname, weeks,fitnessId;
    int id;
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    Call<PlanResponse> call;
    Call<MemberObject> call2;
    ProgressDialog progressDialog;
    Call<FitnessDietPlanResponse>callinfinite;
    Call<ResponsePlanArray> call3;
    int actionStatus;
    CardView buttonpressed;
    String buttonText, email;
    Double weight, height;
    TextView bmi1, calintake1, iw1, minhw1, maxhw1, wg1, wl1, wr1, textgain1, textweeks1;
    TextView bmi, calintake, iw, minhw, maxhw, wg, wl, wr, textgain, textweeks;
    String bmivalue1, calintakevalue1, iwvalue1, minhwvalue1, maxhwvalue1, wgvalue1, wlvalue1, wrvalue1;
    String bmivalue, calintakevalue, iwvalue, minhwvalue, maxhwvalue, wgvalue, wlvalue, wrvalue;
    Member updatedmember;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            // weight gain.
            if (wlvalue.equals( "-1.0" )) {

                bmi.setText( bmivalue );
                calintake.setText( calintakevalue );
                iw.setText( iwvalue );
                minhw.setText( minhwvalue );
                maxhw.setText( maxhwvalue );
                wg.setText( wgvalue );
                wr.setText( wrvalue );

                bmi1.setText( bmivalue1 );
                calintake1.setText( calintakevalue1 );
                iw1.setText( iwvalue1 );
                minhw1.setText( minhwvalue1 );
                maxhw1.setText( maxhwvalue1 );
                wg1.setText( wgvalue1 );
                wr1.setText( wrvalue1 );
                //wr1.setText( wrvalue1 );
                progressDialog.hide();
                layout.setVisibility( View.VISIBLE );
            }


            // weight loose.
            else if (wgvalue.equals( "-1.0" )) {

                textgain.setText( "Weight to loose" );

                bmi.setText( bmivalue );
                calintake.setText( calintakevalue );
                iw.setText( iwvalue );
                minhw.setText( minhwvalue );
                maxhw.setText( maxhwvalue );
                wg.setText( wlvalue );
                wr.setText( wrvalue );

                bmi1.setText( bmivalue1 );
                calintake1.setText( calintakevalue1 );
                iw1.setText( iwvalue1 );
                minhw1.setText( minhwvalue1 );
                maxhw1.setText( maxhwvalue1 );
                wg1.setText( wlvalue1 );
                wr1.setText( wrvalue1 );
                progressDialog.hide();
                layout.setVisibility( View.VISIBLE );
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_created_plan );

        // fetching email;
        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        info = extras.getString( "information" );
        state = extras.getString( "state" );
        weeks = extras.getString( "weeks" );
        fitnessId = extras.getString( "fitnessId" );
        planname = extras.getString( "planname" );




        IntialzingTextfields();
        cv = (RelativeLayout) findViewById( R.id.nameofplan );
        layout = (RelativeLayout) findViewById( R.id.layout );

        cv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////
                final AlertDialog.Builder builder = new AlertDialog.Builder( CreatedPlan.this );
                builder.setMessage( "Do you want to continue with this plan?" );
                builder.setCancelable( true );
                builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        updatedmember = new Member( id, email, email, String.valueOf( height ), String.valueOf( weight.intValue() ) );
                        if (state.equals( "Onlyfit" )){

                            SendingFDObject sendingPlan = new SendingFDObject( Integer.parseInt( fitnessId ), Integer.parseInt( calintakevalue ),actionStatus );
                           // SendingFDObject sendingPlan = new SendingFDObject( 88,2749,1 );
                            creatingFitnessDietPlan(sendingPlan,updatedmember);

                        }
                        else {
                            SendingPlan sp = new SendingPlan( id, planname, Double.parseDouble( wrvalue ), Integer.parseInt( calintakevalue ), actionStatus, "active" );
                            Creatingplan( sp, updatedmember );
                        }


                    }
                } );
                builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                } );

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //////
            }
        } );


        RelativeLayout cv2 = (RelativeLayout) findViewById( R.id.nameofplan2 );

        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Cerating Diet Plans" );
        progressDialog.setCancelable( false );
        progressDialog.show();

        cv2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder( CreatedPlan.this );
                builder.setMessage( "Do you want to continue with this plan?" );
                builder.setCancelable( true );
                builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updatedmember = new Member( id, email, email, String.valueOf( height ), String.valueOf( weight.intValue() ) );

                        if (state.equals( "Onlyfit" )){
                            SendingFDObject sendingPlan = new SendingFDObject( Integer.parseInt( fitnessId ), Integer.parseInt( calintakevalue ),actionStatus );
                            creatingFitnessDietPlan(sendingPlan,updatedmember);


                        }
                        else {
                            SendingPlan sp = new SendingPlan( id, planname, Double.parseDouble( wrvalue1 ),  Integer.parseInt( calintakevalue ), actionStatus, "active" );

                            Creatingplan( sp, updatedmember );
                        }


                    }
                } );
                builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                } );

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } );

        sep = info.split( ";" );


        Log.d( "ta", sep[1] );
        Log.d( "tg", sep[2] );
        Log.d( "th", sep[3] );
        Log.d( "tw", sep[4] );

        try {
            height = Double.parseDouble( sep[3] );
        } catch (NumberFormatException e) {
            String exceptionheight = sep[3];
            hgt = exceptionheight.split( "'" );
            height = Double.parseDouble( hgt[0] ) * 30.48 + Double.parseDouble( hgt[1] ) * 2.54;
        }


        try {
            weight = Double.parseDouble( sep[4] );
        } catch (NumberFormatException e) {
            String exception = sep[4];
            wgt = exception.split( "-" );
            weight = Double.parseDouble( wgt[0] ) + Double.parseDouble( wgt[1] ) / 2;
        }

        try {
            activityvalue = Double.parseDouble( sep[5] );
        } catch (IndexOutOfBoundsException e) {
            activityvalue = 1.2;
        }

        Log.d( "activityvalue", String.valueOf( activityvalue ) );


        //double weight, double height, int age, double af, String gender

        // retrofit = new Retrofit.Builder()
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Log.d( "w1", String.valueOf( weight ) );
        Log.d( "h1", String.valueOf( height ) );
        Log.d( "a1", sep[1] );
        Log.d( "ac1", String.valueOf( activityvalue ) );
        Log.d( "g1", sep[2] );


        fetchingid( email );

        if (state.equals( "Onlyfit" )) {
            Log.d( "mmnnbb", String.valueOf( weight ) );
            Log.d( "mmnnbb", String.valueOf( height ) );
            Log.d( "mmnnbb",sep[1] );
            Log.d( "mmnnbb",sep[5] );
            Log.d( "mmnnbb",sep[2] );
            Log.d( "mmnnbb",weeks );

            PlanObject plano = new PlanObject( weight,
                    height,
                    Integer.parseInt( sep[1] ),
                    Double.parseDouble( sep[5] ), sep[2], weeks );
            Log.d( "Sdsdsdsd","Dsdsdsdsd" );
            Toast.makeText( CreatedPlan.this, "Swipe down to view more.", Toast.LENGTH_LONG ).show();

            Log.d("weeee",plano.getWeeks());

            CreatingFitnessDietSuggestions( plano );


        } else if (!state.equals( "Onlyfit" )) {

            PlanObject po = new PlanObject( weight,
                    height,
                    Integer.parseInt( sep[1] ),
                    activityvalue, sep[2] );
            Log.d( "Sdsdsdsd","Dsdsdsdsd11122" );


            Toast.makeText( CreatedPlan.this, "Swipe down to view more.", Toast.LENGTH_LONG ).show();
            creatingplan( po );

        }


    }

    // api Call.
    public void creatingplan(PlanObject po) {
        call = api.creatingplan( po );
        call.enqueue( new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, final Response<PlanResponse> response) {

                actionStatus = response.body().getData().getActionStatus();
                //  Log.d( "messi", String.valueOf( response.body() ) );

                try {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {


                            bmivalue = response.body().getData().getPlana().getWeightstatus();
                            calintakevalue = response.body().getData().getPlana().getCalorieintake() + "";
                            iwvalue = response.body().getData().getPlana().getIdealweight() + "";
                            minhwvalue = response.body().getData().getPlana().getMinhw() + "";
                            maxhwvalue = response.body().getData().getPlana().getMaxhw() + "";
                            wgvalue = response.body().getData().getPlana().getWg() + "";
                            wlvalue = response.body().getData().getPlana().getWl() + "";
                            wrvalue = response.body().getData().getPlana().getWr() + "";


                            bmivalue1 = response.body().getData().getPlanb().getWeightstatus();
                            calintakevalue1 = response.body().getData().getPlanb().getCalorieintake() + "";
                            iwvalue1 = response.body().getData().getPlanb().getIdealweight() + "";
                            minhwvalue1 = response.body().getData().getPlanb().getMinhw() + "";
                            maxhwvalue1 = response.body().getData().getPlanb().getMaxhw() + "";
                            wgvalue1 = response.body().getData().getPlanb().getWg() + "";
                            wlvalue1 = response.body().getData().getPlanb().getWl() + "";
                            wrvalue1 = response.body().getData().getPlanb().getWr() + "";
                            handler.sendEmptyMessage( 0 );

                        }

                    };


                    Thread runnable = new Thread( r );
                    runnable.start();


                } catch (NullPointerException e) {

                }

                // second thread.

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                Toast.makeText( CreatedPlan.this, "Error", Toast.LENGTH_LONG ).show();
                Log.d( "error", t.getMessage() );
            } // failure ending
        } ); // call response ending


    }

    // intialization of all the text fileds.
    public void IntialzingTextfields() {
        bmi = (TextView) findViewById( R.id.bmiiiivalued );
        calintake = (TextView) findViewById( R.id.callloorieevalue );
        iw = (TextView) findViewById( R.id.iwvaluez );
        minhw = (TextView) findViewById( R.id.mhweightvalue );
        maxhw = (TextView) findViewById( R.id.maxhweightvalue );
        wg = (TextView) findViewById( R.id.wtogainvalue );
        wr = (TextView) findViewById( R.id.wkreqvalue );
        textgain = (TextView) findViewById( R.id.wtogain );
        textweeks = (TextView) findViewById( R.id.wkreq );

        bmi1 = (TextView) findViewById( R.id.bmiiiivalued1 );
        calintake1 = (TextView) findViewById( R.id.callloorieevalue1 );
        iw1 = (TextView) findViewById( R.id.iwvaluez1 );
        minhw1 = (TextView) findViewById( R.id.mhweightvalue1 );
        maxhw1 = (TextView) findViewById( R.id.maxhweightvalue1 );
        wg1 = (TextView) findViewById( R.id.wtogainvalue1 );
        wr1 = (TextView) findViewById( R.id.wkreqvalue1 );
        textgain1 = (TextView) findViewById( R.id.wtogain1 );
        textweeks1 = (TextView) findViewById( R.id.wkreq1 );


    }

    // fetching currently active user id.
    public void fetchingid(String email) {
        // checking now.

        call2 = api.fetchingUser( email );
        call2.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, final Response<MemberObject> response) {


                try {
                    Runnable runn = new Runnable() {
                        @Override
                        public void run() {
                            id = response.body().getData().getMemberId();
                        }
                    };
                    Thread thread = new Thread( runn );
                    thread.start();

                } catch (NullPointerException e) {
                    Toast.makeText( CreatedPlan.this, "Network-error", Toast.LENGTH_LONG ).show();
                }


            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

                Toast.makeText( CreatedPlan.this, "Failed", Toast.LENGTH_SHORT ).show();
            } // failure ending
        } ); // call response ending

    }

     // creating the diet plan of the the fitness plan.
    public void creatingFitnessDietPlan (final SendingFDObject sp, final Member updatedmember) {

        callinfinite = api.insertingfitnessDietPlan(sp);
       callinfinite.enqueue( new Callback<FitnessDietPlanResponse>() {
           @Override
           public void onResponse(Call<FitnessDietPlanResponse> call, Response<FitnessDietPlanResponse> response) {


               updatingMember( updatedmember );
//               Intent intent = new Intent( CreatedPlan.this, DietMain.class );
//               intent.putExtra( "information", id + "" );
//               finish();
//               startActivity( intent );
               Intent intent = new Intent( CreatedPlan.this, FitnessSession.class );
               intent.putExtra( "flow","noexercise;" );

               startActivity( intent );
               finish();

           }

           @Override
           public void onFailure(Call<FitnessDietPlanResponse> call, Throwable t) {

           }
       } );

    }

    // creating the plan only deit plan . or helps in deit and firness plan.
    public void Creatingplan(final SendingPlan sp, final Member updatedmember) {

        call3 = api.insertingPlan( sp );
        call3.enqueue( new Callback<ResponsePlanArray>() {
            @Override
            public void onResponse(Call<ResponsePlanArray> call, final Response<ResponsePlanArray> response) {

                try {

                    try {
                        if (state.equals( "Onlydiet" )) {
                            Log.d( "messiV", String.valueOf( response ) );

                            FetchingDietId();
                        } else {

                            Log.d( "messi", String.valueOf( response ) );
                            Log.d( "messi", String.valueOf( response.body() ) );


                            updatingMember( updatedmember );
                            Intent intent = new Intent( CreatedPlan.this, DietMain.class );
                            intent.putExtra( "information", id + "" );
                            startActivity( intent );
                            finish();
                        }

                    } catch (NullPointerException e) {
                        Log.d( "messi", String.valueOf( response ) );
                        Log.d( "messi", String.valueOf( response.body() ) );
                        updatingMember( updatedmember );
                        Intent intent = new Intent( CreatedPlan.this, DietMain.class );
                        intent.putExtra( "information", id + "" );
                        startActivity( intent );
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText( CreatedPlan.this, "Network-error", Toast.LENGTH_LONG ).show();
                }


                //                        try {
                //                            Runnable runn = new Runnable() {
                //                                @Override
                //                                public void run() {
                //
                //
                //                                }
                //                            };
                //                            Thread thread = new Thread( runn );
                //                            thread.start();

                //                        } catch (NullPointerException e) {
                //                            Toast.makeText( CreatedPlan.this, "Network-error", Toast.LENGTH_LONG ).show();
                //                        }


            }

            @Override
            public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

                Toast.makeText( CreatedPlan.this, "Failed", Toast.LENGTH_SHORT ).show();
            } // failure ending
        } ); // call response ending


    } // method. ending.

     // updating the user height and weight.
    public void updatingMember(Member updatedmember) {
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                Call<MemberObject> call4 = api.updateUser( id, CreatedPlan.this.updatedmember );
                call4.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                    }
                } );


            }
        };

        Thread runnable2 = new Thread( r2 );
        runnable2.start();

    }

    // fetching the diet id , so that it can be linked with the fitness plan if erequired.
    private void FetchingDietId() {

        Call<ResponsePlanArray> call2 = api.checkingplan( id );
        call2.enqueue( new Callback<ResponsePlanArray>() {


            @Override
            public void onResponse(Call<ResponsePlanArray> call, Response<ResponsePlanArray> response) {

                List<SendingPlan> users = response.body().getData();
                Integer dietPlanIdgetter = users.get( users.size() - 1 ).getDietPlanId();

                Log.d( "ccc", String.valueOf( dietPlanIdgetter ) );

                CreatingDietFitnessPlan( dietPlanIdgetter );

            }

            @Override
            public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

            }
        } );

    }

    // creating the fitness plan of the diet plan.
    private void CreatingDietFitnessPlan(Integer dietId) {

        final Call<FitnessResponseObject> call = api.cdfplan( dietId );


        Runnable runexcuting = new Runnable() {
            @Override
            public void run() {
                call.enqueue( new Callback<FitnessResponseObject>() {
                    @Override
                    public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {

                        updatingMember( updatedmember );
                        Intent intent = new Intent( CreatedPlan.this, DietMain.class );
                        intent.putExtra( "information", id + "" );
                        startActivity( intent );
                        finish();

                        Log.d( "SdAAAAAAAAAAAAAAAAs", String.valueOf( response ) );
                        Log.d( "SdsDDDD", response.body().getMessage() );
                        Log.d( "SdSSSSSs", response.message() );


                    }

                    @Override
                    public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                    }
                } );

            }
        };

        Thread threadexcuting = new Thread( runexcuting );
        threadexcuting.start();


    }

    // getting suggestions of the diet for the fitness plan.
    private void CreatingFitnessDietSuggestions(PlanObject po){
        call = api.fitnessdietplan( po );
        call.enqueue( new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, final Response<PlanResponse> response) {

                Log.d( "messi22Sulaaaabbbb", String.valueOf( response ) );
try{
    actionStatus = response.body().getData().getActionStatus();

}
catch (NullPointerException e){
    actionStatus = response.body().getData().getActionStatus();

}
                //  Log.d( "messi", String.valueOf( response.body() ) );

                try {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {


                            bmivalue = response.body().getData().getPlana().getWeightstatus();
                            calintakevalue = response.body().getData().getPlana().getCalorieintake() + "";
                            iwvalue = response.body().getData().getPlana().getIdealweight() + "";
                            minhwvalue = response.body().getData().getPlana().getMinhw() + "";
                            maxhwvalue = response.body().getData().getPlana().getMaxhw() + "";
                            wgvalue = response.body().getData().getPlana().getWg() + "";
                            wlvalue = response.body().getData().getPlana().getWl() + "";
                            wrvalue = response.body().getData().getPlana().getWr() + "";


                            bmivalue1 = response.body().getData().getPlanb().getWeightstatus();
                            calintakevalue1 = response.body().getData().getPlanb().getCalorieintake() + "";
                            iwvalue1 = response.body().getData().getPlanb().getIdealweight() + "";
                            minhwvalue1 = response.body().getData().getPlanb().getMinhw() + "";
                            maxhwvalue1 = response.body().getData().getPlanb().getMaxhw() + "";
                            wgvalue1 = response.body().getData().getPlanb().getWg() + "";
                            wlvalue1 = response.body().getData().getPlanb().getWl() + "";
                            wrvalue1 = response.body().getData().getPlanb().getWr() + "";
                            handler.sendEmptyMessage( 0 );

                        }

                    };


                    Thread runnable = new Thread( r );
                    runnable.start();


                } catch (NullPointerException e) {

                }

                // second thread.

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                Toast.makeText( CreatedPlan.this, "Error", Toast.LENGTH_LONG ).show();
                Log.d( "errorBtao", t.getMessage() );
            } // failure ending
        } ); // call response ending

    }

}
