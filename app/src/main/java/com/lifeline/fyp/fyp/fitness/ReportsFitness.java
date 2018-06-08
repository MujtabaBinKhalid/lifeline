package com.lifeline.fyp.fyp.fitness;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.diet.FoodEaten;
import com.lifeline.fyp.fyp.diet.ReportAdapter;
import com.lifeline.fyp.fyp.diet.ReportModel;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.models.FitnessSessionSummary;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.CalorieSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessAllSessions;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportsFitness extends AppCompatActivity {


    RadioGroup rg, radioGroup2;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8;
    TextView textView, totalcal, datetext;
    ArrayList<FitnessSessionSummary> lastmonthlastresult;
    Calendar calender;
    float previousfullweekcalories;
    ListView listview;
    Set<String> hs;
    ArrayAdapter<String> adapter;
    ArrayList<String> uniquevalue;
    private ReportModel listAdapter; // adapter object .
    List<String> aerobiclist;
    private float previousdaycalories;
    SimpleDateFormat df;
    String aerobicexercises;
    private String formattedDate;
    private String[] current;
    private String lastday;
    String UTC;
    ExpandableListView rvv;

    String onlyDate, text; // only date.
    Integer lastweek;
    String category, selected;
    Retrofit retrofit;
    Api api;
    ProgressDialog progress;
    TextView weraero;
    private RecyclerView frv; // breakfast recycler view.
    private ReportAdapterFitness ffa; //breakfast adapter;
    RadioGroup aerogroup1, aerogroup2;
    Integer idpass;
    String[] sep;
    double totaldistance, totalspeed, totaltime;
    RelativeLayout rl1, rl2, rle, rl3, rl4;
    TextView error, date, distance, speed, time, cal, caltext1, datetext1;
    double gettingtime;

    // rle -- > error , rl1 -- > daily , rl2 --> weekly,monthly.
    // rl3 --> aerobic daily
    String[] utc;
RadioGroup reports3;

    // rl2 ---> Weekly
    // rl1 ----> Daily.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reports_fitness );

        error = (TextView) findViewById( R.id.error );
        reports3 = (RadioGroup) findViewById( R.id.reports3 );

        progress = new ProgressDialog( this );
        progress.setMessage( "Generating Report" );
        progress.show();
        date = (TextView) findViewById( R.id.datetext );
        distance = (TextView) findViewById( R.id.distancevalue );
        time = (TextView) findViewById( R.id.timevalue );
        speed = (TextView) findViewById( R.id.speedvalue );
        cal = (TextView) findViewById( R.id.calorievalue );
        totalcal = (TextView) findViewById( R.id.caltextaero );
        datetext = (TextView) findViewById( R.id.datetextaero );
        caltext1 = (TextView) findViewById( R.id.caltext1 );
        datetext1 = (TextView) findViewById( R.id.datetext1 );

        aerogroup1 = (RadioGroup) findViewById( R.id.reports );
        aerogroup2 = (RadioGroup) findViewById( R.id.reports2 );
        rle = (RelativeLayout) findViewById( R.id.layoutholdingerr );
        rl1 = (RelativeLayout) findViewById( R.id.layoutreportsd );
        rl2 = (RelativeLayout) findViewById( R.id.layoutreportsw );
        rl3 = (RelativeLayout) findViewById( R.id.layoutdailyaerobic );
        rl4 = (RelativeLayout) findViewById( R.id.layoutreports2aero );
        rvv = (ExpandableListView) findViewById( R.id.rvvv );
        frv = (RecyclerView) findViewById( R.id.weeklydata );
        LinearLayoutManager bfm = new LinearLayoutManager( ReportsFitness.this, LinearLayoutManager.VERTICAL, false );
        frv.setLayoutManager( bfm );
        weraero = (TextView) findViewById( R.id.weraero );

        // image button life style.
        ImageButton lifestyle1 = (ImageButton) findViewById( R.id.lifestyle1 );
        lifestyle1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( ReportsFitness.this, LifeStyle.class );
                startActivity( intent );

            }
        } );


        ImageButton profile1 = (ImageButton) findViewById( R.id.profile1 );
        profile1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( ReportsFitness.this, UserProfile.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.


            }
        } );


        // fitnese btn of the nav bar.
        ImageButton fitness1 = (ImageButton) findViewById( R.id.fitness1 );
        fitness1.setEnabled( false );
        fitness1.setClickable( false );

        ImageButton home1 = (ImageButton) findViewById( R.id.home1 );
        home1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( ReportsFitness.this, MainActivity.class );
                startActivity( intent );

            }
        } );

        // diet button.

        ImageButton diet1 = (ImageButton) findViewById( R.id.nutrition1 );
        diet1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( ReportsFitness.this, DietMain.class );
                startActivity( intent );
            }
        } );


        text = "Daily";
        // retorofit
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        // calender

        calender = Calendar.getInstance(); //  current instance.
        df = new SimpleDateFormat( "dd-MM-yyyy" ); // ormat
        formattedDate = df.format( calender.getTime() ); // getting time.
        current = formattedDate.split( "-" ); // seperqtor.

        // radio group
        rg = (RadioGroup) findViewById( R.id.reportsGp );
        radioGroup2 = (RadioGroup) findViewById( R.id.reports1 );
        // layout
        rl1 = (RelativeLayout) findViewById( R.id.layoutreportsd );
        rl2 = (RelativeLayout) findViewById( R.id.layoutreportsw );
        textView = (TextView) findViewById( R.id.reporttitle );
        listview = (ListView) findViewById( R.id.lastday );

        // radio buttons.
        rb1 = (RadioButton) findViewById( R.id.W );
        rb2 = (RadioButton) findViewById( R.id.M );
        rb3 = (RadioButton) findViewById( R.id.D );
        rb4 = (RadioButton) findViewById( R.id.M1 );
        rb5 = (RadioButton) findViewById( R.id.Waero );
        rb6 = (RadioButton) findViewById( R.id.Maero );
        rb7 = (RadioButton) findViewById( R.id.Daero2 );
        rb8 = (RadioButton) findViewById( R.id.M2 );


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString( "btnpreseed" );
        }

        Log.d( "tagcategory", category );

        sep = category.split( ";" );
        idpass = Integer.parseInt( sep[0] );
        selected = sep[1];
        ServerConnection( idpass, selected, text );

        // setting recylcer view.


        currentDaily();
        Weekly();
        DecisionMakingDailyLayout();
        DecisionakingWeeklyLayout();
    }

    private void ServerConnection(Integer idpass, final String cate, final String type) {
        Log.d( "oooopp", String.valueOf( idpass ) );
        Log.d( "ooopp", String.valueOf( cate ) );
        Log.d( "ooopp", String.valueOf( type ) );
        FitnessSessionSummary ff = new FitnessSessionSummary( idpass );
        Call<FitnessAllSessions> summary = api.sessionresponse( idpass, ff );
        summary.enqueue( new Callback<FitnessAllSessions>() {

            @Override
            public void onResponse(Call<FitnessAllSessions> call, Response<FitnessAllSessions> response) {
                Log.d( "EWWWWWQW", String.valueOf( response ) );

                if (response.body().getDataCount() == 0) {
                    error.setText( "No history is available right now. " );
                    rle.setVisibility( View.VISIBLE );
                    rl1.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.INVISIBLE );
                } else {

                    List<FitnessSessionSummary> sessionsall = response.body().getData();

                    if (cate.equals( "exercise" ) && type.equals( "Daily" )) {
                        Log.d( "dfdfd", "uider goon" );
                        AerobicpreviousDayReport( (ArrayList<FitnessSessionSummary>) sessionsall );
                    } else if (cate.equals( "exercise" ) && type.equals( "Weekly" )) {
                        Log.d( "dfdfd", "uider goon" );
                        AerobicpreviousWeekReport( (ArrayList<FitnessSessionSummary>) sessionsall );
                    } else if (cate.equals( "exercise" ) && type.equals( "Monthly" )) {
                        Log.d( "dfdfd", "uider goon" );
                        AerobicpreviousMonthReport( (ArrayList<FitnessSessionSummary>) sessionsall );
                    } else if (type.equals( "Daily" )) {
                        PreviousDayReport( (ArrayList<FitnessSessionSummary>) sessionsall, cate );

                    } else if (type.equals( "Weekly" )) {
                        PreviousWeek( (ArrayList<FitnessSessionSummary>) sessionsall, cate );

                    } else if (type.equals( "Monthly" )) {
                        PreviousMonth( (ArrayList<FitnessSessionSummary>) sessionsall, cate );
                    }

                }
            }

            @Override
            public void onFailure(Call<FitnessAllSessions> call, Throwable t) {

            }
        } );
    }

    // for running and cycling.
    private void PreviousDayReport(ArrayList<FitnessSessionSummary> sessionsall, String category) {


        Log.d( "dff", category );
        lastday = Integer.parseInt( current[0] ) - 1 + ""; // going to preious month.
        //lastday =  "13"; // going to preious month.
        int checker = 0; // checking if the result exists or not.
        Log.d( "lastday", lastday );


        lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.

        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //  Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int countinglast = 0;
        totaldistance = 0;
        totaltime = 0;
        totalspeed = 0;
        gettingtime = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );

        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.
            //  now comparing days.
            // first comparing months.
            StringBuilder sb = new StringBuilder( utc[1] );
            sb.deleteCharAt( 0 );
            Log.d( "sbValue", sb.toString() );

            if (current[1].equals( utc[1] )) {

                if (lastday.equals( utc[2] )) {

                    if (category.equals( sessionsall.get( m ).getSessionType() )) {
                        checker++;

                        Log.d( "zzaa", String.valueOf( sessionsall.get( m ).getDistanceCovered() ) );
                        totaldistance = sessionsall.get( m ).getDistanceCovered() + totaldistance;
                        gettingtime = sessionsall.get( m ).getDistanceCovered() / sessionsall.get( m ).getSpeed();
                        Log.d( "dfdfzzz", String.valueOf( gettingtime ) );
                        totaltime = totaltime + gettingtime;
                        totalspeed = sessionsall.get( m ).getSpeed();


                    } // end of condition

                } // deail with days.

            } // comparing months .


            // last month , last day result.
            if (lastmonth == Integer.parseInt( sb.toString() )) {
                lastmonthlastresult.add( sessionsall.get( m ) );

                checker++;

            }


        } // end of for loop


        // if no result exists that means no activity were performed yestereday.
        if (checker == 0) {

            error.setText( "No session were performed yesterday. " );
            rle.setVisibility( View.VISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );
            progress.hide();

        } else {

            // last month , last result.
            if (lastmonthlastresult.size() > 0) {
                Log.d( "lLlLl", String.valueOf( lastmonthlastresult.size() ) );


                UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getStartTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.

                String lastmothdate = utc[2];

                for (int p = 0; p < lastmonthlastresult.size(); p++) {
                    UTC = String.valueOf( sessionsall.get( p ).getStartTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.


                    if (lastmothdate.equals( utc[2] )) {


                        if (category.equals( sessionsall.get( p ).getSessionType() )) {
                            checker++;

                            Log.d( "zzaa", String.valueOf( sessionsall.get( p ).getDistanceCovered() ) );
                            totaldistance = sessionsall.get( p ).getDistanceCovered() + totaldistance;
                            gettingtime = sessionsall.get( p ).getDistanceCovered() / sessionsall.get( p ).getSpeed();
                            Log.d( "dfdfzzz", String.valueOf( gettingtime ) );
                            totaltime = totaltime + gettingtime;
                            totalspeed = sessionsall.get( p ).getSpeed();


                        } // end of condition
                    } // deail with days.


                } // for loop ends


            }  // if the new months starts and the user wants to see the last report.


            String strdistance = totaldistance + "";
            String timestr = totaltime + "";
            String speedstr = totalspeed + "";
            String calstr = totaldistance*2/3 + "";

            distance.setText( strdistance.subSequence( 0,3 ) + " meters" );
            time.setText( timestr.substring( 0,3 ) + " mints" );
            speed.setText( speedstr.substring( 0,3 ) + " m/sec" );
            cal.setText( calstr.substring( 0,4 ) + ""+ " cal" );
            date.setText( current[0] + "/" + current[1] + "/" + current[2] );


            progress.hide();
            rle.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.VISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );

            // other stuff related to arrays and adapter is missing.

        } // end of else.

    }

    // end of method.
    // for running and ccycling
    private void PreviousWeek(ArrayList<FitnessSessionSummary> sessionsall, String category) {

        lastweek = Integer.parseInt( current[0] ); // aj ka din.
        int checker = 0;
        Integer previous = 0;
        Date date;
        String counting;
        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int countinglast = 0;
        totaldistance = 0;
        totaltime = 0;
        totalspeed = 0;
        gettingtime = 0;
        ArrayList<FitnessObject> foarray = new ArrayList<>();


        // outer loop is used for ilterating for over the week. it actually seperates the day fromm
        // the current date and then do it seven times . and then compare the result with the database result.
        for (int o = 1; o < 8; o++) {
            counting = "";
            previous = lastweek - o;
            Log.d( "qooe", String.valueOf( lastweek ) );
            Log.d( "qooe", String.valueOf( previous ) );

            date = new Date();
            String today = previous + "-" + current[1] + "-" + current[2];

            try {
                date = df.parse( today );

            } catch (ParseException e) {

            }
            String datewithDay = date.toString().substring( 0, 3 ) + ",  " + date.toString().substring( 4, 10 ) + " " + date.toString().substring( 30, 34 );
            String day = date.toString().substring( 0, 10 ) + " " + date.toString().substring( 30, 34 );

            Log.d( "rrDate", date.toString().substring( 0, 3 ) );
            Log.d( "rr", date.toString().substring( 4, 10 ) );
            Log.d( "rr", date.toString().substring( 30, 34 ) );

            Log.d( "preValueeeeee", String.valueOf( previous ) );

            // yahan fitness ki chezein intiailzize krwanui hai.

            //
            for (int m = 0; m < sessionsall.size(); m++) {
                UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.
                //  now comparing days.
                // first comparing months.

                Log.d( "ooooooooooooooooooD", onlyDate );


                if (current[1].equals( utc[1] )) {

                    // previous is used to look a week before.
                    if (previous.toString().equals( utc[2] )) {
                        if (category.equals( sessionsall.get( m ).getSessionType() )) {
                            checker++;
                            Log.d( "zzaa", String.valueOf( sessionsall.get( m ).getDistanceCovered() ) );
                            totaldistance = sessionsall.get( m ).getDistanceCovered() + totaldistance;
                            gettingtime = sessionsall.get( m ).getDistanceCovered() / sessionsall.get( m ).getSpeed();
                            Log.d( "dfdfzzz", String.valueOf( gettingtime ) );
                            totaltime = totaltime + gettingtime;
                            totalspeed = sessionsall.get( m ).getSpeed();


                        } // end of condition


                    }
                }


            } // for loop. // INNER LOOP

            if (checker == 0) {
                error.setText( "No session were performed yesterday. " );
                rle.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );

            } else {
                String strdistancemeasuring = totaldistance + "";
                Log.d( "ccccccdddd", strdistancemeasuring );
                Log.d( "countinggggg", String.valueOf( counting ) );


                if (!strdistancemeasuring.equals( "0.0" )) {
                    countinglast++;
                    //String distance, String time, String speed, String cal, String date
                    FitnessObject fo =
                            new FitnessObject( totaldistance + "", totaltime + "",
                                    totalspeed + "", "gfg", date.toString() );

                    foarray.add( fo );
                }

            }

            totaldistance = 0;
            gettingtime = 0;
            totalspeed = 0;
            totaltime = 0;
        }// OUTER FOR LLOOOPP..

        Log.d( "dfdfdww", String.valueOf( foarray.size() ) );
        if (checker == 0) {
            error.setText( "No session were performed yesterday. " );
            rle.setVisibility( View.VISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );

        } else {
            rle.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.VISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );

            ffa = new ReportAdapterFitness( foarray );
            frv.setAdapter( ffa );
            Log.d( "fdfdf", "Wee" );
            Log.d( "fdfdf", String.valueOf( foarray.size() ) );
            Log.d( "fdfdf", String.valueOf( ffa.getItemCount() ) );

        }

    }

    // for running and cycling
    private void PreviousMonth(ArrayList<FitnessSessionSummary> sessionsall, String category) {

        ArrayList<FitnessObject> foarray = new ArrayList<>();

        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int checker = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );
        lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.


        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.
            StringBuilder sb = new StringBuilder( utc[1] );
            sb.deleteCharAt( 0 );
            Log.d( "vvcc", sb.toString() );
            Log.d( "vvbb", lastmonth.toString() );


            if (lastmonth == Integer.parseInt( sb.toString() )) {
                lastmonthlastresult.add( sessionsall.get( m ) );
                checker++;
            }
        }


        if (checker == 0) {
            error.setText( "No last month history available. " );
            rle.setVisibility( View.VISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );
        } else {

            UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.


            for (int q = 0; q < Integer.parseInt( utc[2] ); q++) {


                Integer lastmothdate = Integer.parseInt( utc[2] ) - q;
                Date date = new Date();
                String today = lastmothdate + "-" + current[1] + "-" + current[2];

                try {
                    date = df.parse( today );

                } catch (ParseException e) {

                }
                String datewithDay = date.toString().substring( 0, 3 ) + ",  " + date.toString().substring( 4, 10 ) + " " + date.toString().substring( 30, 34 );
                String day = date.toString().substring( 0, 10 ) + " " + date.toString().substring( 30, 34 );

                for (int m = 0; m < sessionsall.size(); m++) {
                    UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.
                    //  now comparing days.
                    // first comparing months.

                    Log.d( "ooooooooooooooooooD", onlyDate );

                    // previous is used to look a week before.
                    if (lastmothdate.toString().equals( utc[2] )) {
                        if (category.equals( sessionsall.get( m ).getSessionType() )) {
                            checker++;
                            Log.d( "zzaa", String.valueOf( sessionsall.get( m ).getDistanceCovered() ) );
                            totaldistance = sessionsall.get( m ).getDistanceCovered() + totaldistance;
                            gettingtime = sessionsall.get( m ).getDistanceCovered() / sessionsall.get( m ).getSpeed();
                            Log.d( "dfdfzzz", String.valueOf( gettingtime ) );
                            totaltime = totaltime + gettingtime;
                            totalspeed = sessionsall.get( m ).getSpeed();


                        } // end of condition


                    }


                } // for loop. // INNER LOOP


                String strdistancemeasuring = totaldistance + "";
                Log.d( "ccccccdddd", strdistancemeasuring );


                if (!strdistancemeasuring.equals( "0.0" )) {
                    //String distance, String time, String speed, String cal, String date
                    FitnessObject fo =
                            new FitnessObject( totaldistance + "", totaltime + "",
                                    totalspeed + "", "gfg", date.toString() );

                    foarray.add( fo );
                }

                totaldistance = 0;
                gettingtime = 0;
                totalspeed = 0;
                totaltime = 0;

            } // outer loop

            if (checker == 0) {
                error.setText( "No session were performed yesterday. " );
                rle.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );

            } else {
                rle.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.VISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );

                ffa = new ReportAdapterFitness( foarray );
                frv.setAdapter( ffa );
                Log.d( "fdfdf", "Wee" );
                Log.d( "fdfdf", String.valueOf( foarray.size() ) );
                Log.d( "fdfdf", String.valueOf( ffa.getItemCount() ) );

            }


        }
    }

// for aerobic exercise.

    private void AerobicpreviousDayReport(ArrayList<FitnessSessionSummary> sessionsall) {

        // 1st of the new nonth then?
        lastday = Integer.parseInt( current[0] ) - 1 + ""; // going to preious month.

        int checker = 0; // checking if the result exists or not.
        Log.d( "lastday", lastday );
        lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.

        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int countinglast = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );

        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.
            //  now comparing days.
            // first comparing months.
            StringBuilder sb = new StringBuilder( utc[1] );
            sb.deleteCharAt( 0 );
            Log.d( "vvcc", sb.toString() );
            Log.d( "vvbb", lastmonth.toString() );


            if (current[1].equals( utc[1] )) {
// if (lastday.equals( utc[2] ))
                if (lastday.equals( utc[2] )) {
                    previousdaycalories += sessionsall.get( m ).getCaloriesBurnt();

                    aerobicexercises = sessionsall.get( m ).getDescription() + ";" + aerobicexercises;
                    Log.d( "AEERER", aerobicexercises );
                    checker++;
                } // deail with days.

            } // comparing months .


            if (lastmonth == Integer.parseInt( sb.toString() )) {
                lastmonthlastresult.add( sessionsall.get( m ) );

                checker++;

            }


        } // for loop.


        Log.d( "utc22", String.valueOf( checker ) );
        //   Log.d( "utc22", String.valueOf( lastmonthlastresult.size() ) );


        if (checker == 0) {

            error.setText( "No session were performed yesterday. " );
            rle.setVisibility( View.VISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );
        } else {
            rle.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.VISIBLE );
            rl4.setVisibility( View.INVISIBLE );

            if (lastmonthlastresult.size() > 0) {
                Log.d( "lLlLl", String.valueOf( lastmonthlastresult.size() ) );


                UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getStartTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.

                String lastmothdate = utc[2];

                for (int p = 0; p < lastmonthlastresult.size(); p++) {
                    UTC = String.valueOf( sessionsall.get( p ).getStartTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.


                    if (lastmothdate.equals( utc[2] )) {
                        previousdaycalories += sessionsall.get( p ).getCaloriesBurnt();

                        aerobicexercises = sessionsall.get( p ).getDescription() + ";" + aerobicexercises;
                    } // deail with days.


                } // for loop ends


                Log.d( "newcomer", utc[2] );
            }  // if the new months starts and the user wants to see the last report.


            // same month,last day.


            aerobiclist = new ArrayList<>( Arrays.asList( aerobicexercises.split( ";" ) ) );

            aerobiclist.remove( aerobiclist.size() - 1 ); // removing the last index as it contain NULL.

            Log.d( "lastdayc", String.valueOf( aerobiclist.size() ) );
            String today = lastday + "-" + current[1] + "-" + current[2];


            aerobiclist = refinedFoodItems( aerobiclist );  // the method used for containing the element occurrences.
            uniquekeypair( aerobiclist ); // it make key value pair.
            Collections.sort( aerobiclist ); // it arrange the elements.

            totalcal.setText( previousdaycalories + "" );
            datetext.setText( today );

            adapter = new ArrayAdapter<String>( ReportsFitness.this, R.layout.fooditemname, R.id.itemname, uniquevalue );
            listview.setAdapter( adapter );


        }


    }

    private void AerobicpreviousWeekReport(ArrayList<FitnessSessionSummary> sessionsall) {


        lastweek = Integer.parseInt( current[0] ); // aj ka din.
        int checker = 0;
        Integer previous = 0;
        ArrayList<ReportModel> perday = new ArrayList<>();
        ArrayList<FoodEaten> fd;
        Date date;
        previousfullweekcalories = 0;
        String counting;
        lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.
        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int countinglast = 0;


        for (int o = 1; o < 8; o++) {
            counting = "";
            previous = lastweek - o;
            date = new Date();
            String today = previous + "-" + current[1] + "-" + current[2];

            try {
                date = df.parse( today );

            } catch (ParseException e) {

            }
            String datewithDay = date.toString().substring( 0, 3 ) + ",  " + date.toString().substring( 4, 10 ) + " " + date.toString().substring( 30, 34 );
            String day = date.toString().substring( 0, 10 ) + " " + date.toString().substring( 30, 34 );

            Log.d( "rr", date.toString().substring( 0, 3 ) );
            Log.d( "rr", date.toString().substring( 4, 10 ) );
            Log.d( "rr", date.toString().substring( 30, 34 ) );

            Log.d( "preValueeeeee", String.valueOf( previous ) );

            previousdaycalories = 0;
            aerobicexercises = "";

            fd = new ArrayList<FoodEaten>();

            for (int m = 0; m < sessionsall.size(); m++) {
                UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.
                //  now comparing days.
                // first comparing months.

                Log.d( "ooooooooooooooooooD", onlyDate );


                if (current[1].equals( utc[1] )) {

                    if (previous.toString().equals( utc[2] )) {

                        checker++;
                        Log.d( "chances", String.valueOf( previous ) );
                        previousdaycalories += sessionsall.get( m ).getCaloriesBurnt();
                        previousfullweekcalories += sessionsall.get( m ).getCaloriesBurnt();
                        aerobicexercises = sessionsall.get( m ).getDescription() + ";" + aerobicexercises;


                    }
                }


            } // for loop.


            if (checker == 0) {
                error.setText( "No session were performed yesterday. " );
                rle.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );
            } else {
                counting = countOccurrences( aerobicexercises ) + "";
                Log.d( "countinggggg", String.valueOf( counting ) );


                if (!counting.equals( "0" )) {
                    rle.setVisibility( View.VISIBLE );
                    rl1.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.INVISIBLE );
                    rl3.setVisibility( View.INVISIBLE );
                    rl4.setVisibility( View.VISIBLE );
                    // spliting the food items of daily basics in to an arraylist.
                    aerobiclist = new ArrayList<>( Arrays.asList( aerobicexercises.split( ";" ) ) );

                    // foodList.remove( foodList.size() - 1 );

//                for (int i=0; i< foodList.size();i++){
//                    FoodEaten ffee= new FoodEaten(foodList.get( i ));
//                    fd.add( ffee );
//                }

                    aerobiclist = new ArrayList<>( Arrays.asList( aerobicexercises.split( ";" ) ) );

                    // foodList.remove( foodList.size() - 1 ); // removing the last index as it contain NULL.


                    aerobiclist = refinedFoodItems( aerobiclist );  // the method used for containing the element occurrences.


                    uniquekeypair( aerobiclist ); // it make key value pair.


                    for (int w = 0; w < aerobiclist.size(); w++) {
                        FoodEaten ffee = new FoodEaten( aerobiclist.get( w ) );
                        fd.add( ffee );
                    }

                    Collections.sort( aerobiclist ); // it arrange the elements


                    perday.add( new ReportModel( previousdaycalories + "", datewithDay, day, fd ) );
                    Log.d( "callllperady", String.valueOf( perday.size() ) );

                }

                // idher,

            } // outer loop.
            Log.d( "sdsds", String.valueOf( perday.size() ) );

            String[] sepdate = perday.get( perday.size() - 1 ).getDate().split( " " ); // starting
            String[] sepdate1 = perday.get( 0 ).getDate().split( " " ); // endng.

            datetext1.setText( sepdate[2] + "/" + sepdate[1] + " to " + sepdate1[2] + "/" + sepdate1[1] + "/" + sepdate[3] );

            Log.d( "wssw", perday.get( perday.size() - 1 ).getDate() );
            Log.d( "wssw", perday.get( perday.size() - perday.size() ).getDate() );
            ReportAdapter ra = new ReportAdapter( ReportsFitness.this, perday );
            rvv.setGroupIndicator( null );
            caltext1.setText( previousfullweekcalories + "" );

            rvv.setAdapter( ra );

            Log.d( "baba", utc[2] );
            Log.d( "baba", String.valueOf( sessionsall.size() ) );

        }


    }

    private void AerobicpreviousMonthReport(ArrayList<FitnessSessionSummary> sessionsall) {
        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int checker = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );
        lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.


        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.
            StringBuilder sb = new StringBuilder( utc[1] );
            sb.deleteCharAt( 0 );
            Log.d( "vvcc", sb.toString() );
            Log.d( "vvbb", lastmonth.toString() );


            if (lastmonth == Integer.parseInt( sb.toString() )) {
                lastmonthlastresult.add( sessionsall.get( m ) );
                checker++;
            }
        }


        if (checker == 0) {

            error.setVisibility( View.VISIBLE );
            rl2.setVisibility( View.VISIBLE );
            error.setText( "NO , report available." );
        } else {
            Log.d( "lLlLl", String.valueOf( lastmonthlastresult.size() ) );

            //////////////////////
            Integer previous = 0;
            ArrayList<ReportModel> perday = new ArrayList<>();
            ArrayList<FoodEaten> fd;
            Date date;
            previousfullweekcalories = 0;
            String counting;
            lastmonthlastresult = new ArrayList<FitnessSessionSummary>(); // for storing all the result of the previous month.


            ////////////////////////
            UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getStartTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.


            Log.d( "newcomer", String.valueOf( utc[2] ) );
            // starting from zero and and then moving backward.
            for (int q = 0; q < Integer.parseInt( utc[2] ); q++) {


                Integer lastmothdate = Integer.parseInt( utc[2] ) - q;
                date = new Date();
                String today = lastmothdate + "-" + current[1] + "-" + current[2];

                try {
                    date = df.parse( today );

                } catch (ParseException e) {

                }
                String datewithDay = date.toString().substring( 0, 3 ) + ",  " + date.toString().substring( 4, 10 ) + " " + date.toString().substring( 30, 34 );
                String day = date.toString().substring( 0, 10 ) + " " + date.toString().substring( 30, 34 );


                previousdaycalories = 0;
                aerobicexercises = "";

                fd = new ArrayList<FoodEaten>();

                Log.d( "newcomer11", String.valueOf( lastmothdate ) );
                for (int p = 0; p < lastmonthlastresult.size(); p++) {

                    UTC = String.valueOf( sessionsall.get( p ).getStartTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.
                    Log.d( "newcomer22", utc[2] );

//
                    if (lastmothdate.toString().equals( utc[2] )) {
                        Log.d( "idherhoon", utc[2] );
                        checker++;
                        Log.d( "chances", String.valueOf( previous ) );
                        previousdaycalories += sessionsall.get( p ).getCaloriesBurnt();
                        previousfullweekcalories += sessionsall.get( p ).getCaloriesBurnt();
                        aerobicexercises = sessionsall.get( p ).getDescription() + ";" + aerobicexercises;

                    } // deail with days.


                } // loop ended.

                if (checker == 0) {

                    error.setVisibility( View.VISIBLE );
                    rl2.setVisibility( View.VISIBLE );
                    error.setText( "NO , last week history available ." );
                } else {
                    counting = countOccurrences( aerobicexercises ) + "";
                    Log.d( "countinggggg", String.valueOf( counting ) );


                    if (!counting.equals( "0" )) {
                        // spliting the food items of daily basics in to an arraylist.
                        aerobiclist = new ArrayList<>( Arrays.asList( aerobicexercises.split( ";" ) ) );

                        // foodList.remove( foodList.size() - 1 );

//                for (int i=0; i< foodList.size();i++){
//                    FoodEaten ffee= new FoodEaten(foodList.get( i ));
//                    fd.add( ffee );
//                }

                        aerobiclist = new ArrayList<>( Arrays.asList( aerobicexercises.split( ";" ) ) );

                        // foodList.remove( foodList.size() - 1 ); // removing the last index as it contain NULL.


                        aerobiclist = refinedFoodItems( aerobiclist );  // the method used for containing the element occurrences.


                        uniquekeypair( aerobiclist ); // it make key value pair.


                        for (int w = 0; w < aerobiclist.size(); w++) {
                            FoodEaten ffee = new FoodEaten( aerobiclist.get( w ) );
                            fd.add( ffee );
                        }

                        Collections.sort( aerobiclist ); // it arrange the elements


//                Log.d( "callll-00", String.valueOf( foodList.size() ) );
//                foodList = refinedFoodItems( foodList );
//
//                                for (int i=0; i< foodList.size();i++){
//                    FoodEaten ffee= new FoodEaten(foodList.get( i ));
//                    fd.add( ffee );
//                }
//                Log.d( "callll-33B", String.valueOf( foodList.size() + " mjy yakeen ni a rha" ) );
//
//                foodList = refinedFoodItems( foodList );
//
//               // pairing( fd );
//
//                Collections.sort( foodList );

                        // perday.add( foodList );

                        perday.add( new ReportModel( previousdaycalories + "", datewithDay, day, fd ) );
                        Log.d( "callllperady", String.valueOf( perday.size() ) );

                    }

                    // idher,

                } // outer loop.
                Log.d( "sdsds", String.valueOf( perday.size() ) );

                String[] sepdate = perday.get( perday.size() - 1 ).getDate().split( " " ); // starting
                String[] sepdate1 = perday.get( 0 ).getDate().split( " " ); // endng.

                datetext1.setText( sepdate[2] + "/" + sepdate[1] + " to " + sepdate1[2] + "/" + sepdate1[1] + "/" + sepdate[3] );

                Log.d( "wssw", perday.get( perday.size() - 1 ).getDate() );
                Log.d( "wssw", perday.get( perday.size() - perday.size() ).getDate() );
                ReportAdapter ra = new ReportAdapter( ReportsFitness.this, perday );
                rvv.setGroupIndicator( null );
                caltext1.setText( previousfullweekcalories + "" );

                rvv.setAdapter( ra );

                Log.d( "baba", utc[2] );
                Log.d( "baba", String.valueOf( sessionsall.size() ) );

            }

//
        }

        //   Log.d( "newcomer", utc[2] );
        // Log.d( "newcomer", String.valueOf( previousdaycalories ) );

    }


    // RadioGroups of different layouts.
    private void currentDaily() {

        rb1.setChecked( false );
        rb2.setChecked( false );
        rg.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                Log.d( "Dfdf", "Manu" );
                switch (checkedId) {

                    case R.id.W:
                        Log.d( "meIdharHoon", "WE" );
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.VISIBLE );
                        rle.setVisibility( View.INVISIBLE );
                        text = "Weekly";
                        ServerConnection( idpass, selected, text );
                        break;


                    case R.id.M:
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.VISIBLE );
                        textView.setText( "Monthly Report" );
                        text = "Monthly";
                        ServerConnection( idpass, selected, text );

                        break;

                }
            }
        } );


    }

    private void Weekly() {


        rb3.setChecked( false );
        rb4.setChecked( false );
        radioGroup2.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {

                    case R.id.D:
                        Log.d( "meIdharHoon", "WE" );
                        rl1.setVisibility( View.VISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        text = "Daily";
                        ServerConnection( idpass, selected, text );


                        break;


                    case R.id.M1:
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.VISIBLE );
                        textView.setText( "Monthly Report" );
                        rb4.setText( "Weekly" );
                        text = "Monthly";
                        ServerConnection( idpass, selected, text );


                        break;

                }
            }
        } );


    }

    private void DecisionMakingDailyLayout() {

        rb5.setChecked( false );
        rb6.setChecked( false );
        aerogroup1.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.Waero:
                        Log.d( "meIdharHoon", "WE" );
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
                        rl4.setVisibility( View.VISIBLE );
                        rle.setVisibility( View.INVISIBLE );
                        text = "Weekly";
                        ServerConnection( idpass, selected, text );

                        break;


                    case R.id.Maero:
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
                        rl4.setVisibility( View.VISIBLE );
                        textView.setText( "Monthly Report" );
                        text = "Monthly";
                        ServerConnection( idpass, selected, text );

                        break;

                }
            }
        } );

    }

    private void DecisionakingWeeklyLayout() {

        rb7.setChecked( false );
        rb8.setChecked( false );
        aerogroup2.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.Daero2:
                        Log.d( "meIdharHoon", "WE" );
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.VISIBLE );
                        rl4.setVisibility( View.INVISIBLE );
                        text = "Daily";
                        ServerConnection( idpass, selected, text );


                        break;


                    case R.id.M2:
                        rl1.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.VISIBLE );
                        rb8.setText( "Weekly" );
                        weraero.setText( "Monthly" );
                        text = "Monthly";
                        ServerConnection( idpass, selected, text );

                        break;

                }
            }
        } );

    }
    private void WithErrors() {
        reports3.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {

                    case R.id.Dz:

                        break;


                    case R.id.Mzz:

                        break;


                }
            }
        } );


    }

    /////////////////////////////////////////////////////////////////////
    private ArrayList<String> refinedFoodItems(List<String> aerobiclist) {
//        refinedlist = new ArrayList<>();
//
//        for (int i = 0; i < foodList.size() - 1; i++) {
//            refinedlist.add( foodList.get( i ) );
//        }
//
//        return  refinedlist;

        int repitition = 0;
        uniquevalue = new ArrayList<>();
        for (int x = 0; x < aerobiclist.size(); x++) {
            for (int y = 0; y < aerobiclist.size(); y++) {
                if (aerobiclist.get( x ).equals( aerobiclist.get( y ) )) {
                    repitition++;
                } // if ka band hai
            } // inner for

            if (!uniquevalue.contains( aerobiclist.get( x ) )) {
                uniquevalue.add( aerobiclist.get( x ) + "(" + repitition + ")" );
                repitition = 0;
            } else {
                repitition = 0;
            }
        } // outer for

        return uniquevalue;
    }

    private void uniquekeypair(List<String> aerobiclist) {
        hs = new HashSet<>();
        hs.addAll( aerobiclist );
        aerobiclist.clear();
        aerobiclist.addAll( hs );

    }

    private void pairing(List<FoodEaten> foodList) {
        Set<FoodEaten> hsw = new HashSet<>();
        hsw.addAll( foodList );
        foodList.clear();
        foodList.addAll( hsw );

    }

    private int countOccurrences(String foodlistitems) {
        int count = 0;
        for (int i = 0; i < foodlistitems.length(); i++) {
            if (foodlistitems.charAt( i ) == ';') {
                count++;
            }
        }
        return count;
    }
}