package com.lifeline.fyp.fyp.fitness;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.ScreenShot;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.diet.CategoricallyFoodItems;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.diet.DietPlanQuestion1;
import com.lifeline.fyp.fyp.diet.DietPlans;
import com.lifeline.fyp.fyp.diet.DietSession;
import com.lifeline.fyp.fyp.diet.ListAdapter;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.models.AerobicCategoryName;
import com.lifeline.fyp.fyp.models.AerobicResponse;
import com.lifeline.fyp.fyp.models.CategoryAerobic;
import com.lifeline.fyp.fyp.models.CategoryAerobicGif;
import com.lifeline.fyp.fyp.models.FitnessSessionSummary;
import com.lifeline.fyp.fyp.models.FoodCategory;
import com.lifeline.fyp.fyp.models.FoodItems;
import com.lifeline.fyp.fyp.models.SendingAerobicExerciseData;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.UploadImageInterface;
import com.lifeline.fyp.fyp.retrofit.responses.AerobicExercisesCategoryList;
import com.lifeline.fyp.fyp.retrofit.responses.AerobicExercisesOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.DeletingPlan;
import com.lifeline.fyp.fyp.retrofit.responses.FetchingAerobicExerciseCalories;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessAllSessions;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.PostingStatus;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;
import com.lifeline.fyp.fyp.retrofit.responses.StatusResponse;
import com.lifeline.fyp.fyp.retrofit.responses.UploadObject;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FitnessSession extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    // rl0 ---> when nothing is there,
    // rl1 when the basic info of the session is there
    // rl2 when the timer goes on ,
    // fl1 displaying map.
    // rl3---> for showing result.

    private String path2;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    LatLng originalLatLngfused;
    TextView username;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int FINE_LOCATION = 101;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    TextView solution, ndate, ndatetext;
    private GoogleMap mMap;
    UploadImageInterface uploadImage;
    private Button start, end, endeuro;
    private DrawerLayout dlayout; // drawerlayout
    private Toolbar toolbar;
    boolean exerciseStatus;
    private boolean BooleanChecker;
    private NavigationView mNavigationview;
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private RelativeLayout rl1, rl0, rl2, rl3, rl4, rl5, rl6;
    private FrameLayout fl1;
    private Menu menuNav;

    CircleImageView userimage;
    LocationManager locationManager;
    FloatingActionButton btn;
    String email, btnpressed;
    float results[] = new float[10];
    float totaldistnceCovered = 0;
    Date date1, date2, datestr, datestr2;
    String convertedDate1, convertedDate2;
    SimpleDateFormat sdf;
    Api api;
    String motherfucker;
    String aerobicsExercises = "";
    ListView listView;
    Retrofit retrofit;
    FirebaseAuth auth;
    FirebaseUser user;
    String userweight, userheight;
    Call<MemberObject> call;

    ArrayList<Double> totalaerobiccalories;
    Call<FitnessResponseObject> call2;
    TextView tip, pname, st, weeks, timer, popup, distance, share,
            distancevalue, timevalue, speedvalue, calorievalue, dfp,
            text1, text2, text3, text4, text0, text00, text01, aerbicExercise, stopwatch, stopwatch2, aerobicexercisetitle;
    View view;
    ImageView image1, image2, image3, image4, displayinggif, image5, image6, image7;
    Integer id, dietId;
    int counter = 0;
    int Checker = 0;
    int position;
    int Value = -1;
    double weight;
    private ArrayList<LatLng> storing;
    Marker mCurrLocationMarker, originMarker2, originMarker;
    int fitnessId, days, hours, min, seconds;
    float calorieburnt;
    Button running, cycling, exer, stop, testing, aerostart;
    String nameoftheplan, dateoftheplan;
    double weeksoftheplan;
    Calendar calender; //  current instance.
    SimpleDateFormat df; // ormat
    String formattedDate; // getting time.
    String[] current; // seperqtor.
    String[] aerobicsplit; // seperqtor.
    String UTC, flow;
    String values;
    String onlyDate; // only date.
    float checker = 0;
    boolean presentingstatus; // if its true then the piechar will appear. otherwise no chart.
    String[] utc;
    ProgressDialog dialogproces;
    boolean linking;
    Handler stopwatchhandler;
    MenuItem item0, item1, item2, item3, item4, item5, item6;
    ArrayList<String> exerlist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private SubCategoryAdapter listAdapter; // adapter object .
    private ArrayList<CategoryAerobic> categoryAerobics = new ArrayList<CategoryAerobic>(); // main group array list parents.
    public ArrayList<CategoryAerobicGif> newArray;
    private ExpandableListView expandableListView;
    float lastdistance, lastspeed, lasttime, lastcalorie;
    int secon = 0;
    float speed;
    Handler customhandler = new Handler();
    long starttime = 0L, timemilliseconds = 0L, updateTime = 0L, timeSwafbuff = 0L;

    int Seconds, Minutes, MilliSeconds;
    String selectedcategryname;
    String anotheruserid;
    Integer anotherfitnessid;
    TextView startingtext;
    TextView sttext, runningtext, aerobictext, weekstext, cyclingtext;

    //////////////////////////////////////////////////////////////

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {

            timemilliseconds = SystemClock.uptimeMillis() - starttime;

            updateTime = timeSwafbuff + timemilliseconds;

            Seconds = (int) (updateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (updateTime % 1000);

            stopwatch.setText( "" + Minutes + ":"
                    + String.format( "%02d", Seconds ) + ":"
                    + String.format( "%03d", MilliSeconds ) );

            customhandler.postDelayed( this, 0 );
        }
    };


    Handler handler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Log.d( "Faizabad", "Kam ho rha" );
                    double lat = location.getLatitude();

                    double lon = location.getLongitude();


                    Log.d( "qqww", String.valueOf( lat ) );
                    Log.d( "qqww", String.valueOf( lon ) );

                    LatLng latlng = new LatLng( lat, lon );
                    try {
                        storing.add( latlng );

                    } catch (Exception e) {
                        Toast.makeText( FitnessSession.this, "Warning..", Toast.LENGTH_SHORT ).show();
                    }


                    Log.d( "SIZEEE", String.valueOf( storing.size() ) );

                    try {

                        if (Checker == 0) {
                            originMarker.remove();
                            originMarker2 = mMap.addMarker( new MarkerOptions().position( latlng ).title( "Origin.." )
                                    .icon( BitmapDescriptorFactory.
                                            defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) ) );
                            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latlng, 16.0f ) );

                            Log.d( "qqww", "FOr Once Only" );

                        }
                        if (mCurrLocationMarker != null) {
                            mCurrLocationMarker.remove();

                            Log.d( "qqww", "Remobved" );

                            if (Checker > 0) {
                                Location.distanceBetween( storing.get( Value )
                                        .latitude, storing.get( Value ).longitude, storing.get( storing.size() - 1 )
                                        .latitude, storing.get( storing.size() - 1 ).longitude, results );

                                Log.d( "Disstance", String.valueOf( results[0] ) );

                                totaldistnceCovered = totaldistnceCovered + results[0];

                                if (totaldistnceCovered > 1000) {
                                    float dist = totaldistnceCovered / 1000;
                                    String distancethreeleteres2 = totaldistnceCovered + "";

                                    distance.setText( "" + distancethreeleteres2.substring( 0, 4 ) + " Km" );
                                } else {
                                    String distancethreeleteres = totaldistnceCovered + "";
                                    distance.setText( "" + distancethreeleteres.substring( 0, 5 ) + " meters" );

                                }


                            }

                            String url = getRequestUrl( storing.get( 0 ), storing.get( storing.size() - 1 ) );
                            TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                            taskRequestDirections.execute( url );

                        }
                        mCurrLocationMarker = mMap.addMarker( new MarkerOptions()
                                .position( latlng ).title( "Moving.." )
                                .icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) ) );

                        Log.d( "Checker", String.valueOf( Checker ) );
                        Log.d( "Checker11", String.valueOf( storing.size() ) );

                        Checker++;
                        Value++;

                    } catch (Exception e) {
                        Log.d( "qqww", "vvavavava" );
                        Log.d( "qqwwMsg", e.getMessage() );

                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            } );


        }
    };


    /////////////////////////////////////////////////////////////

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 750, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double lat = location.getLatitude();
                    double lon = location.getLongitude();


                    LatLng latlng = new LatLng( lat, lon );

                    if (counter == 0) {
                        originMarker = mMap.addMarker( new MarkerOptions().position( latlng ).title( "Current Location" )
                                .icon( BitmapDescriptorFactory.
                                        defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) ) );
                        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latlng, 16f ) );
                    }
                    counter++;
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            } );

        }
    };

    ///////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTitle( "Fitness" );
        setContentView( R.layout.activity_fitness_session );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( this );
        locationRequest = LocationRequest.create();
        BooleanChecker = false;
        listView = (ListView) findViewById( R.id.exerlistview );
        expandableListView = (ExpandableListView) findViewById( R.id.expandableList );
        totalaerobiccalories = new ArrayList<>();
        // layouts.
        rl1 = (RelativeLayout) findViewById( R.id.layout1 );
        rl0 = (RelativeLayout) findViewById( R.id.layout0 );
        rl2 = (RelativeLayout) findViewById( R.id.timerlayout );
        fl1 = (FrameLayout) findViewById( R.id.layout2 );
        rl3 = (RelativeLayout) findViewById( R.id.result );
        rl4 = (RelativeLayout) findViewById( R.id.layout4 );
        rl5 = (RelativeLayout) findViewById( R.id.layout5 );
        rl6 = (RelativeLayout) findViewById( R.id.layout6 );
        timer = (TextView) findViewById( R.id.timer );
        aerbicExercise = (TextView) findViewById( R.id.aerobicExercise );
        stopwatch = (TextView) findViewById( R.id.stopwatch );
        stopwatch2 = (TextView) findViewById( R.id.stopwatch2 );
        endeuro = (Button) findViewById( R.id.endaerobics );
        ndate = (TextView) findViewById( R.id.newdate );
        ndatetext = (TextView) findViewById( R.id.newdatetext );
        dfp = (TextView) findViewById( R.id.dietlinkedplanfit );
        aerobicexercisetitle = (TextView) findViewById( R.id.aerobicExercisetitle );
        stopwatchhandler = new Handler();
        // Buttons
        running = (Button) findViewById( R.id.running );
        cycling = (Button) findViewById( R.id.cycling );
        exer = (Button) findViewById( R.id.exer );
        stop = (Button) findViewById( R.id.stopbtn );
        end = (Button) findViewById( R.id.endsession );
        aerostart = (Button) findViewById( R.id.aerostart );
        share = (TextView) findViewById( R.id.share );
        runningtext = (TextView) findViewById( R.id.run );
        cyclingtext = (TextView) findViewById( R.id.cycle );
        aerobictext = (TextView) findViewById( R.id.aero );
        solution = (TextView) findViewById( R.id.solution );

        share.setVisibility( View.INVISIBLE );
//
        tip = (TextView) findViewById( R.id.tipnutri );
        pname = (TextView) findViewById( R.id.planname );
        st = (TextView) findViewById( R.id.startingtime );
        weeks = (TextView) findViewById( R.id.weeks );
        popup = (TextView) findViewById( R.id.popup );
        distance = (TextView) findViewById( R.id.distancetext );
        distancevalue = (TextView) findViewById( R.id.distancevalue );
        timevalue = (TextView) findViewById( R.id.timevalue );
        speedvalue = (TextView) findViewById( R.id.speedvalue );
        calorievalue = (TextView) findViewById( R.id.calorievalue );
        text0 = (TextView) findViewById( R.id.text0 );
        text1 = (TextView) findViewById( R.id.text1 );
        text2 = (TextView) findViewById( R.id.text2 );
        text3 = (TextView) findViewById( R.id.text3 );
        text4 = (TextView) findViewById( R.id.text4 );
        text00 = (TextView) findViewById( R.id.text00 );
        text01 = (TextView) findViewById( R.id.text01 );
        weekstext = (TextView) findViewById( R.id.weekstext );
        startingtext = (TextView) findViewById( R.id.startingtimetexttext );

        view = (View) findViewById( R.id.view1 );


        // image
        image1 = (ImageView) findViewById( R.id.image1 );
        image2 = (ImageView) findViewById( R.id.image2 );
        image3 = (ImageView) findViewById( R.id.image3 );
        image4 = (ImageView) findViewById( R.id.image4 );
        image5 = (ImageView) findViewById( R.id.image5 );
        image6 = (ImageView) findViewById( R.id.image6 );
        image7 = (ImageView) findViewById( R.id.image7 );

////
        // image button life style.
        ImageButton lifestyle = (ImageButton) findViewById( R.id.lifestyle );
        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, LifeStyle.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );


        // fitnese btn of the nav bar.
        ImageButton fitness = (ImageButton) findViewById( R.id.fitness );
        fitness.setEnabled( false );
        fitness.setClickable( false );

        ImageButton home = (ImageButton) findViewById( R.id.home );
        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, MainActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.


            }
        } );

        ImageButton profile = (ImageButton) findViewById( R.id.profile );
        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, UserProfile.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.


            }
        } );

        ImageButton profile1 = (ImageButton) findViewById( R.id.profile1 );
        profile1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, UserProfile.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.


            }
        } );

        // diet button.

        ImageButton diet = (ImageButton) findViewById( R.id.nutrition );
        diet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, DietMain.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );


        ////


        // image button life style.
        ImageButton lifestyle1 = (ImageButton) findViewById( R.id.lifestyle1 );
        lifestyle1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, LifeStyle.class );
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
                Intent intent = new Intent( FitnessSession.this, MainActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.


            }
        } );

        // diet button.

        ImageButton diet1 = (ImageButton) findViewById( R.id.nutrition1 );
        diet1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FitnessSession.this, DietMain.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );


        //////////
        // setting retrofit.
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );
        uploadImage = retrofit.create( UploadImageInterface.class );

        exer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                btnpressed = "exercise";
                if (userweight.equals( "null" )) {
                    Toast.makeText( FitnessSession.this, "Answer these questions to continue!", Toast.LENGTH_LONG ).show();
                    Log.d( "hquestion asking", "hjhjhjh" );
                    Intent intent = new Intent( FitnessSession.this, DietPlanQuestion1.class );
                    Log.d( "idididid", String.valueOf( id ) );
                    Bundle extra = new Bundle();
                    extra.putString( "state", "aerobic" );
                    extra.putString( "userid", String.valueOf( id ) );
                    extra.putString( "useremail", email );
                    intent.putExtras( extra );
                    finish();
                    startActivity( intent );
                } else {

                    Log.d( "hquestion asking", "Nohjhjhjh" );
                    rl0.setVisibility( View.INVISIBLE );
                    rl1.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.INVISIBLE );
                    rl3.setVisibility( View.INVISIBLE );
                    fl1.setVisibility( View.INVISIBLE );
                    Fetchinglist();
                }

            }
        } );

        gettingCurrentLocation();
        // setting Drawlable layout
        mNavigationview = (NavigationView) findViewById( R.id.navmenu );

        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( FitnessSession.this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview.setNavigationItemSelectedListener( this );


        // setting email
        View header = mNavigationview.getHeaderView( 0 );

        TextView useremail = (TextView) header.findViewById( R.id.currentuser );

        userimage = (CircleImageView) header.findViewById( R.id.userimage );
         username = (TextView) header.findViewById( R.id.activeusername );

        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        useremail.setText( email );


        menuNav = mNavigationview.getMenu();

        item0 = menuNav.findItem( R.id.runningmenu );
        item1 = menuNav.findItem( R.id.cyclingmenu );

        item2 = menuNav.findItem( R.id.reportsfit );
        item3 = menuNav.findItem( R.id.reportsfit1 );
        item4 = menuNav.findItem( R.id.reportsfit2 );
        //item5 = menuNav.findItem( R.id.paid );


        stop.setVisibility( View.INVISIBLE );


        stop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datestr2 = new Date();
                sdf = new SimpleDateFormat( "hh:mm a" );
                convertedDate2 = sdf.format( datestr2 );  // Ending time of the session.

                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.VISIBLE );
                StopTracking();

            }
        } );

        end.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  SendingData();

                final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
                builder.setMessage( "Do you want to share the Session?" );
                builder.setCancelable( true );

                // in this case the user wanted to update the height and weight.
                builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openingPictureDialog( rl3, "sending" );
                        Log.d( "Dfdff", String.valueOf( fitnessId ) );

                    }

                } );
                builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SendingData();
                        Log.d("Its here", "its working");



                    }
                } );

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        } );

        //sharing session
        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharingSession();
            }
        } );

        // ruuning button.
        running.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                if (userweight.equals( "null" )) {
                    Toast.makeText( FitnessSession.this, "Answer these questions to continue!", Toast.LENGTH_LONG ).show();
                    Log.d( "hquestion asking", "hjhjhjh" );
                    Intent intent = new Intent( FitnessSession.this, DietPlanQuestion1.class );
                    Log.d( "idididid", String.valueOf( id ) );
                    Bundle extra = new Bundle();
                    extra.putString( "state", "running" );
                    extra.putString( "userid", String.valueOf( id ) );
                    extra.putString( "useremail", email );
                    intent.putExtras( extra );
                    finish();
                    startActivity( intent );
                } else {
                    datestr = new Date();
                    sdf = new SimpleDateFormat( "hh:mm a" );
                    convertedDate1 = sdf.format( datestr );  // starting time of the session.

                    rl0.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.VISIBLE );
                    rl1.setVisibility( View.INVISIBLE );
                    fl1.setVisibility( View.INVISIBLE );
                    storing = new ArrayList<>();

                    btnpressed = "running";
                    Log.d( "LOL1", btnpressed );


                    timer();
                }

            }
        } );

        /////////////////////////////////////
        cycling.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                if (userweight.equals( "null" )) {
                    Toast.makeText( FitnessSession.this, "Answer these questions to continue!", Toast.LENGTH_LONG ).show();
                    Log.d( "hquestion asking", "hjhjhjh" );
                    Intent intent = new Intent( FitnessSession.this, DietPlanQuestion1.class );
                    Log.d( "idididid", String.valueOf( id ) );
                    Bundle extra = new Bundle();
                    extra.putString( "state", "running" );
                    extra.putString( "userid", String.valueOf( id ) );
                    extra.putString( "useremail", email );
                    intent.putExtras( extra );
                    finish();
                    startActivity( intent );
                } else {
                    datestr = new Date();
                    sdf = new SimpleDateFormat( "hh:mm a" );
                    convertedDate1 = sdf.format( datestr );  // starting time of the session.

                    rl0.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.VISIBLE );
                    rl1.setVisibility( View.INVISIBLE );
                    fl1.setVisibility( View.INVISIBLE );
                    storing = new ArrayList<>();

                    btnpressed = "cycling";
                    Log.d( "LOL1", btnpressed );


                    timer();
                }

            }
        } );


        // deleting the plan.

        pname.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskingUser();
            }
        } );
        ///////////////////////////////////

        // firebase and retrofit.

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();


        btn = (FloatingActionButton) findViewById( R.id.fabcreate );

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity();
            }
        } );

        CheckingPlanStatus();

        SelectingExerciseCategory();


        Log.d( "vdf", email );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flow = extras.getString( "flow" );
        }

        aerobicsplit = flow.split( ";" );

        Log.d( "valueAeroo", aerobicsplit[0] );
        try {
            rl0.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.INVISIBLE );

            if (aerobicsplit[0].equals( "exercise" )) {
                share.setVisibility( View.VISIBLE );
                endeuro.setVisibility( View.INVISIBLE );

                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                rl1.setVisibility( View.INVISIBLE );

                Log.d( "namemameem", aerobicsplit[1] );

                displayinggif = (ImageView) findViewById( R.id.displayinggif );
                Glide.with( this )
                        .load( aerobicsplit[2] ).asGif()
                        .into( displayinggif );

                aerbicExercise.setText( aerobicsplit[1] );
                rl0.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );
                rl5.setVisibility( View.INVISIBLE );
                rl6.setVisibility( View.VISIBLE );


                aerostart.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ExerciseTimer();
//                        starttime = SystemClock.uptimeMillis();
//                        handler.postDelayed(stopwatchrunnable, 0);
                    }
                } );

                Log.d( "gfg", "gdfaaa" );
                endeuro.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        timeSwafbuff += timemilliseconds;

                        stopwatchhandler.removeCallbacks( stopwatchrunnable );
                        //
                        stopwatch2.setText( stopwatch.getText() );
                        stopwatch2.setVisibility( View.VISIBLE );
                        stopwatch.setVisibility( View.INVISIBLE );
                        EndingAerobicsExercises();

                    }
                } );

            } else if (aerobicsplit[0].equals( "running" )) {

                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                Log.d( "ererererewww", aerobicsplit[0] );
                Log.d( "ererererewww", aerobicsplit[5] );
                userweight = aerobicsplit[5];

                fitnessId = Integer.parseInt( aerobicsplit[1] );

                ///
                pname.setText( aerobicsplit[2] );

                //weeks // mjy sep krna hai dot ki base per.
                weeks.setText( aerobicsplit[3] + "" );

                // date.
                String date = aerobicsplit[4];
                date = date.substring( 0, 10 );
                String[] seperator = date.split( "-" );

                st.setText( seperator[2] + "/" + seperator[1] + "/" + seperator[0] );

                //
                datestr = new Date();
                sdf = new SimpleDateFormat( "hh:mm a" );
                convertedDate1 = sdf.format( datestr );  // starting time of the session.

                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                storing = new ArrayList<>();

                btnpressed = "running";
                Log.d( "LOSSL1", btnpressed );

                CheckingPlanStatus();

                SelectingExerciseCategory();
                timer();


                stop.setVisibility( View.INVISIBLE );


                stop.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datestr2 = new Date();
                        sdf = new SimpleDateFormat( "hh:mm a" );
                        convertedDate2 = sdf.format( datestr2 );  // Ending time of the session.

                        rl0.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl1.setVisibility( View.INVISIBLE );
                        fl1.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.VISIBLE );
                        StopTracking();

                    }
                } );

                end.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //SendingData();

                        final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
                        builder.setMessage( "Do you want to share the Session?" );
                        builder.setCancelable( true );

                        // in this case the user wanted to update the height and weight.
                        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openingPictureDialog( rl3, "sending" );
                                Log.d( "Dfdff", String.valueOf( fitnessId ) );

                            }

                        } );
                        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SendingData();

                            }
                        } );

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }
                } );
            } else if (aerobicsplit[0].equals( "cycling" )) {

                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                fitnessId = Integer.parseInt( aerobicsplit[1] );
                userweight = aerobicsplit[5];

                ///
                pname.setText( aerobicsplit[2] );

                //weeks // mjy sep krna hai dot ki base per.
                weeks.setText( aerobicsplit[3] + "" );

                // date.
                String date = aerobicsplit[4];
                date = date.substring( 0, 10 );
                String[] seperator = date.split( "-" );

                st.setText( seperator[2] + "/" + seperator[1] + "/" + seperator[0] );

                //

                datestr = new Date();
                sdf = new SimpleDateFormat( "hh:mm a" );
                convertedDate1 = sdf.format( datestr );  // starting time of the session.
                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                storing = new ArrayList<>();
                btnpressed = "cycling";
                Log.d( "LOL1", btnpressed );
                CheckingPlanStatus();

                SelectingExerciseCategory();
                timer();


                stop.setVisibility( View.INVISIBLE );


                stop.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        datestr2 = new Date();
                        sdf = new SimpleDateFormat( "hh:mm a" );
                        convertedDate2 = sdf.format( datestr2 );  // Ending time of the session.

                        rl0.setVisibility( View.INVISIBLE );
                        rl2.setVisibility( View.INVISIBLE );
                        rl1.setVisibility( View.INVISIBLE );
                        fl1.setVisibility( View.INVISIBLE );
                        rl3.setVisibility( View.VISIBLE );
                        StopTracking();

                    }
                } );

                end.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //SendingData();

                        final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
                        builder.setMessage( "Do you want to share the Session?" );
                        builder.setCancelable( true );

                        // in this case the user wanted to update the height and weight.
                        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openingPictureDialog( rl3, "sending" );
                                Log.d( "Dfdff", String.valueOf( fitnessId ) );

                            }

                        } );
                        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SendingData();

                            }
                        } );

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }
                } );

            } else if (aerobicsplit[0].equals( "fromdiet" )) {
                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

                btnpressed = "exercise";
                rl0.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
               // rl4.setVisibility( View.VISIBLE );
                rl5.setVisibility( View.INVISIBLE );
                rl6.setVisibility( View.INVISIBLE );
                Fetchinglist();
            } else {

                Log.d( "gfg", "ddgdfaaa" );
                // textview.
                // textview.

                // layouts.


                // aerobic exercises


            }

        } catch (Exception e) {
            Log.d( "gfg", "gdfaaa" );

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap = googleMap;
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        mMap.setIndoorEnabled( true );
        mMap.setBuildingsEnabled( true );

//        gettingCurrentLocation();
    }

    // used for redirecting the user to create an fitness plan
    private void newActivity() {
        Intent intent = new Intent( FitnessSession.this, FitnessPlan.class );
        finish();
        startActivity( intent );

    }

    // server call to check the state of the acitivity.
    private void CheckingPlanStatus() {


        Runnable run = new Runnable() {
            @Override
            public void run() {

                call = api.fetchingUser( email );


                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                        if (response.isSuccessful()) {
Log.d( "dsfdffdfdfdfdf","zxxxxxxzxxxxxxxxxxzxxxxxxxxxxxx" );
                            id = response.body().getData().getMemberId();
username.setText( response.body().getData().getFirstName()+" "+ response.body().getData().getLastName()  );

                            try{
                                Glide.with( FitnessSession.this ).
                                        load( response.body().getData().getProfilePicture() ).into( userimage );

                            }
                            catch (Exception e){
            Glide.with( FitnessSession.this ).load( R.drawable.user ).into( userimage );
                            }
                            try {
                                userweight = response.body().getData().getWeight();
                                Log.d( "useruseruseruser", userweight );
                            } catch (Exception e) {
                                userweight = "null";
                                Log.d( "useruseruseruser", userweight );
                            }
                            call2 = api.checkingfitnessplan( id );
                            call2.enqueue( new Callback<FitnessResponseObject>() {
                                @Override
                                public void onResponse(Call<FitnessResponseObject> call, final Response<FitnessResponseObject> response) {

                                    try {

                                        String status = response.body().getData().getActiveStatus();

                                        // when the fitnessplan is active.
                                        if (status.equals( "active" )) {


//
//                                                    rl1.setVisibility( View.VISIBLE );
//                                                    rl0.setVisibility( View.INVISIBLE );
//                                                    fl1.setVisibility( View.INVISIBLE );
//
                                            fitnessId = response.body().getData().getFitnessPlanId();

                                            // plan name
                                            Log.d( "Dfeerr", response.body().getData().getName() );
                                            Log.d( "Dfeerr", "ffff" );
//                                                    pname.setText( response.body().getData().getName() );
//
                                            nameoftheplan = response.body().getData().getName();
                                            weeksoftheplan = response.body().getData().getWeeks();
                                            dateoftheplan = response.body().getData().getDate();

                                            rl1.setVisibility( View.VISIBLE );
                                            rl0.setVisibility( View.INVISIBLE );
                                            fl1.setVisibility( View.INVISIBLE );

                                            item0.setVisible( false );
                                            item1.setVisible( false );
                                            item2.setVisible( true );
                                            item3.setVisible( true );
                                            item4.setVisible( true );
                                            // item5.setVisible( true );


                                            pname.setText( nameoftheplan );

                                            //weeks // mjy sep krna hai dot ki base per.
                                            weeks.setText( weeksoftheplan + "" );

                                            // date.
                                            String date = dateoftheplan;
                                            date = date.substring( 0, 10 );
                                            String[] seperator = date.split( "-" );

                                            st.setText( seperator[2] + "/" + seperator[1] + "/" + seperator[0] );

//                                                    //weeks // mjy sep krna hai dot ki base per.
//                                                    weeks.setText( response.body().getData().getWeeks() + "" );
//
//                                                    // date.
//                                                    String date = response.body().getData().getDate();
//                                                    date = date.substring( 0, 10 );
//                                                    String[] seperator = date.split( "-" );
//
//                                                    st.setText( seperator[2] + "/" + seperator[1] + "/" + seperator[0] );
                                            currentDayresult( fitnessId );

                                            //  Log.d( "Dfdfd", date );

                                            //////////////////////////
                                            Call<ResponsePlanArray> call2 = api.checkingplan( id );
                                            call2.enqueue( new Callback<ResponsePlanArray>() {
                                                @Override
                                                public void onResponse(Call<ResponsePlanArray> call, Response<ResponsePlanArray> response) {
                                                    List<SendingPlan> users = response.body().getData();
                                                    String[] status = new String[users.size()];


                                                    for (int i = 0; i < users.size(); i++) {
                                                        status[i] = users.get( i ).getActiveStatus();
                                                    }

                                                    try {

                                                        if (status[status.length - 1].equals( "active" )) {
                                                            dietId = users.get( users.size() - 1 ).getDietPlanId();
                                                            Log.d( "Dfdfzzf", String.valueOf( dietId ) );
                                                            linking = true;
                                                            Log.d( "Dfdf", "sdsd" );

                                                            dfp.setVisibility( View.VISIBLE );

                                                            dfp.setOnClickListener( new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    Intent intent = new Intent( FitnessSession.this, DietMain.class );
                                                                    intent.putExtra( "information", email ); // combination contains boolean+gender+age.
                                                                    startActivity( intent );
                                                                }
                                                            } );
                                                        }

                                                    } catch (Exception e) {

                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<ResponsePlanArray> call, Throwable t) {

                                                }
                                            } );


                                            /////////////////////////////////


                                        } else if (!status.equals( "active" )) {

                                            Log.d( "ssss", String.valueOf( "Balay55 balay" ) );
//////////////////////////////////////////////////////////////////////////////////////////////////////////

                                            Call<ResponsePlanArray> call2 = api.checkingplan( id );
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

                                                                rl1.setVisibility( View.INVISIBLE );
                                                                rl0.setVisibility( View.VISIBLE );
                                                                fl1.setVisibility( View.INVISIBLE );

                                                                /// tu me yahan option de dnga .
                                                            }
                                                            // plan not set.
                                                            else if (!status[status.length - 1].equals( "active" )) {
                                                                Log.d( "ssss", String.valueOf( "Balaybb balay" ) );
                                                                tip.setText( "Add plan and start working !" );
                                                                tip.setVisibility( View.VISIBLE );
                                                                btn.setVisibility( View.VISIBLE );

                                                                rl1.setVisibility( View.INVISIBLE );
                                                                rl0.setVisibility( View.VISIBLE );
                                                                fl1.setVisibility( View.INVISIBLE );


                                                            }
                                                            //plan set.
                                                            else if (status[status.length - 1].equals( "active" )) {
                                                                Log.d( "ssss", String.valueOf( "Balaycc balay" ) );
                                                                tip.setText( "Diet plan is in progress." );
                                                                tip.setVisibility( View.VISIBLE );
                                                                btn.setVisibility( View.INVISIBLE );

                                                                rl1.setVisibility( View.INVISIBLE );
                                                                rl0.setVisibility( View.VISIBLE );
                                                                fl1.setVisibility( View.INVISIBLE );


                                                            }


                                                        } catch (IndexOutOfBoundsException e) {

                                                            Log.d( "ssss", String.valueOf( "Balaydd balay" ) );
                                                            tip.setText( "Add plan and start working !" );
                                                            tip.setVisibility( View.VISIBLE );
                                                            btn.setVisibility( View.VISIBLE );

                                                            rl1.setVisibility( View.INVISIBLE );
                                                            rl0.setVisibility( View.VISIBLE );
                                                            fl1.setVisibility( View.INVISIBLE );


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

                                        rl1.setVisibility( View.INVISIBLE );
                                        rl0.setVisibility( View.VISIBLE );
                                        fl1.setVisibility( View.INVISIBLE );


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

    // count down timer for the running and the cycling
    private void timer() {
        Log.d( "fgfgf", "aqq2" );
        getSupportActionBar().hide();
        new CountDownTimer( 4000, 1000 ) {

            public void onTick(long millisUntilFinished) {
                timer.setText( "" + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                GPSsetting();
                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.VISIBLE );
                Log.d( "fdfd", "Fdfdfd" );
                trackingLocaton();
                buttonDisplaying();

            }
        }.start();

    }

    // trcking the locatio of the user,
    private void gettingCurrentLocation() {

        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener( this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Add a marker in Sydney and move the camera
                        originalLatLngfused = new LatLng( location.getLatitude(), location.getLongitude() );
                        originMarker = mMap.addMarker( new MarkerOptions().position( originalLatLngfused ).title( "Origin.." )
                                .icon( BitmapDescriptorFactory.
                                        defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) ) );
                        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( originalLatLngfused, 16.0f ) );

                        Log.d( "LAALALALAL", String.valueOf( location.getLatitude() ) );
                        Log.d( "LAALALALAL", String.valueOf( location.getLongitude() ) );
                    }

                }
            } );

        }
    }

    // live tracking user.
    private void trackingLocaton() {

        if (ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( FitnessSession.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d( "Faizabad", "Kam ho rha" );
                double lat = location.getLatitude();

                double lon = location.getLongitude();


                Log.d( "qqww", String.valueOf( lat ) );
                Log.d( "qqww", String.valueOf( lon ) );

                LatLng latlng = new LatLng( lat, lon );
                try {
                    storing.add( latlng );

                } catch (Exception e) {
                }


                Log.d( "SIZEEE", String.valueOf( storing.size() ) );

                try {

                    if (Checker == 0) {
  //                      originMarker.remove();
//                        originMarker2 = mMap.addMarker( new MarkerOptions().position( latlng ).title( "Origin.." )
//                                .icon( BitmapDescriptorFactory.
//                                        defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) ) );
//                        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latlng, 16.0f ) );
//
//                        Log.d( "qqww", "FOr Once Only" );

                    }
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();

                        Log.d( "qqww", "Remobved" );

                        if (Checker > 0) {
                            Location.distanceBetween( storing.get( Value )
                                    .latitude, storing.get( Value ).longitude, storing.get( storing.size() - 1 )
                                    .latitude, storing.get( storing.size() - 1 ).longitude, results );

                            Log.d( "Disstance", String.valueOf( results[0] ) );

                            totaldistnceCovered = totaldistnceCovered + results[0];

                            if (totaldistnceCovered > 1000) {
                                float dist = totaldistnceCovered / 1000;
                                String distancethreeleteres2 = totaldistnceCovered + "";

                                distance.setText( "" + distancethreeleteres2.substring( 0, 4 ) + " Km" );
                            } else {
                                String distancethreeleteres = totaldistnceCovered + "";
                                distance.setText( "" + distancethreeleteres.substring( 0, 5 ) + " meters" );

                            }


                        }

                        String url = getRequestUrl( storing.get( 0 ), storing.get( storing.size() - 1 ) );
                        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                        taskRequestDirections.execute( url );

                    }
                    mCurrLocationMarker = mMap.addMarker( new MarkerOptions()
                            .position( latlng ).title( "Moving.." )
                            .icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) ) );

                    Log.d( "Checker", String.valueOf( Checker ) );
                    Log.d( "Checker11", String.valueOf( storing.size() ) );

                    Checker++;
                    Value++;

                } catch (Exception e) {
                    Log.d( "qqww", "vvavavava" );
                    Log.d( "qqwwMsg", e.getMessage() );

                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        } );


    }

    // it is used to draw a marker line between the origin and current user location.
    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=walking";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }

    // request for directions.
    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL( reqUrl );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append( line );
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection( strings[0] );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute( s );
        }


        public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

            @Override
            protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
                JSONObject jsonObject = null;
                List<List<HashMap<String, String>>> routes = null;
                try {
                    jsonObject = new JSONObject( strings[0] );
                    DirectionParser directionsParser = new DirectionParser();
                    routes = directionsParser.parse( jsonObject );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return routes;
            }


            @Override
            protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
                //Get list route and display it into the map

                ArrayList points = null;

                PolylineOptions polylineOptions = null;

                try {
                    for (List<HashMap<String, String>> path : lists) {
                        points = new ArrayList();
                        polylineOptions = new PolylineOptions();

                        for (HashMap<String, String> point : path) {
                            double lat = Double.parseDouble( point.get( "lat" ) );
                            double lon = Double.parseDouble( point.get( "lon" ) );

                            points.add( new LatLng( lat, lon ) );
                        }

                        polylineOptions.addAll( points );
                        polylineOptions.width( 5 );
                        polylineOptions.color( Color.RED );
                        polylineOptions.geodesic( true );
                    }

                    if (polylineOptions != null) {
                        mMap.addPolyline( polylineOptions );
                    } else {
                        //      Toast.makeText( getApplicationContext(), "Need stable connection!", Toast.LENGTH_SHORT ).show();
                    }

                } catch (Exception e) {

                }


            }
        }


    }

    // method that will display all the information after running and cycling.
    private void StopTracking() {

        SnapShot();
        if (BooleanChecker) {
            image7.setVisibility( View.INVISIBLE );
            calorievalue.setVisibility( View.INVISIBLE );

            long diff = datestr2.getTime() - datestr.getTime();

            days = (int) (diff / (1000 * 60 * 60 * 24));
            hours = (int) ((diff - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (diff - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            seconds = min * 60;

            if (hours == 0) {
                timevalue.setText( min + " mints" );
            } else if (hours > 0) {
                timevalue.setText( hours + " hrs " + +min + " mints" );

            }

            speed = totaldistnceCovered / seconds;

            try {
                if (totaldistnceCovered > 1000) {
                    float dist = totaldistnceCovered / 1000;
                    String distancethreeleteres2 = totaldistnceCovered + "";

                    distancevalue.setText( "" + distancethreeleteres2.substring( 0, 4 ) + " Km" );
                } else {
                    String distancethreeleteres = totaldistnceCovered + "";
                    distancevalue.setText( "" + distancethreeleteres.substring( 0, 5 ) + " meters" );

                }

            } catch (Exception e) {
                distancevalue.setText( totaldistnceCovered + " meters" );

            }


            speedvalue.setText( speed + " m/sec" );


        } else {

            long diff = datestr2.getTime() - datestr.getTime();

            days = (int) (diff / (1000 * 60 * 60 * 24));
            hours = (int) ((diff - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (diff - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            seconds = min * 60;

            if (hours == 0) {
                timevalue.setText( min + " mints" );
            } else if (hours > 0) {
                timevalue.setText( hours + " hrs " + +min + " mints" );

            }

            speed = totaldistnceCovered / seconds;

            try {
                if (totaldistnceCovered > 1000) {
                    float dist = totaldistnceCovered / 1000;
                    String distancethreeleteres2 = totaldistnceCovered + "";

                    distancevalue.setText( "" + distancethreeleteres2.substring( 0, 4 ) + " Km" );
                } else {
                    String distancethreeleteres = totaldistnceCovered + "";
                    distancevalue.setText( "" + distancethreeleteres.substring( 0, 5 ) + " meters" );

                }

            } catch (Exception e) {
                distancevalue.setText( totaldistnceCovered + " meters" );

            }


            speedvalue.setText( speed + " m/sec" );


            double totalcaloriesburnt;
            // calorie Calculting.
            if (btnpressed.equals( "running" )) {
                Log.d( "QuettaGladiators", "ALALALALA" );
                Log.d( "dfdfdf", userweight );
                try {
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * Integer.parseInt( userweight ) * 1.036f;
                    Log.d( "dfdfdf", userweight );
                    Log.d( "dfdfdf", String.valueOf( totaldistnceCovered / 1000 ) );
                    Log.d( "dfdfdf", String.valueOf( totalcaloriesburnt ) );

                    calorievalue.setText( String.valueOf( totalcaloriesburnt ).substring( 0, 6 ) + " cal" );
                } catch (Exception e) {

                    String[] weightsep = userweight.split( "-" );
                    int weight = Integer.parseInt( weightsep[0] ) + Integer.parseInt( weightsep[1] ) / 2;
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * weight * 1.036f;
                    calorievalue.setText( String.valueOf( totalcaloriesburnt ).substring( 0, 6 ) + " cal" );
                    Log.d( "weightRanged", String.valueOf( weight ) );
                }

            } else if (btnpressed.equals( "cycling" )) {
                try {
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * Integer.parseInt( userweight ) * 1.050f;
                    calorievalue.setText( String.valueOf( totalcaloriesburnt ).substring( 0, 6 ) + " cal" );
                } catch (Exception e) {

                    String[] weightsep = userweight.split( "-" );
                    int weight = Integer.parseInt( weightsep[0] ) + Integer.parseInt( weightsep[1] ) / 2;
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * weight * 1.036f;
                    calorievalue.setText( String.valueOf( totalcaloriesburnt ).substring( 0, 6 ) + " cal" );
                }

            }

        }


    }

    // A method used for sending the data to the server , as well ending the activity.
    private void SendingData() {

        Log.d("Its here", "its working11");

        // if the user hasnt entered the plan , but want to do running , cycling .
        if (BooleanChecker) {
            Log.d("Its here", "its working1ee");

            rl0.setVisibility( View.VISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            fl1.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            getSupportActionBar().show();
        } else if (!BooleanChecker) {
            Log.d("Its here", "its working122");

            Log.d( "QQQQQQQQQQQ", btnpressed );
            Log.d( "LOL2DDSS", String.valueOf( fitnessId ) );
            Log.d( "LOL2", String.valueOf( btnpressed ) );
            Log.d( "LOL2", String.valueOf( min ) );
            Log.d( "LOL2", String.valueOf( speed ) );
            float cal = 12.2f;
            float totalcaloriesburnt = 0;
            Log.d( "LOLKings", String.valueOf( userweight ) );

            if (btnpressed.equals( "running" )) {
                Log.d( "dfdfdf", userweight );
                try {
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * Integer.parseInt( userweight ) * 1.036f;

                } catch (Exception e) {

                    String[] weightsep = userweight.split( "-" );
                    int weight = Integer.parseInt( weightsep[0] ) + Integer.parseInt( weightsep[1] ) / 2;
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * weight * 1.036f;

                    Log.d( "weightRanged", String.valueOf( weight ) );
                }

            } else if (btnpressed.equals( "cycling" )) {
                try {
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * Integer.parseInt( userweight ) * 1.050f;

                    Log.d( "CalorieBurnt", String.valueOf( totalcaloriesburnt ) );
                } catch (Exception e) {

                    String[] weightsep = userweight.split( "-" );
                    int weight = Integer.parseInt( weightsep[0] ) + Integer.parseInt( weightsep[1] ) / 2;
                    totalcaloriesburnt = (totaldistnceCovered / 1000) * weight * 1.036f;
                    Log.d( "CalorieBurnt", String.valueOf( totalcaloriesburnt ) );

                }

            }

            //Integer fitnessPlanId, float distanceCovered, float speed, float calorieBurnt, String sessionType, String description
            //Integer fitnessPlanId, Integer distanceCovered, Integer speed, Integer caloriesBurnt, String sessionType
            FitnessSessionSummary fssObject = new FitnessSessionSummary( fitnessId, totaldistnceCovered, speed, totalcaloriesburnt, btnpressed, "null" );

//            FitnessSessionSummary fssObject =
//                    new FitnessSessionSummary( fitnessId, 122.2f,
//                            3.3f, 44.3f, "running" );

            Call<FitnessSessionSummary> summary = api.fss( fssObject );
            summary.enqueue( new Callback<FitnessSessionSummary>() {
                @Override
                public void onResponse(Call<FitnessSessionSummary> call, Response<FitnessSessionSummary> response) {
                    Log.d( "LOLKings", String.valueOf( response ) );
                    Log.d( "LOqq", String.valueOf( fitnessId ) );
                    Log.d("Its here", "its working1223311");

                    currentDayresult( fitnessId );
                    rl0.setVisibility( View.INVISIBLE );
                    rl2.setVisibility( View.INVISIBLE );
                    rl1.setVisibility( View.VISIBLE );
                    fl1.setVisibility( View.INVISIBLE );
                    rl3.setVisibility( View.INVISIBLE );
                    getSupportActionBar().show();
                    dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_UNLOCKED );


                }

                @Override
                public void onFailure(Call<FitnessSessionSummary> call, Throwable t) {
                    Log.d( "LOLKings", String.valueOf( t.getMessage() ) );

                }
            } );

        }


    }

    // hiding the btn for some seconds
    private void buttonDisplaying() {

        new CountDownTimer( 80000, 1000 ) {

            public void onTick(long millisUntilFinished) {
                timer.setText( "" + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                stop.setVisibility( View.VISIBLE );

            }
        }.start();


    }

    // for deleting.
    private void AskingUser() {

        final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
        builder.setMessage( "Do you want to delete the Plan?" );
        builder.setCancelable( true );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Log.d( "Dfdff", String.valueOf( fitnessId ) );


                Call<DeletingPlan> calldelete = api.deletingplan( fitnessId, new DeletingPlan( fitnessId, "fitness" ) );
                calldelete.enqueue( new Callback<DeletingPlan>() {
                    @Override
                    public void onResponse(Call<DeletingPlan> call, Response<DeletingPlan> response) {
                        Log.d( "alalaqqq", String.valueOf( response ) );
                        Intent intent = new Intent( FitnessSession.this, MainActivity.class );
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

    private void sharingSession() {

        ndate.setText( current[0] + "/" + current[1] + "/" + current[2] );
        solution.setVisibility( View.VISIBLE );
        ndatetext.setVisibility( View.VISIBLE );
        ndate.setVisibility( View.VISIBLE );


        dfp.setVisibility( View.INVISIBLE );
        pname.setVisibility( View.INVISIBLE );
        st.setVisibility( View.INVISIBLE );
        weeks.setVisibility( View.INVISIBLE );
        view.setVisibility( View.INVISIBLE );
        weekstext.setVisibility( View.INVISIBLE );
        running.setVisibility( View.INVISIBLE );
        cycling.setVisibility( View.INVISIBLE );
        exer.setVisibility( View.INVISIBLE );
        aerobictext.setVisibility( View.INVISIBLE );
        runningtext.setVisibility( View.INVISIBLE );
        cyclingtext.setVisibility( View.INVISIBLE );
        startingtext.setVisibility( View.INVISIBLE );

        openingPictureDialog( rl1, "nosending" );


    }

    // for showing the result of the current day.
    private void currentDayresult(final Integer idpass) {
        Log.d("Its here", "its working1ffggffgg");

        Log.d( "DDLDLALALA", String.valueOf( idpass ) );
        calender = Calendar.getInstance(); //  current instance.
        df = new SimpleDateFormat( "yyyy-MMM-dd" ); // ormat
        formattedDate = df.format( calender.getTime() ); // getting time.
        current = formattedDate.split( "-" ); // seperqtor.

        checker = 0;
        presentingstatus = false; // if its true then the piechar will appear. otherwise no chart.


        FitnessSessionSummary ff = new FitnessSessionSummary( idpass );
        Call<FitnessAllSessions> summary = api.sessionresponse( idpass, ff );
        summary.enqueue( new Callback<FitnessAllSessions>() {
            @Override
            public void onResponse(Call<FitnessAllSessions> call, Response<FitnessAllSessions> response) {


                try {
                    List<FitnessSessionSummary> sessionsall = response.body().getData();


                    try {

                        UTC = String.valueOf( sessionsall.get( sessionsall.size() - 1 ).getStartTime() );
                        onlyDate = UTC.substring( 0, 10 );
                        utc = onlyDate.split( "-" ); // seperate.
                        //  now comparing days.


                        String g = "12";

                        if (current[2].equals( utc[2] )) {
                            share.setVisibility( View.VISIBLE );

                            if (!sessionsall.get( sessionsall.size() - 1 ).getSessionType().equals( "exercise" )) {
                                float distance = sessionsall.get( sessionsall.size() - 1 ).getDistanceCovered();
                                float time = sessionsall.get( sessionsall.size() - 1 ).getDistanceCovered() / sessionsall.get( sessionsall.size() - 1 ).getSpeed();

                                // km ki con
                                text00.setText( "" + sessionsall.get( sessionsall.size() - 1 ).getSessionType() );
                                // sec / mints ki condition

                                if (distance > 1000) {
                                    distance = distance / 1000;
                                    text1.setText( distance + " km" );
                                } else {
                                    String dd = "" + sessionsall.get( sessionsall.size() - 1 ).getDistanceCovered();
                                    text1.setText( dd.substring( 0, 6 ) + " meteres" );

                                }

                                if (time < 60) {
                                    text2.setText( String.valueOf( time ).substring( 0, 4 ) + " secs" );

                                } else {
                                    double newtime = time / 60;
                                    text2.setText( String.valueOf( newtime ).substring( 0, 6 ) + " mints" );
                                }


                                text3.setText( sessionsall.get( sessionsall.size() - 1 ).getSpeed() + " m/sec" );
                                text4.setText( sessionsall.get( sessionsall.size() - 1 ).getCaloriesBurnt() + " cal" );

                                text01.setVisibility( View.INVISIBLE );
                                text00.setVisibility( View.VISIBLE );
                                text0.setVisibility( View.VISIBLE );
                                text1.setVisibility( View.VISIBLE );
                                text2.setVisibility( View.VISIBLE );
                                text3.setVisibility( View.VISIBLE );
                                text3.setVisibility( View.VISIBLE );
                                text4.setVisibility( View.VISIBLE );
                                image1.setVisibility( View.VISIBLE );
                                image2.setVisibility( View.VISIBLE );
                                image3.setVisibility( View.VISIBLE );
                                image4.setVisibility( View.VISIBLE );
                                image5.setVisibility( View.INVISIBLE );
                                image6.setVisibility( View.INVISIBLE );
                            } else {
                                Log.d( "DDLDLALALA", String.valueOf( idpass ) + " ee" );

                                text0.setVisibility( View.VISIBLE );
                                text01.setVisibility( View.INVISIBLE );

                                text00.setVisibility( View.VISIBLE );
                                text00.setText( "" + sessionsall.get( sessionsall.size() - 1 ).getSessionType() );

                                text1.setText( sessionsall.get( sessionsall.size() - 1 ).getDescription() );
                                text2.setText( sessionsall.get( sessionsall.size() - 1 ).getCaloriesBurnt() + " cal" );
                                image5.setVisibility( View.VISIBLE );
                                image6.setVisibility( View.VISIBLE );
                                text1.setVisibility( View.VISIBLE );
                                text2.setVisibility( View.VISIBLE );
                                text3.setVisibility( View.INVISIBLE );
                                text4.setVisibility( View.INVISIBLE );
                                image1.setVisibility( View.INVISIBLE );
                                image2.setVisibility( View.INVISIBLE );
                                image3.setVisibility( View.INVISIBLE );
                                image4.setVisibility( View.INVISIBLE );

                            }
                        } else {
                            text01.setVisibility( View.VISIBLE );
                        }

                    } catch (NullPointerException e) {
                        text01.setVisibility( View.VISIBLE );
                    }

                } catch (Exception e) {

                    text01.setVisibility( View.VISIBLE );
                }

            }


            @Override
            public void onFailure(Call<FitnessAllSessions> call, Throwable t) {
                Log.d( "QALKINGS", t.getMessage() );
            }
        } );


    }

    // fetching aerobic exercise list.
    private void Fetchinglist() {

        dialogproces=new ProgressDialog(FitnessSession.this);
        dialogproces.setCancelable( false );
        dialogproces.setMessage( "Loading..." );
        dialogproces.show();

        Call<AerobicResponse> summary = api.aerobicexercises();
        summary.enqueue( new Callback<AerobicResponse>() {


            @Override
            public void onResponse(Call<AerobicResponse> call, Response<AerobicResponse> response) {
                Log.d( "resr", String.valueOf( response ) );

                exerlist = (ArrayList<String>) response.body().getData();
                Log.d( "qwsize", String.valueOf( exerlist ) );
                Log.d( "qwsize", String.valueOf( exerlist.size() ) );
//
//                exerlist.remove( exerlist.get( 3 ) );
//                exerlist.remove( exerlist.get( 3 ) );
//                exerlist.remove( exerlist.get( 5 ) );
//                exerlist.remove( exerlist.get( 7 ) );
//                exerlist.remove( exerlist.get( 7 ) );
//                exerlist.remove( exerlist.get( 7 ) );
                Log.d( "qwsize", String.valueOf( exerlist ) );
                Log.d( "qwsize", String.valueOf( exerlist.size() ) );

                adapter = new ArrayAdapter<String>( FitnessSession.this, R.layout.fooditemname, R.id.itemname, exerlist );
                listView.setAdapter( adapter );
                dialogproces.dismiss();
                rl4.setVisibility( View.VISIBLE );

            }

            @Override
            public void onFailure(Call<AerobicResponse> call, Throwable t) {
                Log.d( "resr", String.valueOf( t.getMessage() ) );

            }
        } );


    }

    // selecting a category from the list of the aerobic exercises.

    private void SelectingExerciseCategory() {

        Log.d( "rochi", "dafugdfdfdfdfdfdfdfdfd" );
        Log.d( "rochi", String.valueOf( fitnessId ) );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = exerlist.indexOf( adapter.getItem( i ) );

                Log.d( "rochi", exerlist.get( position ) + "dfdfdfdfdfdfdfdfd" );

                rl0.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );
                rl5.setVisibility( View.VISIBLE );

                selectedcategryname = exerlist.get( position );
                FetchingSubCategory( exerlist.get( position ) );
            }
        } );
    }

    // fetching sub categories of the specific cateogry and then displaying their animations.
    private void FetchingSubCategory(final String name) {


        Log.d( "dfderrer", selectedcategryname );

        Log.d( "ssssssaaqqqq", name );
        AerobicCategoryName cn = new AerobicCategoryName( name );

        Call<AerobicExercisesOuterResponse> summary = api.aer( cn );
        summary.enqueue( new Callback<AerobicExercisesOuterResponse>() {


            @Override
            public void onResponse(Call<AerobicExercisesOuterResponse> call, Response<AerobicExercisesOuterResponse> response) {
                Log.d( "resrww", String.valueOf( response ) );

                List<AerobicExercisesCategoryList> list = response.body().getData();


Log.d( "sdfdsfsd","sdsdfwerew" );
Log.d( "sdfdsfsd", String.valueOf( list.size() ) );
                for (int i = 0; i < list.size(); i++) {
                    newArray = new ArrayList<CategoryAerobicGif>();
                    for (int h = 0; h < 1; h++) {
                        Log.d( "dsd", "me idher goon" );

                        //  newArray.add( new CategoryAerobicGif("https://media.giphy.com/media/52EXW7hbQkF0s08LH5/giphy.gif" ));
                        newArray.add( new CategoryAerobicGif( list.get( i ).getImageLink() ) );

                    }
                    CategoryAerobic ca = new CategoryAerobic( list.get( i ).getCategorySubcategory(), newArray );
                    categoryAerobics.add( ca );

                    Log.d( "dfdfdOOO", String.valueOf( newArray.size() ) );

                }


                listAdapter = new SubCategoryAdapter( FitnessSession.this, categoryAerobics, name ); // passing the context of the activity and the main array list.
                //attach the adapter to the list
                expandableListView.setAdapter( listAdapter );
             //   expandAll();


            }

            @Override
            public void onFailure(Call<AerobicExercisesOuterResponse> call, Throwable t) {
                Log.d( "resr", String.valueOf( t.getMessage() ) );

            }
        } );


    }

    // selsection action result.
    public boolean onOptionsItemSelected(MenuItem menu) {

        if (drawerToggle.onOptionsItemSelected( menu )) {
            return true;
        }
        return super.onOptionsItemSelected( menu );
    }

    // intializing the menu items of the navigation bar.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.runningmenu: {
                dlayout.closeDrawer( Gravity.LEFT );
                GPSsetting();
                datestr = new Date();
                sdf = new SimpleDateFormat( "hh:mm a" );
                convertedDate1 = sdf.format( datestr );  // starting time of the session.

                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                storing = new ArrayList<>();

                BooleanChecker = true;
                timer();
                break;
            }
            case R.id.cyclingmenu: {
                GPSsetting();
                dlayout.closeDrawer( Gravity.LEFT );
                datestr = new Date();
                sdf = new SimpleDateFormat( "hh:mm a" );
                convertedDate1 = sdf.format( datestr );  // starting time of the session.

                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.VISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                storing = new ArrayList<>();

                BooleanChecker = true;
                timer();
                break;
            }

            case R.id.reportsfit: {
                Intent intent = new Intent( FitnessSession.this, ReportsFitness.class );
                String combo = fitnessId + ";" + "running";
                intent.putExtra( "btnpreseed", combo );
                startActivity( intent );
                break;
            }

            case R.id.reportsfit1: {
                Intent intent = new Intent( FitnessSession.this, ReportsFitness.class );
                String combo = fitnessId + ";" + "cycling";

                intent.putExtra( "btnpreseed", combo );
                startActivity( intent );
                break;
            }

            case R.id.reportsfit2: {
                Intent intent = new Intent( FitnessSession.this, ReportsFitness.class );
                String combo = fitnessId + ";" + "exercise";

                intent.putExtra( "btnpreseed", combo );
                startActivity( intent );
                break;
            }

            case R.id.logoutfit: {
                auth.signOut();
                finish();
                startActivity( new Intent( this, StartingActivity.class ) );

                break;
            }


        }
        return false;
    }

    // expanding the expandible listview.
    private void expandAll() {
        int count = listAdapter.getGroupCount(); // fetching the size of the main list.
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup( i );
        }
    }

    // the count down timer for the aerobic exercies.
    private void ExerciseTimer() {
        rl0.setVisibility( View.INVISIBLE );
        rl1.setVisibility( View.INVISIBLE );
        rl2.setVisibility( View.VISIBLE );
        rl3.setVisibility( View.INVISIBLE );
        fl1.setVisibility( View.INVISIBLE );
        rl4.setVisibility( View.INVISIBLE );
        rl5.setVisibility( View.INVISIBLE );
        rl6.setVisibility( View.INVISIBLE );
        Log.d( "DFdf", "Fd" );
        getSupportActionBar().hide();
        new CountDownTimer( 4000, 1000 ) {

            public void onTick(long millisUntilFinished) {
                timer.setText( "" + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                rl0.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );
                rl5.setVisibility( View.INVISIBLE );
                rl6.setVisibility( View.VISIBLE );
                aerostart.setVisibility( View.INVISIBLE );
                aerbicExercise.setVisibility( View.VISIBLE );
                displayinggif.setVisibility( View.VISIBLE );
                stopwatch.setVisibility( View.VISIBLE );
                aerobicexercisetitle.setVisibility( View.VISIBLE );
                displayingExerciseTime();
                hidingAeroEnd();
                Log.d( "Party", "PARTY" );
            }
        }.start();

    }

    // the stop watch used for the aerobic exerises .
    private void displayingExerciseTime() {

        starttime = SystemClock.uptimeMillis();
        stopwatchhandler.postDelayed( stopwatchrunnable, 0 );
    }

    // ending the aerobic exercise session.
    private void EndingAerobicsExercises() {
        final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
        builder.setMessage( "Do you want to end this session?" );
        builder.setCancelable( true );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CalculatingCalorieForAerobicExercises();
            }

        } );
        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stopwatch.setVisibility( View.VISIBLE );
                stopwatch2.setVisibility( View.INVISIBLE );
                starttime = SystemClock.uptimeMillis();
                stopwatchhandler.postDelayed( stopwatchrunnable, 0 );

                dialogInterface.cancel();


            }
        } );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        //////////////////////////////////////

//        final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessSession.this );
//        builder.setMessage( "Do you want to perform another exercise?" );
//        builder.setCancelable( true );
//
//        // in this case the user wanted to update the height and weight.
//        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                CalculatingCalorieForAerobicExercises();
//                Log.d( "Dfdfdfwww", "horeya" );
//                rl0.setVisibility( View.INVISIBLE );
//                rl1.setVisibility( View.INVISIBLE );
//                rl2.setVisibility( View.INVISIBLE );
//                rl3.setVisibility( View.INVISIBLE );
//                fl1.setVisibility( View.INVISIBLE );
//                rl4.setVisibility( View.VISIBLE );
//                rl5.setVisibility( View.INVISIBLE );
//                rl6.setVisibility( View.INVISIBLE );
//                Fetchinglist();
//            }
//
//        } );
//        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                CalculatingCalorieForAerobicExercises();
//                ///////////////////////////////////////
//                ///////////////////////
//            }
//        } );
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        ////////////////////////////////////////////


        ///////////////////////////////////////////
    }

    // getting the calories from the server and further using it to insert in the fitness sesssion.
    private void GettingCalories(SendingAerobicExerciseData dd) {
        Call<SendingAerobicExerciseData> cc = api.sae( dd );
        cc.enqueue( new Callback<SendingAerobicExerciseData>() {
            @Override
            public void onResponse(Call<SendingAerobicExerciseData> call, Response<SendingAerobicExerciseData> response) {
                Log.d( "xxxxxx", String.valueOf( response ) );
                Log.d( "fdfwwoqoqoq", String.valueOf( id ) );
                Log.d( "fdfwwoqoqoq", String.valueOf( anotheruserid ) );
                Log.d( "gfgfgfgfgfgfLast", String.valueOf( response.body().getData().getCalories() ) );
                final float calories = response.body().getData().getCalories();

                ///
                Log.d( "fdfwwoqoqoq", String.valueOf( anotheruserid ) );

                Call<FitnessResponseObject> call2 = api.checkingfitnessplan( Integer.parseInt( anotheruserid ) );
                call2.enqueue( new Callback<FitnessResponseObject>() {
                    @Override
                    public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                        Log.d( "SDsdsds", String.valueOf( response ) );
                        anotherfitnessid = response.body().getData().getFitnessPlanId();
                        pname.setText( response.body().getData().getName() );
                        weeks.setText( response.body().getData().getWeeks() + "" );
                        String date = response.body().getData().getDate();
                        date = date.substring( 0, 10 );
                        String[] seperator = date.split( "-" );

                        st.setText( seperator[2] + "/" + seperator[1] + "/" + seperator[0] );


                        FitnessSessionSummary fss = new FitnessSessionSummary( response.body().getData().getFitnessPlanId(), calories,
                                "exercise", aerobicsplit[1] );
                        CreatingFitnessSession( fss );

                    }

                    @Override
                    public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                    }
                } );
////

            }

            @Override
            public void onFailure(Call<SendingAerobicExerciseData> call, Throwable t) {
                Log.d( "xxxxxx", String.valueOf( t.getMessage() ) );

            }
        } );

    }

    // making an object which will be used to calculate the calories of aerobic exercise
    private void CalculatingCalorieForAerobicExercises() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();


        Log.d( "dfdfdssssss", String.valueOf( email ) );


        call = api.fetchingUser( email );


        call.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                Log.d( "Logdfdfdfdfdf", response.body().getData().getWeight() );

                anotheruserid = response.body().getData().getMemberId() + "";
                Log.d( "wwwqqqqqqttt", String.valueOf( anotheruserid ) );
                String timeexercise = stopwatch2.getText().toString();
                String[] splitingTime = timeexercise.split( ":" );

                if (splitingTime[0].equals( "0" )) {
                    Log.d( "seconds", "Seconds " + splitingTime[1] );
                } else {
                    Log.d( "seconds", "Mints: " + splitingTime[0] + " Seconds: " + splitingTime[1] );


                }

//                        SendingAerobicExerciseData ad = new SendingAerobicExerciseData(aerobicsplit[3], Integer.parseInt( response.body().getData().getWeight() ),
//                              Integer.parseInt(  splitingTime[0]) ) ;

                try {
                    SendingAerobicExerciseData ad = new SendingAerobicExerciseData( aerobicsplit[3], Integer.parseInt( response.body().getData().getWeight() ),
                            Integer.parseInt( splitingTime[0] ) );

//                    SendingAerobicExerciseData ad = new SendingAerobicExerciseData( aerobicsplit[3], Integer.parseInt( response.body().getData().getWeight() ),
//                            12 );
                    Log.d( "aerobics", splitingTime[0] );
                    Log.d( "aerobics", aerobicsplit[3] );
                    Log.d( "aerobics", response.body().getData().getWeight() );
                    GettingCalories( ad );
                    Log.d( "dfderrer", aerobicsplit[3] );

                } catch (NumberFormatException e) {
                    String rangeweight = response.body().getData().getWeight();
                    String[] weightsep = rangeweight.split( "-" );
                    int weight = Integer.parseInt( weightsep[0] ) + Integer.parseInt( weightsep[1] ) / 2;
                    Log.d( "weightRanged", String.valueOf( weight ) );
                    SendingAerobicExerciseData ad = new SendingAerobicExerciseData( aerobicsplit[3], weight,
                            12 );
                    GettingCalories( ad );

                }

            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }

        } );


    }

    // insering the aerobic exercises fitness sesssion,.
    private void CreatingFitnessSession(FitnessSessionSummary fssObject) {


        Call<FitnessSessionSummary> summary = api.fss( fssObject );
        summary.enqueue( new Callback<FitnessSessionSummary>() {
            @Override
            public void onResponse(Call<FitnessSessionSummary> call, Response<FitnessSessionSummary> response) {
                Log.d( "LOLKings", String.valueOf( response ) );
                Log.d( "LOqq", String.valueOf( anotherfitnessid ) );
                Log.d( "LOqq", String.valueOf( motherfucker ) );

                currentDayresult( anotherfitnessid );
                rl0.setVisibility( View.INVISIBLE );
                rl2.setVisibility( View.INVISIBLE );
                rl1.setVisibility( View.VISIBLE );
                fl1.setVisibility( View.INVISIBLE );
                rl3.setVisibility( View.INVISIBLE );
                rl4.setVisibility( View.INVISIBLE );
                rl5.setVisibility( View.INVISIBLE );
                rl6.setVisibility( View.INVISIBLE );
                getSupportActionBar().show();
                dlayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_UNLOCKED );


            }

            @Override
            public void onFailure(Call<FitnessSessionSummary> call, Throwable t) {
                Log.d( "LOLKings", String.valueOf( t.getMessage() ) );

            }
        } );


    }


    public Runnable stopwatchrunnable = new Runnable() {

        public void run() {
            timemilliseconds = SystemClock.uptimeMillis() - starttime;

            updateTime = timeSwafbuff + timemilliseconds;

            Seconds = (int) (updateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (updateTime % 1000);

            stopwatch.setText( "" + Minutes + ":"
                    + String.format( "%02d", Seconds ) + ":"
                    + String.format( "%03d", MilliSeconds ) );

            stopwatchhandler.postDelayed( this, 0 );
        }

    };

    private void GPSsetting() {
        boolean GpsStatus = locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER );

        if (GpsStatus == true) {

        } else {
            Intent intent1 = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
            startActivity( intent1 );

        }

    }

    private void SnapShot() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                bitmap = snapshot;
                try {
                    FileOutputStream out = new FileOutputStream( "sdcard/Download/LifeLine"+ ".png" );
                    bitmap.compress( Bitmap.CompressFormat.PNG, 90, out );
                    Log.d( "Olalal", "ff" );
                    Toast.makeText( FitnessSession.this, "Map Captured .", Toast.LENGTH_LONG ).show();
                } catch (Exception e) {
                    Log.d( "Olalal", "ffqqqq" );
                    Log.d( "Olalal", e.getMessage() );


                    e.printStackTrace();
                }
            }
        };

        mMap.snapshot( callback );

    }

    private void hidingAeroEnd() {


        new CountDownTimer( 70000, 1000 ) {

            public void onTick(long millisUntilFinished) {
                //    timer.setText( "" + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                endeuro.setVisibility( View.VISIBLE );

            }
        }.start();


    }

    @Override
    public void onBackPressed() {
//        rl1 = (RelativeLayout) findViewById( R.id.layout1 );
//        rl0 = (RelativeLayout) findViewById( R.id.layout0 );
//        rl2 = (RelativeLayout) findViewById( R.id.timerlayout );
//        fl1 = (FrameLayout) findViewById( R.id.layout2 );
//        rl3 = (RelateiveLayout) findViewById( R.id.result );
//        rl4 = (RelativeLayout) findViewById( R.id.layout4 );
//        rl5 = (RelativeLayout) findViewById( R.id.layout5 );
//        rl6 = (RelativeLayout) findViewById( R.id.layout6 );
//
/// rl0 ---> when nothing is there,
//        // rl1 when the basic info of the session is there
//        // rl2 when the timer goes on ,
//        // fl1 displaying map.
//        // rl3---> for showing result.

        Log.d( "LAYOUTVISIBILITY", String.valueOf( rl2.getVisibility() ) );


        if (rl0.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent( FitnessSession.this, MainActivity.class );
            startActivity( intent );
            finish();
            Log.d( "fgfgfg", "fgfgfBLALALAALALALAL" );
            Log.d( "dfdfwwXXwqq", "dfdfA:A:A:::::" );

        } else if (rl1.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent( FitnessSession.this, MainActivity.class );
            startActivity( intent );
            finish();
            Log.d( "fgfgfg", "fgfgfBLALALAALALALAL" );
            Log.d( "dfdfwwXXwqq", "dfdfA:A:A:::::" );

        } else if (rl4.getVisibility() == View.VISIBLE) {
            rl1.setVisibility( View.VISIBLE );

            rl0.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl4.setVisibility( View.INVISIBLE );
            rl5.setVisibility( View.INVISIBLE );
            rl6.setVisibility( View.INVISIBLE );
            fl1.setVisibility( View.INVISIBLE );

        } else if (rl5.getVisibility() == View.VISIBLE) {
            categoryAerobics.clear();
            rl4.setVisibility( View.VISIBLE );

            rl0.setVisibility( View.INVISIBLE );
            rl2.setVisibility( View.INVISIBLE );
            rl3.setVisibility( View.INVISIBLE );
            rl1.setVisibility( View.INVISIBLE );
            rl5.setVisibility( View.INVISIBLE );
            rl6.setVisibility( View.INVISIBLE );
            fl1.setVisibility( View.INVISIBLE );

        } else if (rl6.getVisibility() == View.VISIBLE) {
            Log.d( "dfdfwwXXwqq", "dfdfA:A:A:" );

        } else {

        }
    }

    private void openingPictureDialog(View v, final String state) {
        dialog = new Dialog( FitnessSession.this );
        builder = new AlertDialog.Builder( FitnessSession.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View customView = inflater.inflate( R.layout.picturestatus, null );
        // reference the textview of custom_popup_dialog
        ImageView imageView = (ImageView) customView.findViewById( R.id.imageviewpic );
        Button uploadButton = (Button) customView.findViewById( R.id.uploadphoto );
        Button shareButton = (Button) customView.findViewById( R.id.postthestatus );
        final EditText editText = (EditText) customView.findViewById( R.id.textshare );

        imageView.setVisibility( View.VISIBLE );
        uploadButton.setVisibility( View.INVISIBLE );
        shareButton.setVisibility( View.VISIBLE );


        // uploading the photo in the dialog imagebox.
        final Bitmap b = ScreenShot.takeScreenshotOfRootView( v );
        imageView.setImageBitmap( b );
        Log.d( "ALALALLA", String.valueOf( b ) );

        shareButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals( "sending" )) {
                    SendingData();
                    uploadingPhoto( b, editText.getText().toString() );
                } else {
                    uploadingPhoto( b, editText.getText().toString() );
                }

            }
        } );


        builder.setTitle( "Share What's in your mind?" );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    private void uploadingPhoto(Bitmap b, String text) {
        try {
            path2 = "sdcard/Download/LifeLine" + ".png";
            FileOutputStream out = new FileOutputStream( path2 );
            b.compress( Bitmap.CompressFormat.PNG, 90, out );

            Log.d( "Olalal", "ff" );
        } catch (Exception e) {
            Log.d( "Olalal", "ffqqqq" );
            Log.d( "Olalal", e.getMessage() );


            e.printStackTrace();

        }

        File file = new File( path2 );

        RequestBody mFile = RequestBody.create( MediaType.parse( "image/*" ), file );
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData( "picture", file.getName(), mFile );
        RequestBody filename = RequestBody.create( MediaType.parse( "text/plain" ), file.getName() );
        uploadingtoserver( fileToUpload, filename, text );

    }

    private void uploadingtoserver(MultipartBody.Part fileToUpload, RequestBody filename, final String text) {
        Call<UploadObject> fileUpload = uploadImage.uploadFile( fileToUpload, filename );

        fileUpload.enqueue( new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {

                Log.d( "ALALALAAL", String.valueOf( response.body().getData() ) );
                Log.d( "ALALALAAL", String.valueOf( id ) );
                Log.d( "ALALALAAL", String.valueOf( text ) );
                Toast.makeText( getApplicationContext(), "Response ", Toast.LENGTH_LONG ).show();
                Toast.makeText( getApplicationContext(), id + ";" + text, Toast.LENGTH_LONG ).show();
                //   Glide.with( UserProfile.this ).load( response.body().getData()).into( userdp );


                uploadingStatus( id, response.body().getData(), text );
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                Log.d( "dfdf", "ErrorALALALALALALALLA " + t.getMessage() );
            }
        } );
    }

    private void uploadingStatus(final Integer memberId, final String imagelink, final String text) {
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
                        startActivity( new Intent( FitnessSession.this, FitnessSession.class ) );
                        Toast.makeText( FitnessSession.this, "Status Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

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