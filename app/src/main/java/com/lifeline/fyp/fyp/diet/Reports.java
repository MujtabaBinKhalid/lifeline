package com.lifeline.fyp.fyp.diet;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.models.RequestingDietSession;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.CalorieSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FetchingSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;
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

// 3 apis calls are used , first to fetch the id of the user , second to fetch the id of the diet plan active,
// third used to fetch the sessinos of that id.

/**
 * A simple {@link Fragment} subclass.
 */
public class Reports extends Fragment {

    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    private String email, fooditems;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    private ReportModel listAdapter; // adapter object .
    ArrayList<CalorieSessionResponse> lastmonthlastresult;
    private TextView datetext, totalcal, we;
    Integer id;
    Set<String> hs;
    TextView wer;
    ArrayList<String> refinedlist;
    Integer lastweek;
    TextView givingtext;
    RelativeLayout rl, rl2, rl3;
    ArrayList<String> uniquevalue;
    List<String> foodList;
    List<String> previousweekfoodlist;
    TextView error;
    Integer DietPlanid;
    Api api;
    ListView listview; // displaying all the list of the food items.
    Call<ResponsePlanArray> call2;
    Call<FetchingSessionResponse> call3;
    private String[] current;
    private String lastday;
    private float previousdaycalories;
    private float previousfullweekcalories;
    private String[] utc;
    private Calendar calender;
    private String formattedDate;
    private SimpleDateFormat df;
    Call<MemberObject> call;
    String UTC;
    RelativeLayout onlyerrors;
    String onlyDate; // only date.
    ArrayAdapter<String> adapter;
    private RadioGroup radioGroup, radioGroup2, radioGrouperr, radioGroupMonth,reports3;
    private RadioButton radioButton;
    String text;
    ExpandableListView rvv;
    private RecyclerView.Adapter dpa; // dietplanadapter adaoter of that.
    private TextView cal1, date1;
    RadioButton btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_reports, container, false );

        wer = (TextView) view.findViewById( R.id.wer );
        datetext = (TextView) view.findViewById( R.id.datetext );
        date1 = (TextView) view.findViewById( R.id.datetext1 );
        cal1 = (TextView) view.findViewById( R.id.caltext1 );
        totalcal = (TextView) view.findViewById( R.id.caltext );
        listview = (ListView) view.findViewById( R.id.lastday );
progressDialog = new ProgressDialog( getActivity() );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( "Generating Reports" );


        rl = (RelativeLayout) view.findViewById( R.id.layoutholding );
        rl2 = (RelativeLayout) view.findViewById( R.id.layoutholdingerr );
        rl3 = (RelativeLayout) view.findViewById( R.id.layoutreports2 );
        btn = (RadioButton) view.findViewById( R.id.M2 );
        givingtext = (TextView) view.findViewById( R.id.givingtext );

        onlyerrors = (RelativeLayout) view.findViewById( R.id.onlyerrors );
        rvv = (ExpandableListView) view.findViewById( R.id.rvvv );
//        LinearLayoutManager dpm = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );
//        rvv.setLayoutManager( dpm );

        //88888888888888888888888

        // image button life style.
        ImageButton lifestyle1 = (ImageButton)view. findViewById( R.id.lifestyle1 );
        lifestyle1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), LifeStyle.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        } );


        // fitnese btn of the nav bar.
        ImageButton fitness1 = (ImageButton) view.findViewById( R.id.fitness1 );
        fitness1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), MainActivity.class );
                intent.putExtra( "flow","noexercise;" );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right ); // right to left.

            }
        } );


        ImageButton home1 = (ImageButton)view. findViewById( R.id.home1 );
        home1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), MainActivity.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        } );
        ImageButton profile1 = (ImageButton)view. findViewById( R.id.profile1 );
        profile1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( getActivity(), UserProfile.class );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        } );

        // diet button.

        ImageButton diet1 = (ImageButton) view.findViewById( R.id.nutrition1 );
        diet1.setEnabled( false );
        diet1.setClickable( false );




        //,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

        text = "Daily";
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        we = (TextView) view.findViewById( R.id.wer );

        previousweekfoodlist = new ArrayList<>();
        radioGroup = (RadioGroup) view.findViewById( R.id.reports );
        radioGroup2 = (RadioGroup) view.findViewById( R.id.reports2 );
        radioGrouperr = (RadioGroup) view.findViewById( R.id.reportserr );
        reports3 = (RadioGroup) view.findViewById( R.id.reports3 );

        DecisionMakingErrorLayout(); // radio buttons on the eeror layout.
        DecisionMakingDailyLayout(); // radio buttons on the daily (mainlayo).
        DecisionakingWeeklyLayout();
        WithErrors();


        // current Date...

        calender = Calendar.getInstance(); //  current instance.
        df = new SimpleDateFormat( "dd-MM-yyyy" ); // ormat
        formattedDate = df.format( calender.getTime() ); // getting time.
        current = formattedDate.split( "-" ); // seperqtor.


        error = (TextView) view.findViewById( R.id.error );


        // setting retrofit.
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        api = retrofit.create( Api.class );
        ServerConnection( text );
        return view;
    }


    private void ServerConnection(final String type) {
        Runnable runreports = new Runnable() {
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
                                        List<SendingPlan> gettingsession = response.body().getData();
                                        if (gettingsession.get( gettingsession.size() - 1 ).getActiveStatus().equals( "active" )) {
                                            DietPlanid = gettingsession.get( gettingsession.size() - 1 ).getDietPlanId();
                                            Log.d( "gggg", String.valueOf( DietPlanid ) );
                                            // LAST CALL FOR FETCHING THE PLANS OF THE SPECIFIC ID.

                                            RequestingDietSession rds = new RequestingDietSession( DietPlanid );


                                            call3 = api.fetchingsessions( rds );

                                            call3.enqueue( new Callback<FetchingSessionResponse>() {
                                                @Override
                                                public void onResponse(Call<FetchingSessionResponse> call, Response<FetchingSessionResponse> response) {

                                                    if (response.body().getDataCount() == 0) {
                                                        error.setVisibility( View.VISIBLE );
                                                        rl2.setVisibility( View.VISIBLE );
                                                        error.setText( "Start session to view your progress" );
                                                        radioGrouperr.setVisibility( View.INVISIBLE );
                                                    } else if (response.body().getDataCount() != 0) {
                                                        List<CalorieSessionResponse> sessionsall = response.body().getData();

                                                        if (type.equals( "Daily" )) {
                                                            previousDayReport( (ArrayList<CalorieSessionResponse>) sessionsall );

                                                        } else if (type.equals( "Weekly" )) {
                                                            previousWeekReport( (ArrayList<CalorieSessionResponse>) sessionsall );

                                                        } else if (type.equals( "Monthly" )) {
                                                            previousMonthReport( (ArrayList<CalorieSessionResponse>) sessionsall );

                                                        }
                                                        //  previousWeekReport( (ArrayList<CalorieSessionResponse>) sessionsall );
                                                        //previousMonthReport( (ArrayList<CalorieSessionResponse>) sessionsall );

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<FetchingSessionResponse> call, Throwable t) {

                                                    Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                                } // failure ending
                                            } );

                                        } else {
                                            error.setVisibility( View.VISIBLE );
                                            rl2.setVisibility( View.VISIBLE );
                                            radioGrouperr.setVisibility( View.INVISIBLE );
                                        }


                                    }  // ending of the tru.
                                    catch (IndexOutOfBoundsException e) {
                                        error.setVisibility( View.VISIBLE );
                                        rl2.setVisibility( View.VISIBLE );
                                        radioGrouperr.setVisibility( View.INVISIBLE );

                                    }


                                    // third api call.

                                }

                                @Override
                                public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

                                    Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                                } // failure ending
                            } );  // second call.


                        } // if ka closing.
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Toast.makeText( getActivity(), "Failed", Toast.LENGTH_SHORT ).show();
                    } // failure ending
                } ); // ca

            }
        };


        Thread runnable = new Thread( runreports );
        runnable.start();


    }

    private void previousDayReport(ArrayList<CalorieSessionResponse> sessionsall) {

        // 1st of the new nonth then?
        lastday = Integer.parseInt( current[0] ) - 1 + ""; // going to preious month.
        int checker = 0; // checking if the result exists or not.
        Log.d( "lastday", lastday );
        lastmonthlastresult = new ArrayList<CalorieSessionResponse>(); // for storing all the result of the previous month.

        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int countinglast = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );

        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getTime() );
            onlyDate = UTC.substring( 0, 10 );
            utc = onlyDate.split( "-" ); // seperate.
            //  now comparing days.
            // first comparing months.
            StringBuilder sb = new StringBuilder( utc[1] );
            sb.deleteCharAt( 0 );
            Log.d( "vvcc", sb.toString() );
            Log.d( "vvbb", lastmonth.toString() );


            if (current[1].equals( utc[1] )) {

                if (lastday.equals( utc[2] )) {
                    previousdaycalories += sessionsall.get( m ).getCaloriesEatenOnDay();
                    fooditems = sessionsall.get( m ).getFoodItems() + ";" + fooditems;

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
            progressDialog.hide();
            onlyerrors.setVisibility( View.VISIBLE );
//            error.setVisibility( View.VISIBLE );
//            rl2.setVisibility( View.VISIBLE );
            givingtext.setText( "NO , session were performed yesterday." );
//            radioGrouperr.setVisibility( View.INVISIBLE );


        } else {

            if (lastmonthlastresult.size() > 0) {
                Log.d( "lLlLl", String.valueOf( lastmonthlastresult.size() ) );


                UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.

                String lastmothdate = utc[2];

                for (int p = 0; p < lastmonthlastresult.size(); p++) {
                    UTC = String.valueOf( sessionsall.get( p ).getTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.


                    if (lastmothdate.equals( utc[2] )) {
                        previousdaycalories += sessionsall.get( p ).getCaloriesEatenOnDay();
                        fooditems = sessionsall.get( p ).getFoodItems() + ";" + fooditems;
                    } // deail with days.


                } // for loop ends


                Log.d( "newcomer", utc[2] );
                Log.d( "newcomer", String.valueOf( previousdaycalories ) );
            }  // if the new months starts and the user wants to see the last report.


            // same month,last day.


            foodList = new ArrayList<>( Arrays.asList( fooditems.split( ";" ) ) );

            foodList.remove( foodList.size() - 1 ); // removing the last index as it contain NULL.

            Log.d( "lastdayc", String.valueOf( foodList.size() ) );
            String today = lastday + "-" + current[1] + "-" + current[2];


            foodList = refinedFoodItems( foodList );  // the method used for containing the element occurrences.
            uniquekeypair( foodList ); // it make key value pair.
            Collections.sort( foodList ); // it arrange the elements.

            totalcal.setText( previousdaycalories + "" );
            datetext.setText( today );

            adapter = new ArrayAdapter<String>( getActivity(), R.layout.fooditemname, R.id.itemname, uniquevalue );
            listview.setAdapter( adapter );

            progressDialog.hide();
            rl.setVisibility( View.VISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            onlyerrors.setVisibility( View.INVISIBLE );

        }


    } // end of method

    private void previousWeekReport(ArrayList<CalorieSessionResponse> sessionsall) {


        lastweek = Integer.parseInt( current[0] ); // aj ka din.
        int checker = 0;
        Integer previous = 0;
        ArrayList<ReportModel> perday = new ArrayList<>();
        ArrayList<FoodEaten> fd;
        Date date;
        previousfullweekcalories = 0;
        String counting;
        lastmonthlastresult = new ArrayList<CalorieSessionResponse>(); // for storing all the result of the previous month.
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
            fooditems = "";

            fd = new ArrayList<FoodEaten>();

            for (int m = 0; m < sessionsall.size(); m++) {
                UTC = String.valueOf( sessionsall.get( m ).getTime() );
                onlyDate = UTC.substring( 0, 10 );
                utc = onlyDate.split( "-" ); // seperate.
                //  now comparing days.
                // first comparing months.

                Log.d( "ooooooooooooooooooD", onlyDate );


                if (current[1].equals( utc[1] )) {

                    if (previous.toString().equals( utc[2] )) {

                        checker++;
                        Log.d( "chances", String.valueOf( previous ) );
                        previousdaycalories += sessionsall.get( m ).getCaloriesEatenOnDay();
                        previousfullweekcalories += sessionsall.get( m ).getCaloriesEatenOnDay();
                        fooditems = sessionsall.get( m ).getFoodItems() + ";" + fooditems;


                    }
                }


            } // for loop.


            if (checker == 0) {
progressDialog.hide();
                onlyerrors.setVisibility( View.VISIBLE );
//                error.setVisibility( View.VISIBLE );
//                rl2.setVisibility( View.VISIBLE );
                givingtext.setText( "NO , last week history available ." );
            } else {
                counting = countOccurrences( fooditems ) + "";
                Log.d( "countinggggg", String.valueOf( counting ) );


                if (!counting.equals( "0" )) {
                    // spliting the food items of daily basics in to an arraylist.
                    foodList = new ArrayList<>( Arrays.asList( fooditems.split( ";" ) ) );

                    // foodList.remove( foodList.size() - 1 );

//                for (int i=0; i< foodList.size();i++){
//                    FoodEaten ffee= new FoodEaten(foodList.get( i ));
//                    fd.add( ffee );
//                }

                    foodList = new ArrayList<>( Arrays.asList( fooditems.split( ";" ) ) );

                    // foodList.remove( foodList.size() - 1 ); // removing the last index as it contain NULL.

                    Log.d( "lastdayc", String.valueOf( foodList.size() ) );


                    foodList = refinedFoodItems( foodList );  // the method used for containing the element occurrences.


                    uniquekeypair( foodList ); // it make key value pair.

                    Log.d( "kyastorye", String.valueOf( foodList.size() ) );

                    for (int w = 0; w < foodList.size(); w++) {
                        FoodEaten ffee = new FoodEaten( foodList.get( w ) );
                        fd.add( ffee );
                    }

                    Collections.sort( foodList ); // it arrange the elements


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
                    Log.d( "callll-33", String.valueOf( foodList.size() + " mjy yakeen ni a rha" ) );
                    Log.d( "callll", String.valueOf( previousdaycalories ) );
                    Log.d( "NewExpweriment", String.valueOf( fd.size() ) );

                    perday.add( new ReportModel( previousdaycalories + "", datewithDay, day, fd ) );
                    Log.d( "callllperady", String.valueOf( perday.size() ) );

                }

                // idher,

            } // outer loop.
            Log.d( "sdsds", String.valueOf( perday.size() ) );

            String[] sepdate = perday.get( perday.size() - 1 ).getDate().split( " " ); // starting
            String[] sepdate1 = perday.get( 0 ).getDate().split( " " ); // endng.

            date1.setText( sepdate[2] + "/" + sepdate[1] + " to " + sepdate1[2] + "/" + sepdate1[1] + "/" + sepdate[3] );

            Log.d( "wssw", perday.get( perday.size() - 1 ).getDate() );
            Log.d( "wssw", perday.get( perday.size() - perday.size() ).getDate() );
            ReportAdapter ra = new ReportAdapter( getActivity(), perday );
            rvv.setGroupIndicator( null );
            cal1.setText( previousfullweekcalories + "" );

            rvv.setAdapter( ra );

            Log.d( "baba", utc[2] );
            Log.d( "baba", String.valueOf( sessionsall.size() ) );

        }

progressDialog.hide();

        rl.setVisibility( View.INVISIBLE );
        rl2.setVisibility( View.INVISIBLE );
        rl3.setVisibility( View.VISIBLE );
        onlyerrors.setVisibility( View.INVISIBLE );

    }

    private void previousMonthReport(ArrayList<CalorieSessionResponse> sessionsall) {
        Integer lastmonth = Integer.parseInt( current[1] ) - 1;
        //Integer lastmonth = 3 - 1;
        int checker = 0;
        Log.d( "lastmonth", String.valueOf( lastmonth ) );
        lastmonthlastresult = new ArrayList<CalorieSessionResponse>(); // for storing all the result of the previous month.


        for (int m = 0; m < sessionsall.size(); m++) {
            UTC = String.valueOf( sessionsall.get( m ).getTime() );
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
//
//            error.setVisibility( View.VISIBLE );
//            rl2.setVisibility( View.VISIBLE );
//            we.setVisibility( View.INVISIBLE );
            progressDialog.hide();
            rl3.setVisibility( View.INVISIBLE );
            onlyerrors.setVisibility( View.VISIBLE );
            givingtext.setText( "NO , report available." );
        } else {
            rl3.setVisibility( View.VISIBLE );
            Log.d( "lLlLl", String.valueOf( lastmonthlastresult.size() ) );

            //////////////////////
            Integer previous = 0;
            ArrayList<ReportModel> perday = new ArrayList<>();
            ArrayList<FoodEaten> fd;
            Date date;
            previousfullweekcalories = 0;
            String counting;
            lastmonthlastresult = new ArrayList<CalorieSessionResponse>(); // for storing all the result of the previous month.


            ////////////////////////
            UTC = String.valueOf( lastmonthlastresult.get( lastmonthlastresult.size() - 1 ).getTime() );
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
                fooditems = "";

                fd = new ArrayList<FoodEaten>();

                Log.d( "newcomer11", String.valueOf( lastmothdate ) );
                for (int p = 0; p < lastmonthlastresult.size(); p++) {

                    UTC = String.valueOf( sessionsall.get( p ).getTime() );
                    onlyDate = UTC.substring( 0, 10 );
                    utc = onlyDate.split( "-" ); // seperate.
                    Log.d( "newcomer22", utc[2] );

//
                    if (lastmothdate.toString().equals( utc[2] )) {
                        Log.d( "idherhoon", utc[2] );
                        checker++;
                        Log.d( "chances", String.valueOf( previous ) );
                        previousdaycalories += sessionsall.get( p ).getCaloriesEatenOnDay();
                        previousfullweekcalories += sessionsall.get( p ).getCaloriesEatenOnDay();
                        fooditems = sessionsall.get( p ).getFoodItems() + ";" + fooditems;

                    } // deail with days.


                } // loop ended.

                if (checker == 0) {

                    onlyerrors.setVisibility( View.VISIBLE );
                    //
//                    error.setVisibility( View.VISIBLE );
//                    rl2.setVisibility( View.VISIBLE );
                   givingtext.setText( "NO , last week history available ." );
                } else {
                    counting = countOccurrences( fooditems ) + "";
                    Log.d( "countinggggg", String.valueOf( counting ) );


                    if (!counting.equals( "0" )) {
                        // spliting the food items of daily basics in to an arraylist.
                        foodList = new ArrayList<>( Arrays.asList( fooditems.split( ";" ) ) );

                        // foodList.remove( foodList.size() - 1 );

//                for (int i=0; i< foodList.size();i++){
//                    FoodEaten ffee= new FoodEaten(foodList.get( i ));
//                    fd.add( ffee );
//                }

                        foodList = new ArrayList<>( Arrays.asList( fooditems.split( ";" ) ) );

                        // foodList.remove( foodList.size() - 1 ); // removing the last index as it contain NULL.

                        Log.d( "lastdayc", String.valueOf( foodList.size() ) );


                        foodList = refinedFoodItems( foodList );  // the method used for containing the element occurrences.


                        uniquekeypair( foodList ); // it make key value pair.

                        Log.d( "kyastorye", String.valueOf( foodList.size() ) );

                        for (int w = 0; w < foodList.size(); w++) {
                            FoodEaten ffee = new FoodEaten( foodList.get( w ) );
                            fd.add( ffee );
                        }

                        Collections.sort( foodList ); // it arrange the elements


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
                        Log.d( "callll-33", String.valueOf( foodList.size() + " mjy yakeen ni a rha" ) );
                        Log.d( "callll", String.valueOf( previousdaycalories ) );
                        Log.d( "NewExpweriment", String.valueOf( fd.size() ) );

                        perday.add( new ReportModel( previousdaycalories + "", datewithDay, day, fd ) );
                        Log.d( "callllperady", String.valueOf( perday.size() ) );

                    }

                    // idher,

                } // outer loop.
                Log.d( "sdsds", String.valueOf( perday.size() ) );

                String[] sepdate = perday.get( perday.size() - 1 ).getDate().split( " " ); // starting
                String[] sepdate1 = perday.get( 0 ).getDate().split( " " ); // endng.

                date1.setText( sepdate[2] + "/" + sepdate[1] + " to " + sepdate1[2] + "/" + sepdate1[1] + "/" + sepdate[3] );

                Log.d( "wssw", perday.get( perday.size() - 1 ).getDate() );
                Log.d( "wssw", perday.get( perday.size() - perday.size() ).getDate() );
                ReportAdapter ra = new ReportAdapter( getActivity(), perday );
                rvv.setGroupIndicator( null );
                cal1.setText( previousfullweekcalories + "" );

                rvv.setAdapter( ra );

                Log.d( "baba", utc[2] );
                Log.d( "baba", String.valueOf( sessionsall.size() ) );

            }

            progressDialog.hide();
            rl.setVisibility( View.INVISIBLE );
            onlyerrors.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );

//
        }

        //   Log.d( "newcomer", utc[2] );
        // Log.d( "newcomer", String.valueOf( previousdaycalories ) );

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

    private ArrayList<String> refinedFoodItems(List<String> foodList) {
//        refinedlist = new ArrayList<>();
//
//        for (int i = 0; i < foodList.size() - 1; i++) {
//            refinedlist.add( foodList.get( i ) );
//        }
//
//        return  refinedlist;

        int repitition = 0;
        uniquevalue = new ArrayList<>();
        for (int x = 0; x < foodList.size(); x++) {
            for (int y = 0; y < foodList.size(); y++) {
                if (foodList.get( x ).equals( foodList.get( y ) )) {
                    repitition++;
                } // if ka band hai
            } // inner for

            if (!uniquevalue.contains( foodList.get( x ) )) {
                uniquevalue.add( foodList.get( x ) + "(" + repitition + ")" );
                repitition = 0;
            } else {
                repitition = 0;
            }
        } // outer for

        return uniquevalue;
    }

    private void uniquekeypair(List<String> foodList) {
        hs = new HashSet<>();
        hs.addAll( foodList );
        foodList.clear();
        foodList.addAll( hs );

    }

    private void pairing(List<FoodEaten> foodList) {
        Set<FoodEaten> hsw = new HashSet<>();
        hsw.addAll( foodList );
        foodList.clear();
        foodList.addAll( hsw );

    }

    private void DecisionMakingErrorLayout() {
        radioGrouperr.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {

                    case R.id.WE:
                        Log.d( "meIdharHoon", "WE" );
                        we.setText( "Weekly Report" );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.VISIBLE );
                        ServerConnection( "Weekly" );
                        break;


                    case R.id.ME:
                        ServerConnection( "Monthly" );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.VISIBLE );

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
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
                        progressDialog.show();
                        ServerConnection( "Daily" );
                        break;


                    case R.id.Mzz:
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
                        progressDialog.show();
                        ServerConnection( "Weekly" );
                        break;


                }
            }
        } );


    }


    private void DecisionMakingDailyLayout() {
        radioGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.W:
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );

                        progressDialog.show();
                        Log.d( "QWWQQWQ","!11" );
                        ServerConnection( "Weekly" );
                        break;


                    case R.id.M:
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
progressDialog.show();
                        Log.d( "QWWQQWQ","!22" );

                        ServerConnection( "Monthly" );
                        wer.setText( "Monthly Report" );
                        break;

                }
            }
        } );

    }

    private void DecisionakingWeeklyLayout() {
        radioGroup2.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.D:
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );

                        Log.d( "QWWQQWQ","!33" );
                        progressDialog.show();
                        ServerConnection( "Daily" );
                        break;


                    case R.id.M2:
                        onlyerrors.setVisibility( View.INVISIBLE );
                        rl.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.INVISIBLE );
progressDialog.show();
                        Log.d( "QWWQQWQ","!44" );
                        ServerConnection( "Monthly" );
                        wer.setText( "Monthly Report" );
                        btn.setText( "Weekly" );
                        break;

                }
            }
        } );

    }
}
