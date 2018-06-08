package com.lifeline.fyp.fyp.diet;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.ScreenShot;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.models.FetchingCalorie;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.RequestingDietSession;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.UploadImageInterface;
import com.lifeline.fyp.fyp.retrofit.responses.CalorieSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.DeletingPlan;
import com.lifeline.fyp.fyp.retrofit.responses.FetchingSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.InsertSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.PostingStatus;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;
import com.lifeline.fyp.fyp.retrofit.responses.StatusResponse;
import com.lifeline.fyp.fyp.retrofit.responses.UploadObject;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietPlans extends Fragment {


    // ONLY DIET IS USED WHEN THE USER WANTS TO REGISTER FITNESS PLAN WITH IT.

    FloatingActionButton fab;
    Call<MemberObject> call;
    private SimpleDateFormat df;
    UploadImageInterface uploadImage;
    Call<ResponsePlanArray> call2;
    Call<FitnessResponseObject> calld;
    Call<FetchingSessionResponse> call3;
    Retrofit retrofit;
    Integer DietPlanid;
    Api api;
    public ArrayList<Float> caloriesvalues;
    public ArrayList<String> caloriesdetails;
    String email;
    Button cal, weeks, session;
    double calintakevalue, weeksvalue;
    Integer id;
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    private String userheight, userweight, allinformation, formattedDate;
    private RelativeLayout notactive, stats;
    private ScrollView active;
    private TextView name, ssession, tippp, fittitle;
    private String namevalue;
    private String dietplanid;
    float actualcalories;
    float caleatentoday;
    float calremaining;
    private Integer idofdietplan;
    TextView sessiondetail, hidedetails, msg, err;
    private PieChart pieChart;
    private TextView share;
    String path2;
    private String[] current;
    private String[] utc;
    private Calendar calender;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    TextView date;
    Integer idofthemember;



    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            cal.setText( calintakevalue + "" );
            weeks.setText( weeksvalue + "" );
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /////
        // home


        /////
        View view = inflater.inflate( R.layout.fragment_diet_plans, container, false );


        getActivity().setTitle( "Diet" );
        // nav
        ImageButton home = (ImageButton) view.findViewById( R.id.sweethome3 );
        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), MainActivity.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );

        share = (TextView) view.findViewById( R.id.share );

        // diet
        ImageButton diet = (ImageButton) view.findViewById( R.id.apnadiet3 );
        diet.setEnabled( false );
        diet.setClickable( false );

        ImageButton lifestyle = (ImageButton) view.findViewById( R.id.lifestylediet );

        pieChart = (PieChart) view.findViewById( R.id.chartpie );

        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), LifeStyle.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );

        ImageButton profile = (ImageButton) view.findViewById( R.id.profile );


        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), UserProfile.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );


        cal = (Button) view.findViewById( R.id.calintakebtn );
        weeks = (Button) view.findViewById( R.id.weeksbtn );
        sessiondetail = (TextView) view.findViewById( R.id.sessiondetail );
        hidedetails = (TextView) view.findViewById( R.id.hidedetails );
        msg = (TextView) view.findViewById( R.id.messagehere );
        err = (TextView) view.findViewById( R.id.errorhere );
        ssession = (TextView) view.findViewById( R.id.startsessionagain );
        tippp = (TextView) view.findViewById( R.id.tipnutri );
        fittitle = (TextView) view.findViewById( R.id.fittitle );
        date = (TextView) view.findViewById( R.id.date );


        // fitnese btn of the nav bar.
        ImageButton fitness = (ImageButton) view.findViewById( R.id.fitness );

        fitness.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), FitnessSession.class );
                intent.putExtra( "flow", "noexercise;" );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right ); // right to left.

            }
        } );

        fittitle.setVisibility( View.INVISIBLE );
        fittitle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), FitnessSession.class );
                intent.putExtra( "flow", "noexercise;" );
                startActivity( intent );


            }
        } );

        active = (ScrollView) view.findViewById( R.id.active );
        notactive = (RelativeLayout) view.findViewById( R.id.notactive );
        stats = (RelativeLayout) view.findViewById( R.id.stats );
        name = (TextView) view.findViewById( R.id.planrecentname );
        // fetching id
        DietMain ls = (DietMain) getActivity();
        //        id =  Integer.parseInt( ls.getId() );

        //        Log.d( "dfdfdfdfdf",getArguments().getString("id")    );


        // dleeting plab optopn,

        name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskingUser();
            }
        } );

        // setting firebase.
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
        uploadImage = retrofit.create( UploadImageInterface.class );


        Runnable run = new Runnable() {
            @Override
            public void run() {

                call = api.fetchingUser( email );


                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                        if (response.isSuccessful()) {

                            id = response.body().getData().getMemberId();

                            call2 = api.checkingplan( id );
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
                                                Log.d( "kk22", "kya tum idher ate " );

                                                Log.d( "ssss", String.valueOf( "Balay balay" ) );
                                                calld = api.checkingfitnessplan( id );
                                                calld.enqueue( new Callback<FitnessResponseObject>() {
                                                    @Override
                                                    public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                                                        //SendingPlan users = response.body().getData().getActionStatus();
                                                        try {
                                                            Log.d( "ssss", String.valueOf( "Balay33 balay" ) );

                                                            String status = response.body().getData().getActiveStatus();
                                                            if (status.equals( "active" )) {
                                                                Log.d( "ssss", String.valueOf( "Balay44 balay" ) );

                                                                active.setVisibility( View.INVISIBLE );
                                                                notactive.setVisibility( View.VISIBLE );
                                                                stats.setVisibility( View.INVISIBLE );
                                                                fab.setVisibility( View.INVISIBLE );
                                                                tippp.setText( "Fitness Plan is in progress." );

                                                            } else if (!status.equals( "active" )) {
                                                                active.setVisibility( View.INVISIBLE );
                                                                notactive.setVisibility( View.VISIBLE );
                                                                stats.setVisibility( View.INVISIBLE );
                                                                fab.setVisibility( View.VISIBLE );
                                                                tippp.setText( "Create your plan." );
                                                            }


                                                        } catch (Exception e) {
                                                            Log.d( "ssss", String.valueOf( "Balay22 balay" ) );

                                                            active.setVisibility( View.INVISIBLE );
                                                            notactive.setVisibility( View.VISIBLE );
                                                            stats.setVisibility( View.INVISIBLE );
                                                            fab.setVisibility( View.VISIBLE );
                                                            tippp.setText( "Create your plan." );

                                                        }


                                                    }

                                                    @Override
                                                    public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                                                        Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                                        Log.d( "dfdfd", t.getMessage() );
                                                    } // failure ending
                                                } ); // call response ending inner response ending

                                            }
                                            // plan not set.
                                            else if (!status[status.length - 1].equals( "active" )) {

                                                Log.d( "kk11", "kya tum idher ate " );
                                                // one more api call to check whether the ONLY fitness plan is on or not.
                                                ////////////////////////////////////////////////////
                                                calld = api.checkingfitnessplan( id );
                                                calld.enqueue( new Callback<FitnessResponseObject>() {
                                                    @Override
                                                    public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                                                        //SendingPlan users = response.body().getData().getActionStatus();

                                                        try {
                                                            String status = response.body().getData().getActiveStatus();
                                                            if (status.equals( "active" )) {
                                                                active.setVisibility( View.INVISIBLE );
                                                                notactive.setVisibility( View.VISIBLE );
                                                                stats.setVisibility( View.INVISIBLE );
                                                                fab.setVisibility( View.INVISIBLE );
                                                                tippp.setText( "Fitness Plan is in progress." );
                                                            } else if (!status.equals( "active" )) {
                                                                active.setVisibility( View.INVISIBLE );
                                                                notactive.setVisibility( View.VISIBLE );
                                                                stats.setVisibility( View.INVISIBLE );
                                                                fab.setVisibility( View.VISIBLE );
                                                                tippp.setText( "Create your plan." );


                                                            }

                                                        } catch (Exception e) {
                                                            active.setVisibility( View.INVISIBLE );
                                                            notactive.setVisibility( View.VISIBLE );
                                                            stats.setVisibility( View.INVISIBLE );
                                                            fab.setVisibility( View.VISIBLE );
                                                            tippp.setText( "Create your plan." );

                                                        }


                                                    }

                                                    @Override
                                                    public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                                                        Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                                        Log.d( "dfdfd", t.getMessage() );
                                                    } // failure ending
                                                } ); // call response ending inner response ending


                                                ////////////////////////////////////////////////////


                                            }
                                            //plan set.
                                            else if (status[status.length - 1].equals( "active" )) {
                                                active.setVisibility( View.VISIBLE );
                                                notactive.setVisibility( View.INVISIBLE );
                                                stats.setVisibility( View.INVISIBLE );

                                                // checking if the diet has an fitnessplan with it.
///////////////////////////////////////////////////
                                                Call<FitnessResponseObject> callq = api.checkingfitnessplan( id );
                                                callq.enqueue( new Callback<FitnessResponseObject>() {
                                                    @Override
                                                    public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {

                                                        String statusChecker = response.body().getData().getActiveStatus();

                                                        // when the fitnessplan is active.
                                                        if (statusChecker.equals( "active" )) {
                                                            Log.d( "dfdfd", "ghghgh" );
                                                            fittitle.setVisibility( View.VISIBLE );

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                                                    }
                                                } );

//////////////////////////////////////////////////////////////////////////


                                                DietPlanid = users.get( users.size() - 1 ).getDietPlanId();
                                                calintakevalue = users.get( users.size() - 1 ).getDialyCalorieIntake();
                                                weeksvalue = users.get( users.size() - 1 ).getWeeks();

                                                namevalue = users.get( users.size() - 1 ).getName();

                                                dietplanid = users.get( users.size() - 1 ).getDietPlanId() + "";

                                                cal.setText( calintakevalue + "" );
                                                weeks.setText( weeksvalue + "" );
                                                name.setText( namevalue );
                                            }


                                        } catch (IndexOutOfBoundsException e) {
                                            Log.d( "kk33", "kya tum idher ate " );
                                            calld = api.checkingfitnessplan( id );
                                            calld.enqueue( new Callback<FitnessResponseObject>() {
                                                @Override
                                                public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                                                    //SendingPlan users = response.body().getData().getActionStatus();

                                                    try {
                                                        String status = response.body().getData().getActiveStatus();
                                                        if (status.equals( "active" )) {
                                                            active.setVisibility( View.INVISIBLE );
                                                            notactive.setVisibility( View.VISIBLE );
                                                            stats.setVisibility( View.INVISIBLE );
                                                            fab.setVisibility( View.INVISIBLE );
                                                            tippp.setText( "Fitness Plan is in progress." );

                                                        }

                                                    } catch (Exception e) {
                                                        active.setVisibility( View.INVISIBLE );
                                                        notactive.setVisibility( View.VISIBLE );
                                                        stats.setVisibility( View.INVISIBLE );
                                                        fab.setVisibility( View.VISIBLE );
                                                        tippp.setText( "Create your plan." );

                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                                                    Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                                    Log.d( "dfdfd", t.getMessage() );
                                                } // failure ending
                                            } ); // call response ending inner response ending

                                        }

                                    } catch (NullPointerException e) {
                                        Log.d( "", String.valueOf( response ) );
                                    }

                                    try {
                                        List<SendingPlan> gettingsession = response.body().getData();
                                        idofdietplan = gettingsession.get( gettingsession.size() - 1 ).getDietPlanId();

                                    } catch (IndexOutOfBoundsException e) {

                                    }


                                }

                                @Override
                                public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

                                    Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                } // failure ending
                            } ); // call response ending inner response ending


                        }


                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                    }
                } ); // outer .
                Log.d( "plandiet", String.valueOf( idofdietplan ) );

                handler.sendEmptyMessage( 0 );
            }
        };

        Thread runnable = new Thread( run );
        runnable.start();


        session = (Button) view.findViewById( R.id.sessionbtn );


        ////// starting session.
        session.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), DietSession.class );
                intent.putExtra( "id", DietPlanid );
                Log.d( "bugtime", String.valueOf( DietPlanid ) );
                startActivity( intent );
                getActivity().finish();


            }
        } );


        fab = (FloatingActionButton) view.findViewById( R.id.fabcreate );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // checking now.
                call = api.fetchingUser( email );
                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, final Response<MemberObject> response) {

                        Log.d( "messi", String.valueOf( response ) );
                        Log.d( "messi", String.valueOf( response.body() ) );


                        try {

                            try {
                                userheight = response.body().getData().getHeight().toString();
                                id = response.body().getData().getMemberId();
                            } catch (NullPointerException e) {
                                userheight = "null";
                            }
                            try {
                                userweight = response.body().getData().getWeight().toString();

                            } catch (NullPointerException e) {
                                userweight = "null";
                            }
                            //////////////////
                            /// checking which question to asked.
                            if (userheight.equals( "null" )) {
                                /////////////////////////
                                // asking a question.
                                final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                                builder.setMessage( "Do you want to link fitness plan with your diet Plan?" );
                                builder.setCancelable( true );
                                builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        allinformation = "true" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender();
                                        Intent intent = new Intent( getActivity(), DietPlanQuestion1.class );
                                        // intent.putExtra( "info", allinformation );
                                        //startActivity( intent );

                                        Bundle extra = new Bundle();
                                        extra.putString( "state", "Onlydiet" );
                                        extra.putString( "info", allinformation );
                                        intent.putExtras( extra );
                                        startActivity( intent );
                                        getActivity().finish();
                                    }
                                } );
                                // if they doesnot want to link their deit plan with the fitness plan then we will fix the activity factor to 1.2
                                builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        allinformation = "true" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender();
                                        Intent intent = new Intent( getActivity(), DietPlanQuestion1.class );
                                        // intent.putExtra( "info", allinformation );
                                        //startActivity( intent );

                                        Bundle extra = new Bundle();
                                        extra.putString( "state", "Onlynotdiet" );
                                        extra.putString( "info", allinformation );
                                        intent.putExtras( extra );
                                        startActivity( intent );
                                        getActivity().finish();

                                    }

                                } );

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                /////////////////////////////////////////////////////

                            } else if (!userheight.equals( "null" )) {
                                //////////////////////////////////////////////////
                                // asking a question.
                                // as the user profile has the height and weight entered , but still we are asking if they want to
                                // update the height and the weight in their profile for the next plan.
                                final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                                builder.setMessage( "Do you want to update your height and weight?" );
                                builder.setCancelable( true );

                                // in this case the user wanted to update the height and weight.
                                builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /////////////////////////////////////////////////////////////////////////////////////////////
                                        final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                                        builder.setMessage( "Do you want to link fitness plan with your diet Plan?" );
                                        builder.setCancelable( true );
                                        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                allinformation = "true" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender();
                                                Intent intent = new Intent( getActivity(), DietPlanQuestion1.class );
                                                // intent.putExtra( "info", allinformation );
                                                //startActivity( intent );

                                                Bundle extra = new Bundle();
                                                extra.putString( "state", "Onlydiet" );
                                                extra.putString( "info", allinformation );
                                                intent.putExtras( extra );
                                                startActivity( intent );
                                                getActivity().finish();

                                            }
                                        } );
                                        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                allinformation = "true" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender();
                                                Intent intent = new Intent( getActivity(), DietPlanQuestion1.class );
                                                // intent.putExtra( "info", allinformation );
                                                //startActivity( intent );

                                                Bundle extra = new Bundle();
                                                extra.putString( "state", "Onlynotdiet" );
                                                extra.putString( "info", allinformation );
                                                intent.putExtras( extra );
                                                startActivity( intent );
                                                getActivity().finish();


                                            }
                                        } );

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();


                                        ///////////////////////////////////////////
//                                        allinformation = "true" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender();
//                                        Intent intent = new Intent( getActivity(), DietPlanQuestion1.class );
//                                        intent.putExtra( "info", allinformation );
//                                        Log.d( "bugtime", String.valueOf( id ) );
//                                        getActivity().finish();
//                                        startActivity( intent );
                                    }
                                } );

                                // he dont wanted to update his weight and now we are asking if he wanted to link fitnessplan with it or not.
                                builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

//////////////////////////////////////////////////////////////////////
                                        final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                                        builder.setMessage( "Do you want to link fitness plan with your diet Plan?" );
                                        builder.setCancelable( true );
                                        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                double value = 1.2;
                                                allinformation = "false" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender() + ";" + response.body().getData().getHeight() + ";" + response.body().getData().getWeight() + ";" + value;
                                                Intent intent = new Intent( getActivity(), FinalzingPlan.class );
                                                // intent.putExtra( "info", allinformation );
                                                //startActivity( intent );

                                                Bundle extra = new Bundle();
                                                extra.putString( "state", "Onlydiet" );
                                                extra.putString( "information", allinformation );
                                                intent.putExtras( extra );
                                                startActivity( intent );
                                                getActivity().finish();

                                            }
                                        } );
                                        // no update in weght and height and no linking of fitness plan.
                                        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                allinformation = "false" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender() + ";" + response.body().getData().getHeight() + ";" + response.body().getData().getWeight();
                                                Intent intent = new Intent( getActivity(), DietPlansQuestions2.class );

                                                Bundle extra = new Bundle();

                                                extra.putString( "state", "diet" );
                                                extra.putString( "info", allinformation );
                                                intent.putExtras( extra );
                                                startActivity( intent );
                                                getActivity().finish();


                                            }
                                        } );

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();


//////////////////////////////////////////////////////////////////////////////////
//
//   allinformation = "false" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender() + ";" + response.body().getData().getHeight() + ";" + response.body().getData().getWeight();
//                                        Intent intent = new Intent( getActivity(), DietPlansQuestions2.class );
//
//                                        Bundle extra = new Bundle(  );
//
//                                        extra.putString("state", "diet");
//                                        extra.putString("info", allinformation);
//                                        intent.putExtras(extra);
//                                        startActivity(intent);
//
////                                        getActivity().finish();
////
////                                        startActivity( intent );

                                    }
                                } );

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();


                                ///////////////////////////////////////////

//                                allinformation = "false" + ";" + response.body().getData().getAge() + ";" + response.body().getData().getGender() + ";" + response.body().getData().getHeight() + ";" + response.body().getData().getWeight();
//                                Intent intent = new Intent( getActivity(), DietPlansQuestions2.class );
//                                intent.putExtra( "info", allinformation );
//                                Log.d( "bugtime", allinformation );
//                                getActivity().getFragmentManager().popBackStack();
//                                startActivity( intent );


                            }


                        } catch (NullPointerException e) {
                            Toast.makeText( getActivity(), "Network-error", Toast.LENGTH_LONG ).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                    } // failure ending
                } ); // call response ending


                // starting session.


            }
        } );

        // last session deatil

        sessiondetail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setVisibility( View.INVISIBLE );
                notactive.setVisibility( View.INVISIBLE );
                stats.setVisibility( View.VISIBLE );

                err.setVisibility( View.INVISIBLE );
                msg.setVisibility( View.INVISIBLE );
                ssession.setVisibility( View.INVISIBLE );
                caloriesdetails = new ArrayList<>();
                caloriesvalues = new ArrayList<>();


                          RequestingDietSession rds = new RequestingDietSession( idofdietplan );


                call3 = api.fetchingsessions( rds );

                call3.enqueue( new Callback<FetchingSessionResponse>() {
                    @Override
                    public void onResponse(Call<FetchingSessionResponse> call, Response<FetchingSessionResponse> response) {
                        List<CalorieSessionResponse> sessionsall = response.body().getData();


                        //// current date.
                        calender = Calendar.getInstance(); //  current instance.
                        df = new SimpleDateFormat( "yyyy-MMM-dd" ); // ormat
                        formattedDate = df.format( calender.getTime() ); // getting time.
                        current = formattedDate.split( "-" ); // seperqtor.
                        String UTC;
                        String onlyDate; // only date.
                        float totalcaloriesoftoday = 0;
                        int checker = 0; // just for me.
                        boolean presentingstatus = false; // if its true then the piechar will appear. otherwise no chart.


                        try {
                            Log.d( "resssssss", String.valueOf( response ) );
                            Log.d( "resssssss", String.valueOf( response.body().getDataCount() ) );
                            Log.d( "resssssss", String.valueOf( response.body().getMessage() ) );
                            Log.d( "resssssss", String.valueOf( sessionsall.get( sessionsall.size() - 1 ).getTime() ) );


                            for (int m = 0; m < sessionsall.size(); m++) {
                                UTC = String.valueOf( sessionsall.get( m ).getTime() );
                                onlyDate = UTC.substring( 0, 10 );
                                utc = onlyDate.split( "-" ); // seperate.
                                //  now comparing days.
                                if (current[2].equals( utc[2] )) {
                                    checker++;
                                    presentingstatus = true;
                                    totalcaloriesoftoday += sessionsall.get( m ).getCaloriesEatenOnDay();
                                } else if (!current[2].equals( utc[2] )) {
                                    presentingstatus = false;
                                }


                            }


                            boolean finalvalue = presentingstatus;
                            Log.d( "ressssssszz", String.valueOf( totalcaloriesoftoday ) );
                            Log.d( "ressssssfvscc", String.valueOf( presentingstatus ) );
                            Log.d( "ressssssfvscc", String.valueOf( finalvalue ) );

                            date.setText( current[2]+"/"+current[1]+"/"+current[0] );
                            caloriesvalues.add( sessionsall.get( sessionsall.size() - 1 ).getAcutualCalories() );
                            caloriesvalues.add( totalcaloriesoftoday );
                            caloriesvalues.add( Math.abs( sessionsall.get( sessionsall.size() - 1 ).getCaloriesToBeEaten() ) );


                            if (!finalvalue) {
                                msg.setText( "No sesssion started today." );
                                share.setVisibility( View.INVISIBLE );
                                Log.d( "resssssssqqw", "1" );

                                msg.setVisibility( View.VISIBLE );
                                pieChart.setVisibility( View.INVISIBLE );
                                err.setVisibility( View.INVISIBLE );

                                ssession.setVisibility( View.VISIBLE );
                                ssession.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent( getActivity(), DietSession.class );
                                        intent.putExtra( "id", idofdietplan );
                                        startActivity( intent );


                                    }
                                } );


                            }
                            // if the session exists , then some checks.
                            else if (finalvalue) {
                                Log.d( "resssssssqqw", "2" );
                                Log.d( "resssssssqqwValue", String.valueOf( caloriesvalues.get( 0 ) ) );
                                Log.d( "resssssssqqwValue", String.valueOf( totalcaloriesoftoday ) );
                                Log.d( "resssssssqqwValue", String.valueOf( caloriesvalues.get( 0 ) - totalcaloriesoftoday ) );


                                if (totalcaloriesoftoday < 0) {
                                    caloriesdetails.add( "calories/day" );
                                    caloriesdetails.add( "calories to be eaten" );
                                    caloriesdetails.add( "calories burned" );


                                    pieChart.setVisibility( View.VISIBLE );
                                    msg.setVisibility( View.INVISIBLE );
                                    err.setVisibility( View.VISIBLE );
                                    err.setText( "Running low of calories" );

                                    caloriesvalues.remove( 1 );
                                    caloriesvalues.add( Math.abs( totalcaloriesoftoday ) );

                                    Log.d( "newnewnew", String.valueOf( caloriesvalues.size() ) );

                                    Log.d( "newvalues", String.valueOf( caloriesvalues.get( 0 ) ) );
                                    Log.d( "newvalues", String.valueOf( caloriesvalues.get( 1 ) ) );
                                    Log.d( "newvalues", String.valueOf( caloriesvalues.get( 2 ) ) );

                                    List<PieEntry> pieEntries = new ArrayList<>();

                                    for (int z = 0; z < caloriesdetails.size(); z++) {
                                        pieEntries.add( new PieEntry( caloriesvalues.get( z ), caloriesdetails.get( z ) ) );

                                        PieDataSet pieDataSet = new PieDataSet( pieEntries, "" );
                                        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );
                                        //object of the arrays combo and the name to be displayed.
                                        PieData pieData = new PieData( pieDataSet );


                                        // getting the chart.
                                        pieChart.setData( pieData );
                                        pieChart.animateY( 1000 );
                                        pieChart.animateX( 1000 );
                                        pieChart.setCenterText( "Calories Information" );
                                        pieChart.invalidate();
                                        pieChart.setDrawSliceText( false );
                                        pieChart.getDescription().setEnabled( false );

                                        Description description = new Description();
                                        description.setText( "" );
                                        pieChart.setDescription( description );

                                    }

                                }

                                // for positive cal remaining
                                else if (caloriesvalues.get( 0 ) - totalcaloriesoftoday > 0) {
                                    Log.d( "resssssssqqw", "3" );
                                    caloriesdetails.add( "calories/day" );
                                    caloriesdetails.add( "calories eaten" );
                                    caloriesdetails.add( "calories remaining" );


                                    pieChart.setVisibility( View.VISIBLE );
                                    msg.setVisibility( View.INVISIBLE );
                                    err.setVisibility( View.INVISIBLE );


                                    if (caloriesvalues.get( 0 ) - totalcaloriesoftoday < 500) {

                                        err.setVisibility( View.VISIBLE );
                                        err.setText( "Just " + caloriesvalues.get( 2 ).toString() + " calories remaining" );
                                    }


                                    Log.d( "www", String.valueOf( caloriesvalues.size() ) );
                                    Log.d( "www", String.valueOf( caloriesdetails.size() ) );

                                    List<PieEntry> pieEntries = new ArrayList<>();

                                    for (int z = 0; z < caloriesdetails.size(); z++) {
                                        pieEntries.add( new PieEntry( caloriesvalues.get( z ), caloriesdetails.get( z ) ) );

                                        PieDataSet pieDataSet = new PieDataSet( pieEntries, "" );
                                        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );
                                        //object of the arrays combo and the name to be displayed.
                                        PieData pieData = new PieData( pieDataSet );


                                        // getting the chart.
                                        pieChart.setData( pieData );
                                        pieChart.animateY( 1000 );
                                        pieChart.animateX( 1000 );
                                        pieChart.setCenterText( "Calories Information" );
                                        pieChart.invalidate();
                                        pieChart.setDrawSliceText( false );
                                        pieChart.getDescription().setEnabled( false );

                                        Description description = new Description();
                                        description.setText( "" );
                                        pieChart.setDescription( description );

                                    }


                                } // end of the inner if , greater condition.


                                else if (caloriesvalues.get( 0 ) - totalcaloriesoftoday < 0) {
                                    Log.d( "resssssssqqw", "4" );
                                    caloriesdetails.add( "calories/day" );
                                    caloriesdetails.add( "calories eaten" );
                                    caloriesdetails.add( "calories over taken" );
                                    Log.d( "ressssssssizes4", String.valueOf( caloriesvalues.size() ) );
                                    Log.d( "ressssssssizes4", String.valueOf( caloriesvalues.get( 2 ) ) );

                                    err.setVisibility( View.VISIBLE );
                                    err.setText( "You have taken " + caloriesvalues.get( 2 ).toString() + " calories extra" );


                                    pieChart.setVisibility( View.VISIBLE );
                                    msg.setVisibility( View.INVISIBLE );

                                    List<PieEntry> pieEntries = new ArrayList<>();

                                    for (int y = 0; y < caloriesvalues.size(); y++) {
                                        pieEntries.add( new PieEntry( caloriesvalues.get( y ), caloriesdetails.get( y ) ) );

                                        PieDataSet pieDataSet = new PieDataSet( pieEntries, "" );
                                        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );
                                        //object of the arrays combo and the name to be displayed.
                                        PieData pieData = new PieData( pieDataSet );


                                        // getting the chart.
                                        pieChart.setData( pieData );
                                        pieChart.animateY( 1000 );
                                        pieChart.animateX( 1000 );
                                        pieChart.setCenterText( "Calories Information" );
                                        pieChart.invalidate();
                                        pieChart.setDrawSliceText( false );
                                        pieChart.getDescription().setEnabled( false );

                                        Description description = new Description();
                                        description.setText( "" );
                                        pieChart.setDescription( description );

                                    }


                                } // end of the inner if , greater condition.


                            } // end of else if boolean
                        } // end of the try.
                        catch (Exception e) {
                            msg.setText( "No session started today." );
                            Log.d( "eeeee", e.getMessage() );
                            msg.setVisibility( View.VISIBLE );
                            pieChart.setVisibility( View.INVISIBLE );
                            ssession.setVisibility( View.VISIBLE );
                            ssession.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent( getActivity(), DietSession.class );
                                    intent.putExtra( "id", idofdietplan );
                                    Log.d( "bugtime", String.valueOf( DietPlanid ) );
                                    startActivity( intent );


                                }
                            } );


                        }

                    }

                    @Override
                    public void onFailure(Call<FetchingSessionResponse> call, Throwable t) {
                        Log.d( "resssssss", String.valueOf( t.getMessage() ) );


                    }

                } );


            }
        } );
        // hiding  the details of the deit session.

        hidedetails.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setVisibility( View.VISIBLE );
                notactive.setVisibility( View.INVISIBLE );
                stats.setVisibility( View.INVISIBLE );
                msg.setVisibility( View.INVISIBLE );
                ssession.setVisibility( View.INVISIBLE );
            }
        } );


        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share.setVisibility( View.INVISIBLE );
                hidedetails.setVisibility( View.INVISIBLE );
                openingPictureDialog();
            }
        } );


        return view;
    }

    public void setupPieChart() {

        Log.d( "chelsea", "ni aya" );
        Log.d( "chelsea", String.valueOf( caloriesvalues.size() ) );
        Log.d( "chelsea", String.valueOf( caloriesdetails.size() ) );
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int z = 0; z < caloriesvalues.size(); z++) {
            pieEntries.add( new PieEntry( caloriesvalues.get( z ), caloriesdetails.get( z ) ) );

            PieDataSet pieDataSet = new PieDataSet( pieEntries, "" );
            pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );
            //object of the arrays combo and the name to be displayed.
            PieData pieData = new PieData( pieDataSet );


            // getting the chart.
            pieChart.setData( pieData );
            pieChart.animateY( 1000 );
            pieChart.animateX( 1000 );
            pieChart.setCenterText( "Calories Information" );
            pieChart.invalidate();
            pieChart.setDrawSliceText( false );
            pieChart.getDescription().setEnabled( false );


        }

    }


    private void AskingUser() {

        final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( "Do you want to delete the Plan?" );
        builder.setCancelable( true );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d( "sdsdsds", dietplanid );
                Log.d( "sdsdsds", String.valueOf( idofdietplan ) );

                Call<DeletingPlan> calldelete = api.deletingplan( idofdietplan, new DeletingPlan( idofdietplan, "diet" ) );
                calldelete.enqueue( new Callback<DeletingPlan>() {
                    @Override
                    public void onResponse(Call<DeletingPlan> call, Response<DeletingPlan> response) {
                        Log.d( "alalaqqq", String.valueOf( response ) );
                        Intent intent = new Intent( getActivity(), MainActivity.class );
                        //intent.putExtra( "flow","noexercise;" );
                        startActivity( intent );

                    }

                    @Override
                    public void onFailure(Call<DeletingPlan> call, Throwable t) {

                    }
                } );

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


    //    @Override
//    public void onDetach() {
//        super.onDetach();
//
























//        Log.d( "MADAD", String.valueOf( active.getVisibility() ) );
//        Log.d( "MADAD", String.valueOf( stats.getVisibility() ) );
//
//        String activemode = "0" + active.getVisibility();
//        String statsmode = "0" + stats.getVisibility();
//
////        if ((active.getVisibility() == 4) && (stats.getVisibility() ==0) ){
////            Log.d( "MADAD",  "ab ayetga maza!");
////
////        }
//
//        if ((activemode.equals( "04" )) && (statsmode.equals( "00" ))) {
//
//            Log.d( "MADAD", "ab ayetga maza!" );
//
//
//            active.setVisibility( View.VISIBLE );
//            notactive.setVisibility( View.INVISIBLE );
//            stats.setVisibility( View.INVISIBLE );
//            msg.setVisibility( View.INVISIBLE );
//            ssession.setVisibility( View.INVISIBLE );
//
//
//        }
//    }
    private void openingPictureDialog() {
        dialog = new Dialog( getContext() );
        builder = new AlertDialog.Builder( getContext() );
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View customView = inflater.inflate( R.layout.picturestatus, null );
        // reference the textview of custom_popup_dialog
        ImageView imageView = (ImageView) customView.findViewById( R.id.imageviewpic );
        Button uploadButton = (Button) customView.findViewById( R.id.uploadphoto );
        Button shareButton = (Button) customView.findViewById( R.id.postthestatus );
        final EditText textshare = (EditText) customView.findViewById( R.id.textshare );

        imageView.setVisibility( View.VISIBLE );
        uploadButton.setVisibility( View.INVISIBLE );
        shareButton.setVisibility( View.VISIBLE );


        // uploading the photo in the dialog imagebox.
        final Bitmap b = ScreenShot.takeScreenshotOfRootView( stats );
        imageView.setImageBitmap( b );
Log.d( "ALALALLA", String.valueOf( b ) );

        shareButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            uploadingPhoto(b,textshare.getText().toString());
            }
        } );


        builder.setTitle( "Share What's in your mind?" );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    private void uploadingPhoto(Bitmap b,String text){
        try {

             path2 = "sdcard/Download/LifeLine"+ ".png";
            FileOutputStream out = new FileOutputStream( path2);
            b.compress( Bitmap.CompressFormat.PNG, 90, out );

            Log.d( "Olalal", "ff" );
        }
        catch (Exception e) {
            Log.d( "Olalal","ffqqqq" );
            Log.d( "Olalal",e.getMessage());


            e.printStackTrace();

        }

        File file = new File( path2 );

        RequestBody mFile = RequestBody.create( MediaType.parse( "image/*" ), file );
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData( "picture", file.getName(), mFile );
        RequestBody filename = RequestBody.create( MediaType.parse( "text/plain" ), file.getName() );
        uploadingtoserver(fileToUpload,filename,text);

    }
    private void uploadingtoserver(MultipartBody.Part fileToUpload , RequestBody filename, final String text){
        Call<UploadObject> fileUpload = uploadImage.uploadFile( fileToUpload, filename );

        fileUpload.enqueue( new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {

                Log.d( "ALALALAAL", String.valueOf( response.body().getData() ) );
                Log.d( "ALALALAAL", String.valueOf( text ) );
                Log.d( "ALALALAAL", String.valueOf( id ) );
                //   Glide.with( UserProfile.this ).load( response.body().getData()).into( userdp );


                uploadingStatus(id ,response.body().getData(),text );
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                Log.d( "dfdf", "ErrorALALALALALALALLA " + t.getMessage() );
            }
        } );
    }

    private void uploadingStatus(final Integer memberId, final String imagelink, final String text){
        Runnable r = new Runnable() {
            @Override
            public void run() {

                PostingStatus postingStatus = new PostingStatus( memberId, text, imagelink, "true" );

                Call<StatusResponse> call = api.status( postingStatus );
                call.enqueue( new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d( "Responsesesse", String.valueOf( response ) );
                        Log.d( "Responsesesse", String.valueOf( response.body().getData().getPictureLink() ) );
                        dialog.dismiss();
                        startActivity( new Intent( getActivity(),MainActivity.class  ));
                        Toast.makeText( getContext(), "Status Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.d( "WEROEOEOEO", t.getMessage() );
                    }
                } );

            }
        };

        Thread runnable = new Thread( r );
        runnable.start();

    }
}
