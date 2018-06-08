package com.lifeline.fyp.fyp.socialMedia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.NewsFeedAdapter;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListResponse;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.PlanReport;
import com.lifeline.fyp.fyp.retrofit.responses.SearchResultResponseOuterArray;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.retrofit.responses.UserPostOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.friendRequests;
import com.lifeline.fyp.fyp.retrofit.responses.friendsList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsProfile extends AppCompatActivity {

    private String pinfo;
    private String[] seperator;
    Api api;
    ProgressDialog dialogprocessposts;
    RelativeLayout r1,r2,r3,r4,r5;
    CircleImageView userdp;
    ArrayList<String> allfodditems  = new ArrayList<>(  );
    ArrayList<String> allexercised = new ArrayList<>(  );
    private AlertDialog.Builder builder, builder2;
    private Dialog dialog, dialog2;


    TextView username,postsnumber,friendsnumber,friendlistnumber,nofriend,friendlisttext,plandetail;
    private LinearLayout l1, l2,l3;
    private Button sendrequest,cancelrequest,reject,accept,unfriendrequest;
    ArrayList<FetchingNewsFeedData> fetchingallpostsdata;
    private RecyclerView.Adapter fetchingpostadapter,pendingrequestadapter; // skinadapter
    private RecyclerView recyclerViewpost,friendlist;
    LinearLayoutManager dpm,dpaa;
    private ArrayList<friendRequests> fullfriendlist;
    TextView fetchinginfo;
    TextView planname,planname2,sdate,sdate2;
    ImageView allexer2,runningicon,calorie,cyclingicon,caloriezz,allfood,allfood2,allexer;
    TextView running,cycling,cal,cal2;
    Button tdays2,tdays,rdays2,rdays,weeks2,weeks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_friends_profile );

        fetchinginfo = (TextView) findViewById( R.id.fetchinginfo );
        fetchingallpostsdata = new ArrayList<>(  );
        fullfriendlist = new ArrayList<>(  );
        recyclerViewpost = (RecyclerView) findViewById( R.id.allpost );
        userdp = (CircleImageView) findViewById( R.id.dp );
        username = (TextView) findViewById( R.id.username );
        postsnumber = (TextView) findViewById( R.id.postsnumber );
        friendsnumber = (TextView) findViewById( R.id.friendsnumber );
        l1 = (LinearLayout) findViewById( R.id.posts );
        l2 = (LinearLayout) findViewById( R.id.friends );
        l3 = (LinearLayout) findViewById( R.id. plan);
        sendrequest = (Button) findViewById( R.id.sendrequest );
        cancelrequest = (Button) findViewById( R.id.cancelrequest );
        reject = (Button) findViewById( R.id.rejectrequest );
        accept = (Button) findViewById( R.id.acceptrequest );
        unfriendrequest = (Button) findViewById( R.id.unfriendrequest );
        r1 = (RelativeLayout) findViewById( R.id.mainlayout );
        r2 = (RelativeLayout) findViewById( R.id.userownpost );
        r3 = (RelativeLayout) findViewById( R.id.friendslayout );
        friendlist = (RecyclerView) findViewById( R.id.friendlist );
        friendlistnumber = (TextView) findViewById( R.id.friendlistnumber );
        nofriend = (TextView) findViewById( R.id.nofriend );
        friendlisttext = (TextView) findViewById( R.id.friendlisttext );
        r4 = (RelativeLayout) findViewById( R.id.singleplan );
        r5 = (RelativeLayout) findViewById( R.id.singleplandiet );
        planname = (TextView) findViewById( R.id.planname );
        sdate = (TextView) findViewById( R.id.date );
        allexer2 = (ImageView) findViewById( R.id.allexer2 );
        planname2 = (TextView) findViewById( R.id.planname2 );
        sdate2 = (TextView) findViewById( R.id.date2 );
        plandetail = (TextView) findViewById( R.id.plandetail );


        weeks2 = (Button) findViewById( R.id.weeks2 );
        rdays2 = (Button) findViewById( R.id.daysleft2 );
        tdays2 = (Button) findViewById( R.id.days2 );

        weeks = (Button) findViewById( R.id.weeks );
        rdays = (Button) findViewById( R.id.daysleft );
        tdays = (Button) findViewById( R.id.days );
        runningicon = (ImageView) findViewById( R.id.runningicon );
        cyclingicon = (ImageView) findViewById( R.id.cyclingicon );
        calorie = (ImageView) findViewById( R.id.calorie );
        caloriezz = (ImageView) findViewById( R.id.caloriezz );
        running = (TextView) findViewById( R.id.running );
        cycling = (TextView) findViewById( R.id.cycling );
        cal = (TextView) findViewById( R.id.cal );
        cal2 = (TextView) findViewById( R.id.cal2 );

        allfood = (ImageView) findViewById( R.id.allfood );
        allfood2 = (ImageView) findViewById( R.id.allfood2 );
        allexer = (ImageView) findViewById( R.id.allexer );




        dpm = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        dpaa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );

        // disabling.
        ImageButton lifestyle = (ImageButton) findViewById(R.id.lifestyle1);
        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FriendsProfile.this, LifeStyle.class );
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
                Intent intent = new Intent( FriendsProfile.this, FitnessSession.class );
                intent.putExtra( "flow","noexericse;" );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );


        ImageButton profile1 = (ImageButton) findViewById( R.id.profile );
        profile1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FriendsProfile.this, UserProfile.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );



        ImageButton home1 = (ImageButton) findViewById( R.id.home1 );
        home1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( FriendsProfile.this, MainActivity.class );
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
                Intent intent = new Intent( FriendsProfile.this, DietMain.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        } );


        //setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("combination");
        }

    Log.d( "WOEOQOOQOW",pinfo );
        seperator = pinfo.split(";");
        Log.d( "Sfsdfdsfsdfsdfsd",seperator[0] );
        Log.d( "Sfsdfdsfsdfsdfsd",seperator[1] );
        fetchingFriendList(Integer.parseInt( seperator[1] ));
        gettingSocialPlan(Integer.parseInt( seperator[1] ));


        postsnumber.setText( seperator[5] );
        friendsnumber.setText( seperator[4] );

        if(seperator[6].equals( "areNotFriends" )){
            sendrequest.setVisibility( View.VISIBLE );
            l1.setEnabled( false );
            l2.setEnabled( false );
            l3.setEnabled( false );
        }
        else if(seperator[6].equals( "areFriends" )){
            sendrequest.setVisibility( View.INVISIBLE );
            unfriendrequest.setVisibility( View.VISIBLE );

            l1.setEnabled( true );
            l2.setEnabled( true );
            l3.setEnabled( true );
        }
        else if(seperator[6].equals( "cancelRequest" )){
            sendrequest.setVisibility( View.INVISIBLE );
            cancelrequest.setVisibility( View.VISIBLE );
            l1.setEnabled( false );
            l2.setEnabled( false );
            l3.setEnabled( false );
        }

        else if(seperator[6].equals( "acceptOrRejectRequest" )){
            sendrequest.setVisibility( View.INVISIBLE );
            cancelrequest.setVisibility( View.INVISIBLE );
            l1.setEnabled( false );
            l2.setEnabled( false );
            reject.setVisibility( View.VISIBLE );
            accept.setVisibility( View.VISIBLE );

            l1.setEnabled( true );
            l2.setEnabled( true );
            l3.setEnabled( true );

        }


        unfriendrequest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unFriend( Integer.parseInt( seperator[0] ),Integer.parseInt( seperator[1] ));
            }
        } );
        l1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogprocessposts=new ProgressDialog(FriendsProfile.this);
                dialogprocessposts.setCancelable( false );
                dialogprocessposts.setMessage( "Loading..." );
                dialogprocessposts.show();
                allPostofUser(Integer.parseInt( seperator[1] ));

                r1.setVisibility( View.INVISIBLE );
        r3.setVisibility( View.INVISIBLE );

            }
        } );

        l3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                r1.setVisibility( View.INVISIBLE );
                r2.setVisibility( View.INVISIBLE );
                r3.setVisibility( View.INVISIBLE );
                r4.setVisibility( View.VISIBLE );
                r5.setVisibility( View.VISIBLE );

            }
        } );
        l2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setVisibility( View.INVISIBLE );
                r2.setVisibility( View.INVISIBLE );
                r3.setVisibility( View.VISIBLE );

            }
        } );




        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRequest(Integer.parseInt( seperator[0] ),Integer.parseInt( seperator[1] ));

            }
        } );


        reject.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectReject(Integer.parseInt( seperator[0] ),Integer.parseInt( seperator[1] ));

            }
        } );
        fetchingInformation( seperator[2]);

        sendrequest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendingRequest(Integer.parseInt( seperator[0] ),Integer.parseInt( seperator[1] ));
            }
        } );

        cancelrequest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest(Integer.parseInt( seperator[0] ),Integer.parseInt( seperator[1] ));
            }
        } );

    }


    private void sendingRequest(Integer memberId,Integer friendId){

        Call<SendingRequestResponse> sending = api.sendingrequest( memberId,friendId );

        sending.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {
                Log.d( "Response", String.valueOf( response ) );
                Log.d( "Response", String.valueOf(response.body().getMessage()) );
                Toast.makeText( FriendsProfile.this,"Friend Request Send!!",Toast.LENGTH_LONG ).show();
                sendrequest.setVisibility( View.INVISIBLE );
                cancelrequest.setVisibility( View.VISIBLE );
            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {
                Log.d( "Response", String.valueOf( t.getMessage() ) );

            }
        } );
    }
    private void cancelRequest(Integer memberId,Integer friendId){

        Call<SendingRequestResponse> sending = api.cancelRequest( memberId,friendId );

        sending.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {
                Log.d( "Response", String.valueOf( response ) );
                Log.d( "Response", String.valueOf(response.body().getMessage()) );
                Toast.makeText( FriendsProfile.this,"Friend Request Cancelled!!",Toast.LENGTH_LONG ).show();
                cancelrequest.setVisibility( View.INVISIBLE );
                sendrequest.setVisibility( View.VISIBLE );
            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {
                Log.d( "Response", String.valueOf( t.getMessage() ) );

            }
        } );
    }
    private void acceptRequest(final Integer memberId, Integer friendId) {

        Call<SendingRequestResponse> sendingRequestResponseCall = api.acceptrequest( memberId,friendId );
        sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {

            accept.setVisibility( View.INVISIBLE );
            reject.setVisibility( View.INVISIBLE );
                unfriendrequest.setVisibility( View.VISIBLE );

            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {

            }
        } );
    }


    private void rejectReject(Integer memberId, Integer friendId) {
        Call<SendingRequestResponse> sendingRequestResponseCall = api.rejectRequest( memberId,friendId );
        sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {

                accept.setVisibility( View.INVISIBLE );
                reject.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {

            }
        } );
    }
    private void unFriend(final Integer activeId, final Integer memberId) {

        final AlertDialog.Builder builder = new AlertDialog.Builder( FriendsProfile.this );
        builder.setMessage( "Do you want to unfriend?" );
        builder.setCancelable( true );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Log.d( "lslslslllsalsa", String.valueOf( activeId ) );
                Log.d( "lslslslllsalsa", String.valueOf( memberId ) );

                Call<SendingRequestResponse> sendingRequestResponseCall = api.unfriend( activeId,memberId );

                sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
                    @Override
                    public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {


                        Toast.makeText( FriendsProfile.this,"You are no longer friends!", Toast.LENGTH_LONG ).show();
                 unfriendrequest.setVisibility( View.INVISIBLE );
                        sendrequest.setVisibility( View.VISIBLE );
                        l1.setEnabled( false );
                        l2.setEnabled( false );
                        l3.setEnabled( false );
                        plandetail.setText( "--" );
                    }

                    @Override
                    public void onFailure(Call<SendingRequestResponse> call, Throwable t) {

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
    private void fetchingInformation(final String email) {



        //
        Runnable r = new Runnable() {
            @Override
            public void run() {


                Call<MemberObject> call = api.fetchingUser( email );
                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                        Log.d( "ALALALALALBUANAJ", String.valueOf( response.body().getData().getMemberId() ) );
                        username.setText( response.body().getData().getFirstName() + " " + response.body().getData().getLastName() );
                        try {

                            if (response.body().getData().getProfilePicture() != null) {
                                Log.d( "akakkaka", "alala" );
                                userdp.setVisibility( View.VISIBLE );
                                Log.d( "akakkaka", response.body().getData().getProfilePicture() );
                                Glide.with( FriendsProfile.this ).load( response.body().getData().getProfilePicture() ).into( userdp );

                            }
                        } catch (Exception e) {
                            userdp.setVisibility( View.VISIBLE );
                            userdp.setImageResource( R.drawable.user );
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                    }
                } );

            }
        };

        Thread runnable = new Thread( r );
        runnable.start();


        //

    }

    private void allPostofUser(final Integer memberId) {


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

                    Log.d( "MEMVWRRWRWIDDD", String.valueOf( memberId ) );
                    Log.d( "MEMVWRRWRWIDDD", String.valueOf( seperator[0] ) );
                    Log.d( "MEMVWRRWRWIDDD", String.valueOf( seperator[1] ) );
                    Log.d( "ArrayofAllUserPost", String.valueOf( fetchingallpostsdata.size() ) );
                    fetchingpostadapter = new NewsFeedAdapter( fetchingallpostsdata, FriendsProfile.this, Integer.parseInt( seperator[0] ) , "user" );
                    recyclerViewpost.setAdapter( fetchingpostadapter );
                    Log.d( "currentUserMemberId", String.valueOf( memberId ) );
                    recyclerViewpost.setLayoutManager( dpm );

                    dialogprocessposts.hide();
                    r2.setVisibility( View.VISIBLE );


                } catch (Exception e) {
                    dialogprocessposts.hide();
                    fetchinginfo.setVisibility( View.VISIBLE );
                    r2.setVisibility( View.VISIBLE );

                }
            }

            @Override
            public void onFailure(Call<UserPostOuterResponse> call, Throwable t) {
                Log.d( "UserResponse", String.valueOf( t.getMessage() ) );


            }
        } );


    }


    private void gettingSocialPlan(Integer memberId) {
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
                                Toast.makeText( FriendsProfile.this,
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
                                Toast.makeText( FriendsProfile.this,
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
                                Toast.makeText( FriendsProfile.this,
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
                                Toast.makeText( FriendsProfile.this,
                                        "No item available", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );



                } catch (Exception e) {
                    Log.d( "RESPONSE NULL", String.valueOf( e ) );

                    l3.setEnabled( false );
                    plandetail.setText( "NO ACTIVE PLAN" );

                }
            }

            @Override
            public void onFailure(Call<PlanReport> call, Throwable t) {

            }
        } );

    }

    private void fetchingFriendList(final Integer memberId) {



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
                            friendlistnumber.setText( "(" + response.body().getData().getFriendsList().size() + ")" );
                            for (int y = 0; y < listOptions.size(); y++) {

                                //String name, String picture, Integer memberId, Integer activeId, String viewProfileAPI, String acceptRequestAPI, String rejectRequestAPI
                                fullfriendlist.add( new friendRequests( response.body().getData().getFriendsList().get( y ).getName(),
                                        response.body().getData().getFriendsList().get( y ).getPicture(),
                                        response.body().getData().getFriendsList().get( y ).getEmail(),
                                        response.body().getData().getFriendsList().get( y ).getMemberId(), memberId,
                                        response.body().getData().getFriendsList().get( y ).getViewProfileAPI()
                                ) );

                                friendlist.setVisibility( View.VISIBLE );
                                pendingrequestadapter = new FriendListAdapter( false,fullfriendlist, FriendsProfile.this,"0","0" );
                                friendlist.setAdapter( pendingrequestadapter );
                                dpaa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
                                friendlist.setLayoutManager( dpaa );
                            }

                        } catch (Exception e) {
                            friendlisttext.setVisibility( View.INVISIBLE );
                            friendlistnumber.setVisibility( View.INVISIBLE );
                            friendlist.setVisibility( View.INVISIBLE );
                            nofriend.setVisibility( View.VISIBLE );

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
        dialog = new Dialog( FriendsProfile.this );
        builder = new AlertDialog.Builder( FriendsProfile.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.arrayofitems, null );

        builder.setTitle( text );
        ListView listview = (ListView) customView.findViewById( R.id.listview );

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>( FriendsProfile.this,
                R.layout.fooditemname, R.id.itemname, strings );

        listview.setAdapter( arrayAdapter );


        builder.setView( customView );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    public   void  onBackPressed(){

        if(r1.getVisibility() ==0){
           Log.d( "dfsfdffdf",seperator[0] );
           Log.d( "dfsfdffdf",seperator[1] );
//            Intent intent = new Intent( FriendsProfile.this,Friend_List.class );
//            intent.putExtra( "memberId",seperator[0] );
//            startActivity( intent );
        }
        else if((r2.getVisibility() ==0) || (r3.getVisibility() ==0) || (r4.getVisibility() ==0) || (r5.getVisibility() ==0) ){
            r2.setVisibility( View.INVISIBLE );
            r3.setVisibility( View.INVISIBLE );
            r4.setVisibility( View.INVISIBLE );
            r5.setVisibility( View.INVISIBLE );
            r1.setVisibility( View.VISIBLE );
        }

    }

}

