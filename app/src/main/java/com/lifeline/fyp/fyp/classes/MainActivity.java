package com.lifeline.fyp.fyp.classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.fitness.FitnessHome;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.models.RemainingNewsFeed;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.NewsFeedOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.PostsDetail;
import com.lifeline.fyp.fyp.socialMedia.Allnotification;
import com.lifeline.fyp.fyp.socialMedia.FetchingNewsFeedData;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, SwipyRefreshLayout.OnRefreshListener {

    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    private TextView useremail, username;
    private Toolbar toolbar;
    ProgressDialog dialogproces;
    private int statuscode;
    CircleImageView userimage;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    Api api;
    RelativeLayout rl1, rl2;
    private RecyclerView newsfeed;
    private RecyclerView.Adapter newsfeedadapter; // skinadapter
    private EndlessScrollListener scrollListener;
    ArrayList<FetchingNewsFeedData> fetchingNewsFeedData;
    private String sc;
    private String userid;
    private ImageButton home;
    TextView fetchinginfo;
    private ImageButton profile;
    LinearLayoutManager dpm;
    Integer memberId;
    SwipeRefreshLayout swipe;


    // SwipyRefreshLayout swipyRefreshLayout;


    private String email; // checking used to store the facetype which will be used for checking for different if's.
    //height checker is used for tracking the clothing module availablity.
    // combination is used to fetch the age and the gender of the signed in user. // checking is used to check whether the additional fields are filled or not.
    private NavigationView mNavigationview;
    CallbackManager callbackManager;


// new Variables.

    Integer id;


    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate( savedInstanceState );
        FacebookSdk.sdkInitialize( this.getApplicationContext() );
        setContentView( R.layout.activity_main );
        getSupportActionBar().setElevation( 0 );

      //  startService( new Intent( this, NotificationService.class ) );
// Create a GestureDetector
        dialogproces = new ProgressDialog( MainActivity.this );
        dialogproces.setCancelable( false );
        dialogproces.setMessage( "Loading..." );
        dialogproces.show();

        swipe = (SwipeRefreshLayout) findViewById( R.id.swipelayout );
        swipe.setOnRefreshListener( this );
        newsfeed = (RecyclerView) findViewById( R.id.newsfeed );
        dpm = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        // intialzing,
        home = (ImageButton) findViewById( R.id.home1 );
        home.setEnabled( false );
        home.setClickable( false );
        fetchinginfo = (TextView) findViewById( R.id.fetchinginfo );
        callbackManager = CallbackManager.Factory.create();


        Log.d("ALAALALALAL", FirebaseInstanceId.getInstance().getToken());
        ////////
        newsfeed.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                Log.d( "asfwaewewewzzzX1", String.valueOf( dy ) );
                Log.d( "asfwaewewewzzzY1", String.valueOf( dx ) );
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = dpm.getChildCount();
                    totalItemCount = dpm.getItemCount();
                    pastVisiblesItems = dpm.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            String details = (String) fetchinginfo.getText();
                            String[] seperator = details.split( ";" );


                            if (!seperator[0].equals( "0" )) {
                                RemainingNewsFeed remainingNewsFeedall = new RemainingNewsFeed( Integer.parseInt( seperator[2] )
                                        , Integer.parseInt( seperator[0] ), seperator[1] );

                                final ProgressDialog dialog = new ProgressDialog( MainActivity.this );
                                dialog.setCancelable( false );
                                dialog.setMessage( "Loading..." );
                                dialog.show();

                                //Do pagination.. i.e. fetch new data

                                Call<NewsFeedOuterResponse> remainingNewsFeed = api.newsfeed2( remainingNewsFeedall );
                                remainingNewsFeed.enqueue( new Callback<NewsFeedOuterResponse>() {
                                    @Override
                                    public void onResponse(Call<NewsFeedOuterResponse> call, Response<NewsFeedOuterResponse> response) {
                                        ArrayList<PostsDetail> newsFeedOuterResponses = response.body().getData().getPosts();
                                        Log.d( "Dfdfdfdfwewewe", String.valueOf( response.body().getData().getPosts().get( 0 ).getPostcollected().getHasImage() ) );
                                        //       Log.d( "Dfdfdfdfwewewe", String.valueOf( response.body().getData().getPosts().get( 1 ).getLastCommentposted().getContent() ) );

                                        // 1. image and comment.
                                        // 2. image but no commnent.
                                        // 3. no image but comment.
                                        //4. no image , no comment.
                                        String commentstatus;
                                        for (int i = 0; i < newsFeedOuterResponses.size(); i++) {
                                            commentstatus = response.body().getData().getPosts().get( i ).getLastCommentposted() + "";


// image hai , comment nahi hai.
                                            if ((response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                                    && (commentstatus.equals( "null" ))) {

                                                fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                                                response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getPictureLink(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.PICTURE, response.body().getData().getPosts().get( i ).isHasliked() ) );

                                            } // end if

// image hai , comment hai.
                                            else if ((response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                                    && !(commentstatus.equals( "null" ))) {
                                                fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                                                response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getPictureLink(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getTime(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getContent(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getTime(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getUsername(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.PICTURE, response.body().getData().getPosts().get( i ).isHasliked() ) );
                                            } // end if


// image nahi hai , commenr hai.
                                            else if (!(response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                                    && !(commentstatus.equals( "null" ))) {
                                                fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                                                response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getTime(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getContent(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getTime(),
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getUsername()
                                                        ,
                                                        response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.TEXT, response.body().getData().getPosts().get( i ).isHasliked() ) );

                                            } // end if

// image nahi hai and connent b nahi hai.
                                            else if (!(response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                                    && (commentstatus.equals( "null" ))) {

                                                fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                                                response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                                        response.body().getData().getPosts().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.TEXT, response.body().getData().getPosts().get( i ).isHasliked() ) );
                                            }


                                            newsfeedadapter = new NewsFeedAdapter( fetchingNewsFeedData, MainActivity.this, memberId, "main" );
                                            NewsFeedAdapter.itemList = fetchingNewsFeedData;
//  newsfeed.setAdapter( newsfeedadapter );
//                                        newsfeed.setLayoutManager( new LinearLayoutManager( MainActivity.this, LinearLayoutManager.VERTICAL, false ) );

                                            newsfeed.getAdapter().notifyDataSetChanged();
                                            dialog.hide();
//                                        // end if

                                            Log.d( "SDFsdfsdt", "Kuch ho rha hai" );

                                        }

                                        Log.d( ":SDfsdfsdf", String.valueOf( fetchingNewsFeedData.size() ) );

//
                                    }

                                    @Override
                                    public void onFailure(Call<NewsFeedOuterResponse> call, Throwable t) {

                                    }
                                } );

                                ////

                                /////////

                            }
                        }


                    }
                }
                //////////,,,,,,,,,,,///////////////////
            }
        } );
        profile = (ImageButton) findViewById( R.id.profile );

        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( MainActivity.this, UserProfile.class ) );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

            }
        } );

        // inflating the navigation header.
        mNavigationview = (NavigationView) findViewById( R.id.navmenu );
        View header = mNavigationview.getHeaderView( 0 );

        useremail = (TextView) header.findViewById( R.id.currentuser );
        username = (TextView) header.findViewById( R.id.activeusername );
        userimage = (CircleImageView) header.findViewById( R.id.userimage );
        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // setting the sidenav textfields.
        useremail.setText( email );

        if (auth.getCurrentUser() == null) {
            finish();
            startActivity( new Intent( getApplicationContext(), StartingActivity.class ) );
        }
        //setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );


        fetchingLoginUserDetail( email );
        // setting Drawlable layout
        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( MainActivity.this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview.setNavigationItemSelectedListener( this );
    }


    private void fetchingLoginUserDetail(String email) {

        Call<MemberObject> call = api.fetchingUser( email );


        call.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                if (response.isSuccessful()) {
                    Log.d( "messi", String.valueOf( response ) );
                    Log.d( "messi", String.valueOf( response.body() ) );
                    statuscode = response.code();
                    sc = "" + statuscode;
                }

                Log.d( "statuscodeeror", String.valueOf( statuscode ) );


                try {
                    memberId = response.body().getData().getMemberId();
                    userid = response.body().getData().getMemberId().toString();
                    username.setText( response.body().getData().getFirstName() + " " + response.body().getData().getLastName() );
                    Glide.with( MainActivity.this ).load( response.body().getData().getProfilePicture() ).into( userimage );
                    gettingNewsFeed( response.body().getData().getMemberId() );

                } catch (NullPointerException e) {
                    username.setText( "" );
                    userimage.setImageResource( R.drawable.user );
                    gettingNewsFeed( response.body().getData().getMemberId() );
                }

            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

                Toast.makeText( MainActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
            }
        } );


    }

    private void gettingNewsFeed(final Integer memberId) {
        fetchingNewsFeedData = new ArrayList<>();

        if (swipe.isRefreshing()) {
            swipe.setRefreshing( false );
        }

        final Call<NewsFeedOuterResponse> newsFeedOuterResponseCall = api.newsfeed( memberId );
        newsFeedOuterResponseCall.enqueue( new Callback<NewsFeedOuterResponse>() {
            @Override

            public void onResponse(Call<NewsFeedOuterResponse> call, Response<NewsFeedOuterResponse> response) {

                try {
                    fetchinginfo.setVisibility( View.INVISIBLE );
                    fetchinginfo.setText( response.body().getData().getNextIndex() + ";" + response.body().getData().getStartTimeSpan() + ";" + memberId );
                    ArrayList<PostsDetail> newsFeedOuterResponses = response.body().getData().getPosts();
                    //       Log.d( "Dfdfdfdfwewewe", String.valueOf( response.body().getData().getPosts().get( 1 ).getLastCommentposted().getContent() ) );

                    // 1. image and comment.
                    // 2. image but no commnent.
                    // 3. no image but comment.
                    //4. no image , no comment.
                    String commentstatus;
                    for (int i = 0; i < newsFeedOuterResponses.size(); i++) {
                        commentstatus = response.body().getData().getPosts().get( i ).getLastCommentposted() + "";


// image hai , comment nahi hai.
                        if ((response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                && (commentstatus.equals( "null" ))) {

                            fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getPictureLink(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.PICTURE,
                                    response.body().getData().getPosts().get( i ).isHasliked() ) );

                        } // end if

// image hai , comment hai.
                        else if ((response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                && !(commentstatus.equals( "null" ))) {
                            fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getPictureLink(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getTime(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getContent(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getTime(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getUsername(),

                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.PICTURE, response.body().getData().getPosts().get( i ).isHasliked() ) );
                        } // end if


// image nahi hai , commenr hai.
                        else if (!(response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                && !(commentstatus.equals( "null" ))) {
                            fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getTime(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getContent(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getTime(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getUsername(),
                                    response.body().getData().getPosts().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.TEXT, response.body().getData().getPosts().get( i ).isHasliked() ) );

                        } // end if

// image nahi hai and connent b nahi hai.
                        else if (!(response.body().getData().getPosts().get( i ).getPostcollected().getHasImage())
                                && (commentstatus.equals( "null" ))) {

                            fetchingNewsFeedData.add( new FetchingNewsFeedData( response.body().getData().getPosts().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().getPosts().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getContent(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().getPosts().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.TEXT, response.body().getData().getPosts().get( i ).isHasliked() ) );

                        }

                        newsfeedadapter = new NewsFeedAdapter( fetchingNewsFeedData, MainActivity.this, memberId, "main" );
                        newsfeed.setAdapter( newsfeedadapter );
                        newsfeed.setLayoutManager( dpm );
                        dialogproces.hide();
                        // end if

                    }


                } catch (Exception e) {
                    fetchinginfo.setVisibility( View.VISIBLE );
                    newsfeed.setVisibility( View.INVISIBLE );
                    swipe.setVisibility( View.INVISIBLE );
                    dialogproces.hide();
                }


            }


            @Override
            public void onFailure(Call<NewsFeedOuterResponse> call, Throwable t) {

            }
        } );
    }

    // lifestyle module
    public void LifestyleNav(View view) {
        if (checkInternetConenction()) {
            Intent intent = new Intent( this, LifeStyle.class );
            intent.putExtra( "information", email ); // combination contains boolean+gender+age.
            startActivity( intent );
            overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left ); // left to right.

        } else if (!checkInternetConenction()) {

            setContentView( R.layout.no_iternet );
            ImageButton button = (ImageButton) findViewById( R.id.internetbtn );

            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity( getIntent() );
                }

            } );

        }

    }

    public void DietNav(View view) {
        Intent intent = new Intent( this, DietMain.class );
        intent.putExtra( "information", email ); // combination contains boolean+gender+age.
        startActivity( intent );
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right ); // right to left.


    }

    public void FitNav(View view) {
        Intent intent = new Intent( this, FitnessSession.class );
        intent.putExtra( "flow", "noexercise;" );
        startActivity( intent );
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right ); // right to left.


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

            case R.id.homeapp: {
           startActivity( new Intent( getApplicationContext(),UserProfile.class ) );
                break;
            }
            case R.id.setting: {
                break;
            }

            case R.id.reports: {
                Intent intent = new Intent( MainActivity.this, Allnotification.class );
                intent.putExtra( "memberId", memberId );
                startActivity( intent );

                break;
            }

            case R.id.logout: {
                auth.signOut();
                finish();
                startActivity( new Intent( this, StartingActivity.class ) );

                break;
            }


        }
        return false;
    }


    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
        builder.setMessage( "Do you want to exit?" );
        builder.setCancelable( true );
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
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

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService( getBaseContext().CONNECTIVITY_SERVICE );

        // Check for network connections
        if (connec.getNetworkInfo( 0 ).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo( 1 ).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        gettingNewsFeed( memberId );
        Log.d( "sfsads", "sdss" );
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {

    }
}
