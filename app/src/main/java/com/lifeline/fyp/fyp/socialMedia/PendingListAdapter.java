package com.lifeline.fyp.fyp.socialMedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.FriendListResponse;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.retrofit.responses.friendRequests;
import com.lifeline.fyp.fyp.retrofit.responses.friendsList;
import com.lifeline.fyp.fyp.socialMedia.PendingRequest;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.PendingRequest> {

    public ArrayList<friendRequests> mModelList;
    Context context;
    Api api;
    String friends,pending;
    String onlyfriends,onlypending;
    Retrofit retrofit;
    ProgressDialog dialogprocess;
    private ArrayList<friendRequests> fullfriendlist;
    private RecyclerView.Adapter  pendingrequestadapter;
    Integer activemember;


    public PendingListAdapter(ArrayList<friendRequests> mModelList, Context context,Integer activemember,String friends,String pendingrequest) {
        this.mModelList = mModelList;
        this.context = context;
        this.activemember = activemember;
        this.friends = friends;
        this.pending = pendingrequest;
    }

    @Override
    public PendingRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.pendingfriendrequest, parent, false );
        return new PendingRequest( view );


    }

    @Override
    public void onBindViewHolder(PendingRequest holder, final int position) {


        final friendRequests model = mModelList.get( position );
        holder.fullname.setText( model.getName() );

        Glide.with( context ).load( model.getPicture() ).into( holder.dp );


        holder.checking.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     VisitingFriendProfile(mModelList.get( position ).getEmail(),mModelList.get( position ).getActiveId(),mModelList.get( position ).getMemberId());
             }
        } );
        holder.accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialogprocess=new ProgressDialog(context);
                dialogprocess.setCancelable( false );
                dialogprocess.setMessage( "Updating friendlist." );
                dialogprocess.show();

               acceptRequest(mModelList.get( position ).getActiveId(), mModelList.get( position ).getMemberId(),position );
                mModelList.remove( position );
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mModelList.size());

            }
        } );

        holder.reject.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rejectReject( mModelList.get( position ).getActiveId(), mModelList.get( position ).getMemberId(),position );
                mModelList.remove( position );
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mModelList.size());

            }
        } );
    }

    private void VisitingFriendProfile(final String email, final Integer activeid, final Integer memberId){


        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        Api api = retrofit.create( Api.class );

        Call<FriendDetailsOuter> call = api.getProfile( activeid,memberId );
        call.enqueue( new Callback<FriendDetailsOuter>() {
            @Override
            public void onResponse(Call<FriendDetailsOuter> call, Response<FriendDetailsOuter> response) {
                Log.d( "lsdflsdlfsdlf", String.valueOf( response ) );
                Log.d( "lsdflsdlfsdlf", String.valueOf( email ) );

                String combination = activeid+";"+memberId+";"+email+";"+"nothing"+";"+response.body().getData().getNoOfFriends()+";"+
                        response.body().getData().getNoOfPosts()+";"+response.body().getData().getStatus();
                Intent intent = new Intent(context,FriendsProfile.class);

                intent.putExtra( "combination",combination );
                Log.d( "sdfsdfsd",combination );
                context.startActivity( intent );

            }

            @Override
            public void onFailure(Call<FriendDetailsOuter> call, Throwable t) {

            }
        } );



        // id,friendid,email,posts,friends
    }

    private void acceptRequest(final Integer memberId, Integer friendId, final Integer position) {

        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Call<SendingRequestResponse> sendingRequestResponseCall = api.acceptrequest( memberId,friendId );
        sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {

                Log.d( "ALLAALALAL",response.body().getMessage() );

                UpdatingFriendList(position);


            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {

            }
        } );
    }


    private void UpdatingFriendList(final Integer position){

        onlypending = pending.replaceAll("\\D+","");


        int number2 = Integer.parseInt(onlypending);
        int newsfriends2 = number2-1;

        if(newsfriends2 == 0){
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequest.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequestnumber.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequesttext.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.nofriend.setVisibility( View.VISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.viewfriends.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.nofriend.setText( "No Pending Request" );
            dialogprocess.dismiss();
        }

        else {
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequestnumber.setVisibility( View.INVISIBLE );
            dialogprocess.dismiss();
        }


                            }



    private void rejectReject(Integer memberId, Integer friendId, final Integer position) {
        onlypending = pending.replaceAll("\\D+","");


        int number2 = Integer.parseInt(onlypending);
        int newsfriends2 = number2-1;

        if(newsfriends2 == 0){
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequest.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequestnumber.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequesttext.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.nofriend.setVisibility( View.VISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.viewfriends.setVisibility( View.INVISIBLE );
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.nofriend.setText( "No Pending Request" );
        }

        else {
            com.lifeline.fyp.fyp.socialMedia.PendingRequest.pendingrequestnumber.setVisibility( View.INVISIBLE );
        }
        Log.d( "sederwer","Erewrwere" );
        Log.d( "sederwer",onlypending);
        Log.d( "sederwer", String.valueOf( number2 ) );
        Log.d( "sederwer", String.valueOf( newsfriends2 ) );

        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        api = retrofit.create( Api.class );

        Call<SendingRequestResponse> sendingRequestResponseCall = api.rejectRequest( memberId,friendId );
        sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
            @Override
            public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {

                Log.d( "akdldlsdlsdldsd", String.valueOf( response ) );


            }

            @Override
            public void onFailure(Call<SendingRequestResponse> call, Throwable t) {

            }
        } );
    }

    @Override
    public int getItemCount() {

        return mModelList == null ? 0 : mModelList.size();
    }

    public class PendingRequest extends RecyclerView.ViewHolder {
        CircleImageView dp;
        TextView fullname;
        Button accept, reject;
        LinearLayout checking;

        public PendingRequest(View itemView) {
            super( itemView );

            dp = (CircleImageView) itemView.findViewById( R.id.dp );
            fullname = (TextView) itemView.findViewById( R.id.fullname );
            accept = (Button) itemView.findViewById( R.id.accept );
            reject = (Button) itemView.findViewById( R.id.reject );
            checking = (LinearLayout) itemView.findViewById( R.id.checking );
        }
    }
}
