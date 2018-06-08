package com.lifeline.fyp.fyp.socialMedia;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.annotations.Expose;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.NewsFeedAdapter;
import com.lifeline.fyp.fyp.classes.NotificationReceiver;
import com.lifeline.fyp.fyp.classes.NotificationService;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.SkinTip;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.UploadImageInterface;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListOption;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListResponse;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.NewsFeedOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.NotificationOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.PlanReport;
import com.lifeline.fyp.fyp.retrofit.responses.PostingStatus;
import com.lifeline.fyp.fyp.retrofit.responses.SearchResultResponse;
import com.lifeline.fyp.fyp.retrofit.responses.SearchResultResponseOuterArray;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.retrofit.responses.StatusResponse;
import com.lifeline.fyp.fyp.retrofit.responses.UploadObject;
import com.lifeline.fyp.fyp.retrofit.responses.UserAllPosts;
import com.lifeline.fyp.fyp.retrofit.responses.UserPostOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.friendRequests;
import com.lifeline.fyp.fyp.retrofit.responses.friendsList;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private static final String TAG = UserProfile.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private Uri uri;
    String currentstate;
    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    FirebaseAuth auth;
    ArrayList<String> uniqueNotification;
    TextView fetchinginfo;
    TextView newfriendsnoti,newpostsnoti;
    FirebaseUser user;
    Integer numberofPosts;
    ImageView allfood, allexer, allfood2, allexer2;
    TextView cal, cycling, running, sdate, sdate2, planname2, planname, plandetail;
    EditText editText;
    private ArrayList<NotificationStructure> notificationfriends;
    private ArrayList<NotificationStructure> notificationpost;
    String email;
    TextView searchingtext;
    static TextView pendingrequesttext;
    UploadImageInterface uploadImage;
    Api api;
    ArrayList<String> allfodditems;
    ArrayList<String> allexercised;
    private Button poststatus, posting, picturestatus, uploadphoto;
    private AlertDialog.Builder builder, builder2;
    private Dialog dialog, dialog2;
    private LinearLayout l1, l2, l3;
    private RelativeLayout r1, r2, r3, r4, r5, r6, r7,r8;
    Integer memberId;
    Retrofit retrofit;
    CircleImageView userdp;
    Bitmap bitmap;
     ProgressDialog dialogproces,dialogprocesfriends,dialogprocessposts;
    TextView username, friendlisttext, pendingrequestnumber, friendlistnumber, nofriend;
    String dp = "false";
    ImageView imageView, caloriezz;
    String runningstatus;
    static TextView noofposts, nooffriends, cal2;
    EditText textshare;
    ImageView calorie;
    LinearLayoutManager dpm, dpaa, dpf,dpnoti,dpfa;
    ArrayList<FetchingNewsFeedData> fetchingallpostsdata;
    private RecyclerView.Adapter fetchingpostadapter,notificationadapter; // skinadapter
    private RecyclerView recyclerViewpost,notifications;
    List<String> emailchecking;
    Button weeks, rdays, tdays;
    Button weeks2, rdays2, tdays2;
    ImageView runningicon, cyclingicon;
    // link of the image.
    String linkoftheimage;
    // recycler view.
    private ArrayList<friendRequests> friendRequests;
    private ArrayList<friendRequests> fullfriendlist;
    public ArrayList<SearchFriendsObject> friends;
    private RecyclerView freindsrecycler;
          static  RecyclerView  friendlist,pendingrequest;
    private RecyclerView.Adapter freindsrecycleradapter, pendingrequestadapter; // skinadapter
    private Toolbar toolbar;
    private NavigationView mNavigationview;
    TextView useremailside,usernameside;
    CircleImageView userimagside;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        //FacebookSdk.sdkInitialize( this.getApplicationContext() );

        setContentView( R.layout.activity_user_profile );

        // disabling.
        ImageButton lifestyle = (ImageButton) findViewById(R.id.lifestyle1);
        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( UserProfile.this, LifeStyle.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );






        // fitnese btn of the nav bar.
        ImageButton fitness1 = (ImageButton) findViewById( R.id.fitness1 );
        fitness1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( UserProfile.this, FitnessSession.class );
                intent.putExtra( "flow","noexericse;" );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );


        ImageButton profile1 = (ImageButton) findViewById( R.id.profile );
        profile1.setEnabled(false);
        profile1.setClickable(false);



        ImageButton home1 = (ImageButton) findViewById( R.id.home1 );
        home1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( UserProfile.this, MainActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );

        // diet button.

        ImageButton diet1 = (ImageButton) findViewById( R.id.nutrition1 );
        diet1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( UserProfile.this, DietMain.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        } );


        mNavigationview = (NavigationView) findViewById( R.id.navmenu );
        View header = mNavigationview.getHeaderView( 0 );

        useremailside = (TextView) header.findViewById( R.id.currentuser );
        usernameside = (TextView) header.findViewById( R.id.activeusername );
        userimagside = (CircleImageView) header.findViewById( R.id.userimage );

        newfriendsnoti= (TextView) findViewById( R.id.newfriendsnoti );
        newpostsnoti= (TextView) findViewById( R.id.newpostsnoti );

        uniqueNotification = new ArrayList<>();
        notificationfriends = new ArrayList<>();
        notificationpost = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        fullfriendlist = new ArrayList<>();
        emailchecking = new ArrayList<>();
        allfodditems = new ArrayList<>();
        allexercised = new ArrayList<>();

        runningicon = (ImageView) findViewById( R.id.runningicon );
        cyclingicon = (ImageView) findViewById( R.id.cyclingicon );
        calorie = (ImageView) findViewById( R.id.calorie );
        caloriezz = (ImageView) findViewById( R.id.caloriezz );

        notifications = (RecyclerView) findViewById( R.id.notification );

        fetchinginfo = (TextView) findViewById( R.id.fetchinginfo );
        plandetail = (TextView) findViewById( R.id.plandetail );
        allfood = (ImageView) findViewById( R.id.allfood );
        allfood2 = (ImageView) findViewById( R.id.allfood2 );
        allexer = (ImageView) findViewById( R.id.allexer );
        freindsrecycler = (RecyclerView) findViewById( R.id.recylcerviewfriends );
        pendingrequest = (RecyclerView) findViewById( R.id.pendingrequest );
        pendingrequesttext = (TextView) findViewById( R.id.pendingrequesttext );
        recyclerViewpost = (RecyclerView) findViewById( R.id.allpost );
        friendlist = (RecyclerView) findViewById( R.id.friendlist );
        friendlisttext = (TextView) findViewById( R.id.friendlisttext );
        friendlistnumber = (TextView) findViewById( R.id.friendlistnumber );
        nooffriends = (TextView) findViewById( R.id.friendsnumber );
        noofposts = (TextView) findViewById( R.id.postsnumber );
        running = (TextView) findViewById( R.id.running );
        cycling = (TextView) findViewById( R.id.cycling );
        cal = (TextView) findViewById( R.id.cal );
        cal2 = (TextView) findViewById( R.id.cal2 );


        weeks2 = (Button) findViewById( R.id.weeks2 );
        rdays2 = (Button) findViewById( R.id.daysleft2 );
        tdays2 = (Button) findViewById( R.id.days2 );

        weeks = (Button) findViewById( R.id.weeks );
        rdays = (Button) findViewById( R.id.daysleft );
        tdays = (Button) findViewById( R.id.days );


        dpm = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        dpf = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        dpnoti = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        dpfa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );

        userdp = (CircleImageView) findViewById( R.id.dp );
        username = (TextView) findViewById( R.id.username );
        l1 = (LinearLayout) findViewById( R.id.posts );
        l2 = (LinearLayout) findViewById( R.id.friends );
        l3 = (LinearLayout) findViewById( R.id.Plan );

        r1 = (RelativeLayout) findViewById( R.id.mainlayout );
        r2 = (RelativeLayout) findViewById( R.id.friendslayout );
        r3 = (RelativeLayout) findViewById( R.id.addingfriend );
        r4 = (RelativeLayout) findViewById( R.id.seachingfriend );
        r5 = (RelativeLayout) findViewById( R.id.userownpost );
        r6 = (RelativeLayout) findViewById( R.id.singleplan );
        r7 = (RelativeLayout) findViewById( R.id.singleplandiet );
        r8 = (RelativeLayout) findViewById( R.id.userallnoti );

        // wanna share button intialization
        poststatus = (Button) findViewById( R.id.postingggg );
        picturestatus = (Button) findViewById( R.id.postinggpicture );
        searchingtext = (TextView) findViewById( R.id.searchingtext );
        pendingrequestnumber = (TextView) findViewById( R.id.pendingrequestnumber );
        nofriend = (TextView) findViewById( R.id.nofriend );
        planname = (TextView) findViewById( R.id.planname );
        sdate = (TextView) findViewById( R.id.date );
        allexer2 = (ImageView) findViewById( R.id.allexer2 );
        planname2 = (TextView) findViewById( R.id.planname2 );
        sdate2 = (TextView) findViewById( R.id.date2 );

        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        // posts tab
        l1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                r1.setVisibility( View.INVISIBLE );
                r2.setVisibility( View.INVISIBLE );
                r3.setVisibility( View.INVISIBLE );
                r4.setVisibility( View.INVISIBLE );

                dialogprocessposts=new ProgressDialog(UserProfile.this);
                dialogprocessposts.setCancelable( false );
                dialogprocessposts.setMessage( "Loading..." );
                dialogprocessposts.show();


                Log.d( "MemberIdOnClicking", String.valueOf( memberId ) );
                     allPostofUser( memberId );
            }
        } );


        // friend tab
        l2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                r1.setVisibility( View.INVISIBLE );
//                r3.setVisibility( View.INVISIBLE );
//                r4.setVisibility( View.INVISIBLE );
//                r5.setVisibility( View.INVISIBLE );
//
//                dialogprocesfriends=new ProgressDialog(UserProfile.this);
//                dialogprocesfriends.setCancelable( false );
//                dialogprocesfriends.setMessage( "Loading..." );
//                dialogprocesfriends.show();

                Intent intent = new Intent( UserProfile.this,Friend_List.class );
                intent.putExtra( "memberId",memberId );
                startActivity( intent );
                Log.d( "MemberIdOnClicking22", String.valueOf( memberId ) );
//                fetchingFriendList( memberId );
            }
        } );

        l3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setVisibility( View.INVISIBLE );
                r2.setVisibility( View.INVISIBLE );
                r3.setVisibility( View.INVISIBLE );
                r4.setVisibility( View.INVISIBLE );
                r5.setVisibility( View.INVISIBLE );
                r6.setVisibility( View.VISIBLE );
                r7.setVisibility( View.VISIBLE );
            }
        } );

        //wanna share buton
        poststatus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openingDialog();
            }
        } );

        picturestatus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openingPictureDialog();
            }
        } );

        uploadImage = retrofit.create( UploadImageInterface.class );
        api = retrofit.create( Api.class );


        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        gettingId( email );


        userdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dp = "true";
                selectingPhoto();
            }
        } );

        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( UserProfile.this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview.setNavigationItemSelectedListener( this );
    }

    private void allPostofUser(final Integer memberId) {

        fetchingallpostsdata = new ArrayList<>();

        Call<UserPostOuterResponse> userPostOuterResponseCall = api.gettinguserpost( memberId );

        userPostOuterResponseCall.enqueue( new Callback<UserPostOuterResponse>() {
            @Override
            public void onResponse(Call<UserPostOuterResponse> call, Response<UserPostOuterResponse> response) {
                Log.d( "UserResponse", String.valueOf( response ) );
                try {
                    fetchinginfo.setVisibility( View.INVISIBLE );
                    Log.d( "SIEE", String.valueOf( response.body().getData().size() ) + " dfddf" );
                    Log.d( "SIEE", String.valueOf( response.body().getData().get( 0 ).getPostcollected().getPostId() ) + " dfddf" );
                    Log.d( "SIEE", String.valueOf( response.body().getData().get( 0 ).getPostcollected().getContent() ) + " dfddf" );

                    String commentstatus;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        commentstatus = response.body().getData().get( i ).getLastCommentposted() + "";

                        if ((response.body().getData().get( i ).getPostcollected().getHasImage())
                                && (commentstatus.equals( "null" ))) {

                            fetchingallpostsdata.add( new FetchingNewsFeedData( response.body().getData().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().get( i ).getPostcollected().getContent(),
                                    response.body().getData().get( i ).getPostcollected().getPictureLink(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.PICTURE,
                                    response.body().getData().get( i ).getUserHasLikedPost() ) );
                        } // end if
                        else if ((response.body().getData().get( i ).getPostcollected().getHasImage())
                                && !(commentstatus.equals( "null" ))) {
                            fetchingallpostsdata.add( new FetchingNewsFeedData( response.body().getData().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().get( i ).getPostcollected().getContent(),
                                    response.body().getData().get( i ).getPostcollected().getPictureLink(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().get( i ).getPostcollected().getTime(),
                                    response.body().getData().get( i ).getLastCommentposted().getContent(),
                                    response.body().getData().get( i ).getLastCommentposted().getTime(),
                                    response.body().getData().get( i ).getLastCommentposted().getMemberdetail().getUsername(),

                                    response.body().getData().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.PICTURE,
                                    response.body().getData().get( i ).getUserHasLikedPost() ) );
                        } // end if


// image nahi hai , commenr hai.
                        else if (!(response.body().getData().get( i ).getPostcollected().getHasImage())
                                && !(commentstatus.equals( "null" ))) {
                            fetchingallpostsdata.add( new FetchingNewsFeedData( response.body().getData().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().get( i ).getPostcollected().getContent(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().get( i ).getPostcollected().getTime(),
                                    response.body().getData().get( i ).getLastCommentposted().getContent(),
                                    response.body().getData().get( i ).getLastCommentposted().getTime(),
                                    response.body().getData().get( i ).getLastCommentposted().getMemberdetail().getUsername(),
                                    response.body().getData().get( i ).getLastCommentposted().getMemberdetail().getProfilePicture(), FetchingNewsFeedData.PostType.TEXT,
                                    response.body().getData().get( i ).getUserHasLikedPost() ) );

                        } // end if

// image nahi hai and connent b nahi hai.
                        else if (!(response.body().getData().get( i ).getPostcollected().getHasImage())
                                && (commentstatus.equals( "null" ))) {

                            fetchingallpostsdata.add( new FetchingNewsFeedData( response.body().getData().get( i ).getPostcollected().getPostId(),
                                    response.body().getData().get( i ).getPostcollected().getMemberId(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getFirstName() +
                                            response.body().getData().get( i ).getPostcollected().getMember().getLastName(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getEmail(),
                                    response.body().getData().get( i ).getPostcollected().getMember().getProfilePicture(),
                                    response.body().getData().get( i ).getPostcollected().getContent(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfComments(),
                                    response.body().getData().get( i ).getPostcollected().getNoOfLikes(),
                                    response.body().getData().get( i ).getPostcollected().getHasImage(),
                                    response.body().getData().get( i ).getPostcollected().getTime(), FetchingNewsFeedData.PostType.TEXT,
                                    response.body().getData().get( i ).getUserHasLikedPost() ) );

                        }


                    }

                    Log.d( "ArrayofAllUserPost", String.valueOf( fetchingallpostsdata.size() ) );
                    fetchingpostadapter = new NewsFeedAdapter( fetchingallpostsdata, UserProfile.this, memberId , "user" );
                    recyclerViewpost.setAdapter( fetchingpostadapter );
                    Log.d( "currentUserMemberId", String.valueOf( memberId ) );
                    recyclerViewpost.setLayoutManager( dpm );
                    dialogprocessposts.hide();
                    r5.setVisibility( View.VISIBLE );


                } catch (Exception e) {
                    dialogprocessposts.hide();
                    fetchinginfo.setVisibility( View.VISIBLE );
                    r5.setVisibility( View.VISIBLE );

                }
            }

            @Override
            public void onFailure(Call<UserPostOuterResponse> call, Throwable t) {
                Log.d( "UserResponse", String.valueOf( t.getMessage() ) );


            }
        } );


    }

    private void gettingFullDetail(Integer memberId) {

        Log.d( "SLdlsldls", String.valueOf( memberId ) );
        Call<FriendDetailsOuter> getProfile = api.getProfile( memberId, memberId );
        getProfile.enqueue( new Callback<FriendDetailsOuter>() {
            @Override

            public void onResponse(Call<FriendDetailsOuter> call, Response<FriendDetailsOuter> response) {
                numberofPosts = Integer.parseInt( response.body().getData().getNoOfPosts() );
                nooffriends.setText( response.body().getData().getNoOfFriends() );
                noofposts.setText( String.valueOf( numberofPosts ) );
            }

            @Override
            public void onFailure(Call<FriendDetailsOuter> call, Throwable t) {

            }
        } );
    }

    private void fetchingFriendList(final Integer memberId) {
        Call<FriendListResponse> friendListResponseCall = api.friendlist( memberId );

        friendListResponseCall.enqueue( new Callback<FriendListResponse>() {
            @Override
            public void onResponse(Call<FriendListResponse> call, Response<FriendListResponse> response) {
                Log.d( "SIZESIZEM",response+"" );
                String checkingSize = response.body().getData().getFriendRequests().size()+"mull";
                Log.d( "SIZESIZEM",checkingSize);
            }

            @Override
            public void onFailure(Call<FriendListResponse> call, Throwable t) {

            }
        } );

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Call<FriendListResponse> friendListResponseCall = api.friendlist( memberId );
                friendListResponseCall.enqueue( new Callback<FriendListResponse>() {
                    @Override
                    public void onResponse(Call<FriendListResponse> call, Response<FriendListResponse> response) {
                        Log.d( "sldsldlsdlsdl", String.valueOf( response ) );


                        List<friendRequests> friendRequestsList = response.body().getData().getFriendRequests();

                        try {
                            pendingrequestnumber.setVisibility( View.VISIBLE );
                            pendingrequestnumber.setText( "(" + response.body().getData().getFriendRequests().size() + ")" );
                            pendingrequest.setVisibility( View.VISIBLE );
                            pendingrequesttext.setVisibility( View.VISIBLE );
                            Log.d( "dkfdkfkd", String.valueOf( response.body().getData().getFriendRequests().size() ) );


                            for (int y = 0; y < friendRequestsList.size(); y++) {
                                Log.d( ":SLSLSLSL", response.body().getData()
                                        .getFriendRequests().get( y ).getEmail() );
                                //String name, String picture, Integer memberId, Integer activeId, String viewProfileAPI, String acceptRequestAPI, String rejectRequestAPI
                                friendRequests.add( new friendRequests( response.body().getData().getFriendRequests().get( y ).getName(),
                                        response.body().getData().getFriendRequests().get( y ).getPicture(),
                                        response.body().getData().getFriendRequests().get( y ).getEmail(),
                                        response.body().getData().getFriendRequests().get( y ).getMemberId(), memberId,
                                        response.body().getData().getFriendRequests().get( y ).getViewProfileAPI(),
                                        response.body().getData().getFriendRequests().get( y ).getAcceptRequestAPI(),
                                        response.body().getData().getFriendRequests().get( y ).getRejectRequestAPI() ) );
                            }


                            pendingrequestadapter = new PendingListAdapter( friendRequests, UserProfile.this ,memberId,"0","0");
                            pendingrequest.setAdapter( pendingrequestadapter );
                            pendingrequest.setLayoutManager( dpf );


                        } catch (Exception e) {
                            pendingrequest.setVisibility( View.INVISIBLE );
                            pendingrequesttext.setVisibility( View.INVISIBLE );
                            pendingrequestnumber.setVisibility( View.INVISIBLE );
                        }
                    }

                    @Override
                    public void onFailure(Call<FriendListResponse> call, Throwable t) {
                        Log.d( "sldsldlsdlsdl", String.valueOf( t.getMessage() ) );

                    }
                } );
            }
        };
        Thread thread = new Thread( runnable );
        thread.start();


        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Call<FriendListResponse> friendListResponseCall = api.friendlist( memberId );
                friendListResponseCall.enqueue( new Callback<FriendListResponse>() {
                    @Override
                    public void onResponse(Call<FriendListResponse> call, Response<FriendListResponse> response) {

                        List<friendsList> listOptions = response.body().getData().getFriendsList();

                        try {
                            friendlisttext.setVisibility( View.VISIBLE );
                            friendlistnumber.setVisibility( View.VISIBLE );
                            friendlistnumber.setVisibility( View.INVISIBLE );
                            friendlistnumber.setText( "(" + response.body().getData().getFriendsList().size() + ")" );
                            for (int y = 0; y < listOptions.size(); y++) {

                                //String name, String picture, Integer memberId, Integer activeId, String viewProfileAPI, String acceptRequestAPI, String rejectRequestAPI
                                fullfriendlist.add( new friendRequests( response.body().getData().getFriendsList().get( y ).getName(),
                                        response.body().getData().getFriendsList().get( y ).getPicture(),
                                        response.body().getData().getFriendsList().get( y ).getEmail(),
                                        response.body().getData().getFriendsList().get( y ).getMemberId(), memberId,
                                        response.body().getData().getFriendsList().get( y ).getViewProfileAPI()
                                ) );

                                pendingrequestadapter = new FriendListAdapter( true,fullfriendlist, UserProfile.this,"0","0" );
                                friendlist.setAdapter( pendingrequestadapter );
                                dpaa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );

                                friendlist.setLayoutManager( dpfa );
                                dialogprocesfriends.hide();
                                r2.setVisibility( View.VISIBLE );

                            }

                        } catch (Exception e) {
                            friendlisttext.setVisibility( View.INVISIBLE );
                            friendlistnumber.setVisibility( View.INVISIBLE );
                            friendlist.setVisibility( View.INVISIBLE );
                            nofriend.setVisibility( View.VISIBLE );
                            dialogprocesfriends.hide();
                            r2.setVisibility( View.VISIBLE );


                        }


                    }

                    @Override
                    public void onFailure(Call<FriendListResponse> call, Throwable t) {

                    }
                } );
            }
        };
//
        Thread thread1 = new Thread( runnable1 );
        thread1.start();

    }

    private void selectingPhoto() {
        Intent openGalleryIntent = new Intent( Intent.ACTION_PICK );
        openGalleryIntent.setType( "image/*" );
        startActivityForResult( openGalleryIntent, REQUEST_GALLERY_CODE );

    }

    private void gettingId(final String email) {

        //

        dialogproces=new ProgressDialog(UserProfile.this);
        dialogproces.setCancelable( false );
        dialogproces.setMessage( "Loading..." );
        dialogproces.show();

        Call<MemberObject> call = api.fetchingUser( email );
                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                        memberId = response.body().getData().getMemberId();
                        useremailside.setText( email );
                        username.setText( response.body().getData().getFirstName() + " " + response.body().getData().getLastName() );
                        usernameside.setText( response.body().getData().getFirstName() + " " + response.body().getData().getLastName() );

//                       r1.setVisibility( View.VISIBLE );

                        try {
                            if (response.body().getData().getProfilePicture() != null) {
                                userdp.setVisibility( View.VISIBLE );
                                Glide.with( UserProfile.this ).load( response.body().getData().getProfilePicture() ).into( userdp );
                                Glide.with( UserProfile.this ).load( response.body().getData().getProfilePicture() ).into( userimagside );
                                Log.d( "Hai bhaim", "2224HAIFFAF" );

                                gettingFullDetail( memberId );
                                gettingNotification( memberId );
                                gettingSocialPlan( memberId );

                            } else {
                                Log.d( "Hai bhaim", "222HAIFFAF" );
                                userdp.setVisibility( View.VISIBLE );
                                userdp.setImageResource( R.drawable.user );
                                gettingFullDetail( memberId );
                                gettingNotification( memberId );
                                gettingSocialPlan( memberId );

                            }
                        } catch (Exception e) {
                            Log.d( "Hai bhaim", "HAIFFAF" );
                            userdp.setVisibility( View.VISIBLE );
                            userdp.setImageResource( R.drawable.user );
                            gettingFullDetail( memberId );
                            gettingNotification( memberId );
                            gettingSocialPlan( memberId );

                        }
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                    }
                } );



        //

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            Log.d( "SLSLSLSLSL", String.valueOf( dp ) );

            uri = data.getData();
            Log.d( "URIYRI", String.valueOf( uri ) );
            String filePath = getRealPathFromURIPath( uri, UserProfile.this );

            Log.d( "URIURI", filePath );
            userdp.setVisibility( View.VISIBLE );

            if (dp.equals( "true" )) {
                Log.d( "SLSLSLSLSL", String.valueOf( "KISA DIA" ) );
                Glide.with( UserProfile.this ).load( uri ).centerCrop().into( userdp );
                File file = new File( filePath );
                Bundle extra = data.getExtras();
                RequestBody mFile = RequestBody.create( MediaType.parse( "image/*" ), file );
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData( "picture", file.getName(), mFile );
                RequestBody filename = RequestBody.create( MediaType.parse( "text/plain" ), file.getName() );
                uploadingtoserver( fileToUpload, filename );

            } else if (dp.equals( "false" )) {
                Glide.with( UserProfile.this ).load( uri ).centerCrop().into( imageView );
                Log.d( "SLSLSLSLSL", String.valueOf( "KISA DIANAHI" ) );
                uploadphoto.setVisibility( View.INVISIBLE );
                posting.setVisibility( View.VISIBLE );
                imageView.setVisibility( View.VISIBLE );
                File file = new File( filePath );
                Bundle extra = data.getExtras();
                RequestBody mFile = RequestBody.create( MediaType.parse( "image/*" ), file );
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData( "picture", file.getName(), mFile );
                RequestBody filename = RequestBody.create( MediaType.parse( "text/plain" ), file.getName() );


                Log.d( "FIleToUpload", filePath );
                Log.d( "FIleToUpload", String.valueOf( fileToUpload ) );

                uploadingtoserver( fileToUpload, filename );


            }


        }
    }

    // uploading the photo to the server.
    private void uploadingtoserver(MultipartBody.Part fileToUpload, RequestBody filename) {
        Call<UploadObject> fileUpload = uploadImage.uploadFile( fileToUpload, filename );

        fileUpload.enqueue( new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                Toast.makeText( UserProfile.this, "Profile Picture Updated ", Toast.LENGTH_LONG ).show();
                //   Glide.with( UserProfile.this ).load( response.body().getData()).into( userdp );


                if (dp.equals( "true" )) {
                    UpdatingProfilePicture( response.body().getData() );
                } else if (dp.equals( "false" )) {
                    linkoftheimage = response.body().getData();

                }
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                Log.d( TAG, "ErrorALALALALALALALLA " + t.getMessage() );
            }
        } );
    }

    // getting the link and updating in the user profile.
    private void UpdatingProfilePicture(String url) {


        Member member = new Member();
        member.setProfilePicture( url );
        Call<MemberObject> call2 = api.updateUser( memberId, member );

        call2.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                Log.d( "Abdekho", String.valueOf( response ) );

            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }
        } );

    }

    /// getting the path of the selected file from the gallery.
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query( contentURI, null, null, null, null );
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
            return cursor.getString( idx );
        }
    }

    private void openingPictureDialog() {
        dialog = new Dialog( UserProfile.this );
        builder = new AlertDialog.Builder( UserProfile.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View customView = inflater.inflate( R.layout.picturestatus, null );
        // reference the textview of custom_popup_dialog

        posting = (Button) customView.findViewById( R.id.postthestatus );
        uploadphoto = (Button) customView.findViewById( R.id.uploadphoto );
        textshare = (EditText) customView.findViewById( R.id.textshare );


        // uploading the photo in the dialog imagebox.
        uploadphoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectingPhoto();

            }
        } );
        imageView = (ImageView) customView.findViewById( R.id.imageviewpic );


        builder.setTitle( "Share What's in your mind?" );
        posting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                postingPictureStatus( textshare.getText().toString(), linkoftheimage );
            }
        } );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    private void openingDialog() {
        dialog = new Dialog( UserProfile.this );
        builder = new AlertDialog.Builder( UserProfile.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );


        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = inflater.inflate( R.layout.statusposting, null );
        // reference the textview of custom_popup_dialog

        posting = (Button) customView.findViewById( R.id.postthestatus );
        editText = (EditText) customView.findViewById( R.id.textstatus );
        builder.setTitle( "What's in your mind?" );

        posting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty( editText.getText().toString() )) {
                    Toast.makeText( UserProfile.this, "Write some text to continue", Toast.LENGTH_LONG ).show();
                } else {
                    postingTextStatus( editText.getText().toString() );
                }
                // api call first then in that call close the dialog.

            }
        } );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.searchbar, menu );
        MenuItem menuItem = menu.findItem( R.id.action_search );
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView( menuItem );

        Log.d( "rtrtwf","fghysdg" );
        Log.d( "rtrtwf", String.valueOf( r1.getVisibility() ) );
        Log.d( "rtrtwf", String.valueOf( r3.getVisibility() ) );

if(r1.getVisibility() ==4 || r3.getVisibility() ==0 || r4.getVisibility() ==0){
    Log.d( "rtrtwf","ddddffdfdfdfdf" );

    searchView.setVisibility( View.VISIBLE );
}
            Log.d( "ALALQL", "sdsds" );
            searchView.setOnSearchClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                      r1.setVisibility( View.INVISIBLE );
                    r2.setVisibility( View.INVISIBLE );
                    r3.setVisibility( View.INVISIBLE );
                    r4.setVisibility( View.VISIBLE );
                }
            } );


        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d( "AKAKAKA", String.valueOf( "||ZZXX||ZXX|||ZZXX||ZZXX||ZZ" ) );


                    if (newText.length() >= 3) {

                        Log.d( "AKAKAKA", String.valueOf( "||ZZXX||ZXX|||ZZXX||ZZXX||ZZ" ) );

                        Call<SearchResultResponseOuterArray> call = api.searchResult( newText, memberId );
                        call.enqueue( new Callback<SearchResultResponseOuterArray>() {
                            @Override
                            public void onResponse(Call<SearchResultResponseOuterArray> call, Response<SearchResultResponseOuterArray> response) {
                                Log.d( "LSLSLSLSLS", String.valueOf( response ) );
                                if (response.code() == 404) {
                                    //    Toast.makeText( UserProfile.this, "NO search found", Toast.LENGTH_LONG ).show();
                                    searchingtext.setText( "No search available" );
                                }
                                else {

                                    Log.d( "IDID", Settings.Secure.getString( getContentResolver(), Settings.Secure.ANDROID_ID ) );
                                    searchingtext.setVisibility( View.INVISIBLE );
                                    List<SearchResultResponse> searchFriendsObjects = response.body().getData().getSearchResults();
                                    Log.d( "LSLSLSLSLS", String.valueOf( response ) );
                                    Log.d( "LSLSLSLSLS", String.valueOf( response.body().getData().getSearchResults().size() ) );


                                    for (int i = 0; i < searchFriendsObjects.size(); i++) {

                                        Log.d( "CheckingBoolState", String.valueOf( emailchecking.contains( response.body().getData().getSearchResults().get( i ).getEmail() ) ) );
                                        Log.d( "CheckingBoolState", String.valueOf( response.body().getData().getSearchResults().get( i ).getEmail() ) );

                                        if (!emailchecking.contains( response.body().getData().getSearchResults().get( i ).getEmail() )) {
                                            emailchecking.add( response.body().getData().getSearchResults().get( i ).getEmail() );

                                            friends.add( new SearchFriendsObject(
                                                    response.body().getData().getSearchResults().get( i ).getId(),
                                                    response.body().getData().getSearchResults().get( i ).getName(),
                                                    response.body().getData().getSearchResults().get( i ).getEmail(),
                                                    response.body().getData().getSearchResults().get( i ).getPicture(),
                                                    response.body().getData().getSearchResults().get( i ).getAreFriends()
                                            ) );

                                        }


                                    }


                                    Log.d( "SIZECHECKING", String.valueOf( emailchecking.size() ) );

                                    Log.d( "AKAKAKA", "its here" );
                                    LinearLayoutManager dpmanager = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );

                                    freindsrecycleradapter = new SearchFriendAdapter( friends, UserProfile.this, memberId );
                                    freindsrecycleradapter.notifyDataSetChanged();

                                    freindsrecycler.setAdapter( freindsrecycleradapter );
                                    freindsrecycler.setLayoutManager( dpmanager );

                                    //  friends.clear();
                                    Log.d( "AKAKAKA", String.valueOf( freindsrecycleradapter.getItemCount() ) );

                                }
                            }

                            @Override
                            public void onFailure(Call<SearchResultResponseOuterArray> call, Throwable t) {
                                Log.d( "LSLSLSLSLS", String.valueOf( t.getMessage() ) );

                            }


                        } );

                    }


                    return false;
                }
            } );




        return super.onCreateOptionsMenu( menu );
    }

    private void postingTextStatus(final String status) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                PostingStatus postingStatus = new PostingStatus( memberId, status, null, "false" );

                Call<StatusResponse> call = api.status( postingStatus );
                call.enqueue( new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        numberofPosts++;
                        noofposts.setText( String.valueOf( numberofPosts ) );
                        dialog.dismiss();
                        Toast.makeText( UserProfile.this, "Status Posted Sucessfully!", Toast.LENGTH_SHORT ).show();
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

    private void postingPictureStatus(final String status, final String link) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                PostingStatus postingStatus = new PostingStatus( memberId, status, link, "true" );

                Call<StatusResponse> call = api.status( postingStatus );
                call.enqueue( new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        numberofPosts++;
                        noofposts.setText( String.valueOf( numberofPosts ) );
                        dialog.dismiss();
                        Toast.makeText( UserProfile.this, "Status Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

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

    private void gettingNotification(final Integer memberId) {
        Call<NotificationOuterResponse> notificationOuterResponseCall
                = api.notificationresponse( memberId );

        notificationOuterResponseCall.enqueue( new Callback<NotificationOuterResponse>() {
            @Override
            public void onResponse(Call<NotificationOuterResponse> call, Response<NotificationOuterResponse> response) {

                String notificationtype;
                for (int i = 0; i < response.body().getNotificaitionObjectList().size(); i++) {

                    notificationtype = response.body().getNotificaitionObjectList().get( i ).getNotificationType();
                    if (notificationtype.trim().equals( "friendRequest" )) {
                        notificationfriends.add( new NotificationStructure(
                               response.body().getNotificaitionObjectList().get( i ).getId(),
                                response.body().getNotificaitionObjectList().get( i ).getContent(),
                                response.body().getNotificaitionObjectList().get( i ).getTime(),
                                response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getFirstName() + " " +
                                        response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getLastName(),
                                response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getEmail(),
                                response.body().getNotificaitionObjectList().get( i ).getFromMember_obj().getProfilePicture(),
                                response.body().getNotificaitionObjectList().get( i ).getNotificationType(),
                                response.body().getNotificaitionObjectList().get( i ).isSeen() ));
                    } else {

                        if(!uniqueNotification.contains( response.body().getNotificaitionObjectList().get( i ).getContent() )){
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
                                    response.body().getNotificaitionObjectList().get( i ).isSeen()) );

                        }

                  if(notificationpost.size() > 0 || notificationfriends.size() >0){

            Log.d( "QLQLWLQLQLWLEL","QWQWQWQWQW" );
            Log.d( "QLQLWLQLQLWLEL", String.valueOf( memberId ) );
         Call<MemberObject> memberCall = api.getmemberbyid( memberId );
         memberCall.enqueue( new Callback<MemberObject>() {
             @Override
             public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

                 Log.d( "Now latest tresponse",response.body().getData().getHasRecentNotifications() );
                 if(response.body().getData().getHasRecentNotifications().trim().equals( "true" )){
                     drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
                 }
             }

             @Override
             public void onFailure(Call<MemberObject> call, Throwable t) {
                 Log.d("AALALALALALLLL",t.getMessage());
             }
         } );

                  }
                    }

                }




            }

            @Override
            public void onFailure(Call<NotificationOuterResponse> call, Throwable t) {

            }
        } );
    }

    private void gettingSocialPlan(Integer memberId) {
        setTitle( "Social Media" );

        Call<PlanReport> planReportCall = api.planreport( memberId );

        planReportCall.enqueue( new Callback<PlanReport>() {
            @Override
            public void onResponse(Call<PlanReport> call, Response<PlanReport> response) {
               Log.d( "dfgfdgdfgdfg", String.valueOf( response ) );
                try {
                    plandetail.setText( "Active" );
                    String runningstatus = response.body().getData().getAvgRunningSpeed() + "";
                    String cyclingstatus = response.body().getData().getAvgCyclingSpeed() + "";
                    String caloriestatus = response.body().getData().getDailycalorieintake() + "";



                    // no fitness plan , so showing diet plan.
                    if (runningstatus.equals( "null" )) {
                        Log.e( "sfdfdffd","111" );
                        for (int i = 0; i < response.body().getData().getFoodItemEaten().size(); i++) {
                            allfodditems.add( response.body().getData().getFoodItemEaten().get( i ) );
                        }

                        planname.setVisibility( View.INVISIBLE );
                        allfood.setVisibility( View.INVISIBLE );
                        allexer.setVisibility( View.INVISIBLE );
                        cal.setVisibility( View.INVISIBLE );
                        calorie.setVisibility( View.INVISIBLE );
                        running.setVisibility( View.INVISIBLE );
                        runningicon.setVisibility( View.INVISIBLE );
                        allexer2.setVisibility( View.INVISIBLE );
                        cycling.setVisibility( View.INVISIBLE );
                        cyclingicon.setVisibility( View.INVISIBLE );
                    }

                   else if (caloriestatus.equals( "null" )) {
                        Log.e( "sfdfdffd","121" );

                        allfood.setVisibility( View.INVISIBLE );
                        cal2.setVisibility( View.INVISIBLE );
                        caloriezz.setVisibility( View.INVISIBLE );
                        allfood2.setVisibility( View.INVISIBLE );
                        allexer.setVisibility( View.INVISIBLE );
                        allexer2.setVisibility( View.VISIBLE );
                        calorie.setVisibility( View.INVISIBLE );
                        cal.setVisibility( View.INVISIBLE );
                        for (int i = 0; i < response.body().getData().getExercisesUseds().size(); i++) {
                            allexercised.add( response.body().getData().getExercisesUseds().get( i ) );
                        }


                    } else {
                        Log.e( "sfdfdffd","1331" );

                        for (int i = 0; i < response.body().getData().getExercisesUseds().size(); i++) {
                            allexercised.add( response.body().getData().getExercisesUseds().get( i ) );
                        }

                        for (int i = 0; i < response.body().getData().getFoodItemEaten().size(); i++) {
                            allfodditems.add( response.body().getData().getFoodItemEaten().get( i ) );
                        }


                        planname2.setVisibility( View.INVISIBLE );
                        allfood2.setVisibility( View.INVISIBLE );

                        caloriezz.setVisibility( View.INVISIBLE );
                        cal2.setVisibility( View.INVISIBLE );


                    }


                    // both fitness and diet plan.

                    // if only ftiness plan.


                    planname.setText( response.body().getData().getName() );
                    planname2.setText( response.body().getData().getName() );

                    running.setText( response.body().getData().getAvgRunningSpeed() + " m/sec" );
                    cycling.setText( response.body().getData().getAvgCyclingSpeed()+ " m/sec" );

                    cal.setText( response.body().getData().getDailycalorieintake()+ " cal" );
                    cal2.setText( response.body().getData().getDailycalorieintake()+ " cal" );

                    weeks.setText( response.body().getData().getWeeks() );
                    weeks2.setText( response.body().getData().getWeeks() );

                    tdays.setText( response.body().getData().getTotalDays() );
                    tdays2.setText( response.body().getData().getTotalDays() );

                    rdays2.setText( response.body().getData().getRemainingDays() );
                    rdays.setText( response.body().getData().getRemainingDays() );

                    String[] sep, sepdate;
                    sep = response.body().getData().getStartDate().split( "T" );
                    sepdate = sep[0].split( "-" );

                    sdate.setText( sepdate[2] + "-" + sepdate[1] + "-" + sepdate[0] );
                    sdate2.setText( sepdate[2] + "-" + sepdate[1] + "-" + sepdate[0] );


                    Log.d( "SIZESIZEISW", String.valueOf( allfodditems.size() ) );

                    allfood.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.e( "QLWERTSDF", allfodditems.size() + "" );
                            if (allfodditems.size() > 0) {
                                displayingItemsDetail( allfodditems, "List of food item taken" );
                            } else {
                                Toast.makeText( UserProfile.this,
                                        "No item available", Toast.LENGTH_SHORT ).show();
                            }

                        }

                    } );


                    allfood2.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.d( "SIZECHECKING", String.valueOf( allfodditems.size() ) );
                            if (allfodditems.size() > 0) {
                                displayingItemsDetail( allfodditems, "List of food item taken" );
                            } else {
                                Toast.makeText( UserProfile.this,
                                        "No item available", Toast.LENGTH_SHORT ).show();
                            }

                        }

                    } );


                    allexer.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (allexercised.size() > 0) {
                                displayingItemsDetail( allfodditems, "List of exercise performed" );

                            } else {
                                Toast.makeText( UserProfile.this,
                                        "No item available", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );


                    allexer2.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (allexercised.size() > 0) {
                                displayingItemsDetail( allfodditems, "List of exercise performed" );

                            } else {
                                Toast.makeText( UserProfile.this,
                                        "No item available", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );


                    dialogproces.hide();
                    r1.setVisibility( View.VISIBLE );

                } catch (Exception e) {
                    Log.d( "RESPONSE NULL", String.valueOf( e ) );

                    l3.setEnabled( false );
                    plandetail.setText( "NO ACTIVE PLAN" );
                    dialogproces.hide();
                    r1.setVisibility( View.VISIBLE );

                }
            }

            @Override
            public void onFailure(Call<PlanReport> call, Throwable t) {

            }
        } );

    }

    private void displayingItemsDetail(ArrayList items, String text) {

String [] seperator = items.get( 0 ).toString().split( ";" );
        int count = items.get( 0 ).toString().length() - items.get( 0 ).toString().replace(";", "").length();
Log.e( "deeeeere", String.valueOf( count ) );
Log.e( "deeeeere", String.valueOf( seperator[0] ) );
ArrayList<String> strings = new ArrayList<>(  );
        for(int i=0; i<= count ;i++){
strings.add( seperator[i] );
        }



        Log.e( "dfwwwew", String.valueOf( items.size() ) );
        Log.e( "dfwwwew", String.valueOf( strings.size() ) );
        Log.e( "dfwwwew", String.valueOf( count ) );
        dialog = new Dialog( UserProfile.this );
        builder = new AlertDialog.Builder( UserProfile.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.arrayofitems, null );

        builder.setTitle( text );
        ListView listview = (ListView) customView.findViewById( R.id.listview );

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>( UserProfile.this,
                R.layout.fooditemname, R.id.itemname, strings );

        listview.setAdapter( arrayAdapter );


        builder.setView( customView );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

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

            case R.id.allnoti: {

//                Log.d( "ALQLWLQLWLL", String.valueOf( notificationfriends.size() ) );
//                Log.d( "ALQLWLQLWLL", String.valueOf( notificationpost.size() ) );
//            Call<NewsFeedOuterResponse> notiseen = api.seen( memberId );
//            notiseen.enqueue( new Callback<NewsFeedOuterResponse>() {
//                @Override
//                public void onResponse(Call<NewsFeedOuterResponse> call, Response<NewsFeedOuterResponse> response) {
//                    Intent intent = new Intent( UserProfile.this,Allnotification.class );
//                    intent.putExtra( "memberId",memberId);
//                    startActivity( intent );
//                }
//
//                @Override
//                public void onFailure(Call<NewsFeedOuterResponse> call, Throwable t) {
//
//                }
//            } );
//

                Intent intent = new Intent( UserProfile.this, Allnotification.class );
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

         if(r5.getVisibility() ==0){
            r5.setVisibility( View.INVISIBLE );
            r1.setVisibility( View.VISIBLE );
         }
         else if (r8.getVisibility() ==0){
             r8.setVisibility( View.INVISIBLE );
             r1.setVisibility( View.VISIBLE );

         }
         else if (r7.getVisibility() ==0){
             r6.setVisibility( View.INVISIBLE );
             r7.setVisibility( View.INVISIBLE );
             r1.setVisibility( View.VISIBLE );

         }
         else if (r6.getVisibility() ==0){
             r6.setVisibility( View.INVISIBLE );
             r7.setVisibility( View.INVISIBLE );
             r1.setVisibility( View.VISIBLE );

         }
         else if (r3.getVisibility() ==0){
             r3.setVisibility( View.INVISIBLE );
             r4.setVisibility( View.INVISIBLE );
             r1.setVisibility( View.VISIBLE );

         }
         else if (r4.getVisibility() ==0){
             r3.setVisibility( View.INVISIBLE );
             r4.setVisibility( View.INVISIBLE );
             r1.setVisibility( View.VISIBLE );

         }
         else if (r1.getVisibility() ==0){
         startActivity( new Intent( UserProfile.this,MainActivity.class ) );
          }




    }


}