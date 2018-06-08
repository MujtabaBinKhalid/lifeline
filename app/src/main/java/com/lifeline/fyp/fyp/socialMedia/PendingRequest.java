package com.lifeline.fyp.fyp.socialMedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FriendSearchingFilter;
import com.lifeline.fyp.fyp.retrofit.responses.Searches;
import com.lifeline.fyp.fyp.retrofit.responses.friendRequests;
import com.lifeline.fyp.fyp.retrofit.responses.friendsList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PendingRequest extends AppCompatActivity {
    RelativeLayout rl1, rl2,rl3;
    ProgressDialog dialogprocess;
    Retrofit retrofit;
    Api api;
    static TextView pendingrequestnumber, pendingrequesttext,viewfriends;
    static TextView friendlistnumber, friendlisttext, nofriend;
    static TextView friendlisttext2, friendlistnumber2, nofriend2,search;
    static RecyclerView friendlist, pendingrequest, friendlist2,searchingfriend;
    private RecyclerView.Adapter freindsrecycleradapter, pendingrequestadapter,searchingadapter;
    private ArrayList<com.lifeline.fyp.fyp.retrofit.responses.friendRequests> friendRequests;
    private ArrayList<friendRequests> fullfriendlist;
    private ArrayList<friendRequests> fullfriendlist2;
    LinearLayoutManager dpm, dpfa;
    ArrayList<String> emailcontaining;
    ArrayList<SearchFriendsObject> searchFriendsallarray;
    Integer memberId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pending_request );
        emailcontaining = new ArrayList<>();
        searchFriendsallarray = new ArrayList<>();

        dialogprocess = new ProgressDialog( this );
        dialogprocess.setCancelable( false );
        dialogprocess.setMessage( "Loading Data." );
        dialogprocess.show();//

        viewfriends =(TextView) findViewById( R.id.viewfriends );
        rl3 = (RelativeLayout) findViewById( R.id.seachingfriend );
        searchingfriend = (RecyclerView) findViewById( R.id.recylcerviewfriends );
        search = (TextView) findViewById( R.id.searchingtext );
        pendingrequesttext = (TextView) findViewById( R.id.pendingrequesttext );
        pendingrequestnumber = (TextView) findViewById( R.id.pendingrequestnumber );
        pendingrequest = (RecyclerView) findViewById( R.id.pendingrequest );

        friendlisttext = (TextView) findViewById( R.id.friendlisttext );
        friendlistnumber = (TextView) findViewById( R.id.friendlistnumber );
        friendlist = (RecyclerView) findViewById( R.id.friendlist );
        nofriend = (TextView) findViewById( R.id.nofriend );
        friendlisttext2 = (TextView) findViewById( R.id.friendlisttext2 );
        friendlistnumber2 = (TextView) findViewById( R.id.friendlistnumber2 );
        friendlist2 = (RecyclerView) findViewById( R.id.friendlist2 );
        nofriend2 = (TextView) findViewById( R.id.nofriend2 );

        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        friendRequests = new ArrayList<>();
        fullfriendlist = new ArrayList<>();
        fullfriendlist2 = new ArrayList<>();

        rl1 = (RelativeLayout) findViewById( R.id.friendslayoutwithpending );
        rl2 = (RelativeLayout) findViewById( R.id.friendslayout );
        dpm = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        dpfa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            memberId = extras.getInt( "memberId" ); // information contains boolean+gender+age+lifestylecategory.
        }

        Log.d( "yyyyrrrwwwqqqqqwwee","Pending" );

        fetchingFriendList( memberId );
        viewfriends.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingFriendList(memberId);
            }
        } );


    }
    private void loadingFriendList(Integer memberId){
        Intent intent = new Intent( PendingRequest.this,Friend_List.class );
        intent.putExtra( "memberId",memberId );
        startActivity( intent );
    }
    private void fetchingFriendList(final Integer memberId) {
        Log.d( "R U HERE", "22" );
        Log.d( "R U HERE", String.valueOf( memberId ) );



        Call<FriendListResponse> friendListResponseCall = api.friendlist( memberId );
        friendListResponseCall.enqueue( new Callback<FriendListResponse>() {
            @Override
            public void onResponse(Call<FriendListResponse> call, Response<FriendListResponse> response) {

                // no friends.
                if (response.body().getData().getFriendsList().size() == 0) {

                    // no friends and no pending request
                    if (response.body().getData().getFriendRequests().size() == 0) {
//                        rl2.setVisibility( View.VISIBLE );
//                        friendlisttext2.setVisibility( View.INVISIBLE );
//                        friendlistnumber2.setVisibility( View.INVISIBLE );
//                        friendlist2.setVisibility( View.INVISIBLE );
//                        nofriend2.setVisibility( View.VISIBLE );
//                        dialogprocess.hide();

                    }


                    // no friends but yes pending request.
                    else {
                        List<friendRequests> friendRequestsList = response.body().getData().getFriendRequests();
                        rl1.setVisibility( View.VISIBLE );
                        pendingrequestnumber.setVisibility( View.INVISIBLE );
                        pendingrequestnumber.setText( "(" + response.body().getData().getFriendRequests().size() + ")" );
                        pendingrequest.setVisibility( View.VISIBLE );
                        pendingrequesttext.setVisibility( View.VISIBLE );

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
                        pendingrequestadapter = new PendingListAdapter( friendRequests, PendingRequest.this, memberId,"0",pendingrequestnumber.getText().toString() );
                        pendingrequest.setAdapter( pendingrequestadapter );
                        pendingrequest.setLayoutManager( dpm );
                        friendlisttext.setVisibility( View.INVISIBLE );
                        friendlistnumber.setVisibility( View.INVISIBLE );
                        friendlist.setVisibility( View.INVISIBLE );
                        nofriend.setVisibility( View.VISIBLE );
                        dialogprocess.hide();


                    }
                }
                // yes friends.
                else {
//                    viewfriends.setVisibility( View.VISIBLE );
//                    // yes friends and no pending request
                   if (response.body().getData().getFriendRequests().size() == 0) {
//                        rl1.setVisibility( View.INVISIBLE );
//                        rl2.setVisibility( View.VISIBLE );
//                        List<friendsList> listOptions = response.body().getData().getFriendsList();
//                        friendlisttext2.setVisibility( View.VISIBLE );
//                        friendlistnumber2.setVisibility( View.VISIBLE );
//                        friendlistnumber2.setText( "(" + response.body().getData().getFriendsList().size() + ")" );
//
//                        for (int y = 0; y < listOptions.size(); y++) {
//                            //String name, String picture, Integer memberId, Integer activeId, String viewProfileAPI, String acceptRequestAPI, String rejectRequestAPI
//                            fullfriendlist2.add( new friendRequests( response.body().getData().getFriendsList().get( y ).getName(),
//                                    response.body().getData().getFriendsList().get( y ).getPicture(),
//                                    response.body().getData().getFriendsList().get( y ).getEmail(),
//                                    response.body().getData().getFriendsList().get( y ).getMemberId(), memberId,
//                                    response.body().getData().getFriendsList().get( y ).getViewProfileAPI()
//                            ) );
//
//                        }
//                        friendlist2.setVisibility( View.VISIBLE );
//                        pendingrequestadapter = new FriendListAdapter( true,fullfriendlist2, PendingRequest.this,friendlistnumber2.getText().toString(),"0" );
//                        friendlist2.setAdapter( pendingrequestadapter );
//                        dpfa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
//
//                        friendlist2.setLayoutManager( dpfa );
//                        //dialogprocesfriends.hide();

                        // rl1.setVisibility( View.VISIBLE );
                        dialogprocess.hide();

                    }

                    // yes friends and yes pending request.
                    else {
                        List<friendRequests> friendRequestsList = response.body().getData().getFriendRequests();
                        rl1.setVisibility( View.VISIBLE );
                        pendingrequestnumber.setVisibility( View.INVISIBLE );
                        pendingrequestnumber.setText( "(" + response.body().getData().getFriendRequests().size() + ")" );
                        pendingrequest.setVisibility( View.VISIBLE );
                        pendingrequesttext.setVisibility( View.VISIBLE );

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

                        pendingrequestadapter = new PendingListAdapter( friendRequests, PendingRequest.this, memberId,friendlistnumber.getText().toString(),pendingrequestnumber.getText().toString() );
                        pendingrequest.setAdapter( pendingrequestadapter );
                        pendingrequest.setLayoutManager( dpm );



//                        List<friendsList> listOptions = response.body().getData().getFriendsList();
//                        friendlisttext.setVisibility( View.VISIBLE );
//                        friendlistnumber.setVisibility( View.VISIBLE );
//                        friendlistnumber.setText( "(" + response.body().getData().getFriendsList().size() + ")" );
//
//                        for (int y = 0; y < listOptions.size(); y++) {
//                            //String name, String picture, Integer memberId, Integer activeId, String viewProfileAPI, String acceptRequestAPI, String rejectRequestAPI
//                            fullfriendlist.add( new friendRequests( response.body().getData().getFriendsList().get( y ).getName(),
//                                    response.body().getData().getFriendsList().get( y ).getPicture(),
//                                    response.body().getData().getFriendsList().get( y ).getEmail(),
//                                    response.body().getData().getFriendsList().get( y ).getMemberId(), memberId,
//                                    response.body().getData().getFriendsList().get( y ).getViewProfileAPI()
//                            ) );
//
//                        }
//                        friendlist.setVisibility( View.VISIBLE );
//                        pendingrequestadapter = new FriendListAdapter( fullfriendlist, PendingRequest.this,friendlistnumber.getText().toString(),pendingrequesttext.getText().toString() );
//                        friendlist.setAdapter( pendingrequestadapter );
//                        dpfa = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
//
//                        friendlist.setLayoutManager( dpfa );
//                        //dialogprocesfriends.hide();

                        rl1.setVisibility( View.VISIBLE );
                        dialogprocess.hide();

                    }
                }
            }

            @Override
            public void onFailure (Call < FriendListResponse > call, Throwable t){
                Log.d( "ALWQLWQWQW.", "wbaskaswas" );
                Log.d( "ALWQLWQWQW.", t.getMessage() );
            }
        } );


    }

}
