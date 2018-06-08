package com.lifeline.fyp.fyp.socialMedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.DeletingPlan;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.retrofit.responses.friendRequests;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendList> {
    public static ArrayList<friendRequests> mModelList;
    Context context;
    Api api;
    Retrofit retrofit;
    ProgressDialog dialogprocess;
    String friends,pendingrequests;
    String onlyfriends,onlypending;
    boolean status;


    public FriendListAdapter(boolean status,ArrayList<friendRequests> mModelList, Context context,String friends,String pendingrequest) {
        this.mModelList = mModelList;
        this.context = context;
      this.friends = friends;
      this.pendingrequests =pendingrequest;
      this.status = status;

    }

    @Override
    public FriendList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.friendlist, parent, false );
        return  new FriendList( view );
    }

    @Override
    public void onBindViewHolder(final FriendList holder, final int position) {
        final friendRequests model = mModelList.get( position );
        holder.fullname.setText( model.getName() );
        holder.unfriend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unFriend(holder,position,mModelList.get( position ).getActiveId(),mModelList.get( position ).getMemberId());
              }
        } );

if(status){
    holder.checking.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            VisitingFriendProfile(mModelList.get( position ).getEmail(),mModelList.get( position ).getActiveId(),mModelList.get( position ).getMemberId());

        }
    } );

}
        Glide.with( context ).load( model.getPicture() ).into( holder.dp );

    }

    private void unFriend(FriendList holder, final int position, final Integer activeId, final Integer memberId) {
      Log.d( "Asdasdasdasdasdasdas",pendingrequests );
      Log.d( "Asdasdasdasdasdasdas",friends );
       onlyfriends = friends.replaceAll("\\D+","");
       onlypending = pendingrequests.replaceAll("\\D+","");


        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        final Api api = retrofit.create( Api.class );
        final AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setMessage( "Do you want to unfriend?" );
        builder.setCancelable( false );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogprocess=new ProgressDialog(context);
                dialogprocess.setCancelable( false );
                dialogprocess.setMessage( "Updating friendlist." );
                dialogprocess.show();

                Log.e( "lslslslllsalsa", String.valueOf( activeId ) );
                Log.e( "lslslslllsalsa", String.valueOf( memberId ) );

                Call<SendingRequestResponse> sendingRequestResponseCall = api.unfriend( activeId,memberId );

                sendingRequestResponseCall.enqueue( new Callback<SendingRequestResponse>() {
                    @Override
                    public void onResponse(Call<SendingRequestResponse> call, Response<SendingRequestResponse> response) {
                    Log.d( "ALALLALA", String.valueOf( response ) );
                        mModelList.remove( position );
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,mModelList.size());

                        dialogprocess.dismiss();


                        int number = Integer.parseInt(onlyfriends);
                        int newsfriends = number-1;

                        if(newsfriends ==0){
                            Log.d( "sdfsfsdfsd","SDfdsfsdfsdfsdf" );
                            Friend_List.friendlist.setVisibility( View.INVISIBLE );
                            Friend_List.friendlist2.setVisibility( View.INVISIBLE );

                            Friend_List.friendlistnumber.setVisibility( View.INVISIBLE );
                            Friend_List.friendlistnumber2.setVisibility( View.INVISIBLE );

                            Friend_List.friendlisttext.setVisibility( View.INVISIBLE );
                            Friend_List.friendlisttext2.setVisibility( View.INVISIBLE );


                            Friend_List.nofriend.setVisibility( View.VISIBLE );
                            Friend_List.nofriend2.setVisibility( View.VISIBLE );

                        }
                        else
                        {
                            Log.d( "sdfsfsdfsd","SDfss2222dsfsdfsdfsdf" );
                            Friend_List.friendlistnumber.setText( "("+ String.valueOf( newsfriends ) +")" );
                            Friend_List.friendlistnumber2.setText( "("+String.valueOf( newsfriends )+")" );

                        }

                        Toast.makeText( context,"You are no longer friends!", Toast.LENGTH_LONG ).show();

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

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class FriendList extends RecyclerView.ViewHolder {
        CircleImageView dp;
        TextView fullname;
        Button unfriend;
        LinearLayout checking;

        public FriendList(View itemView) {
            super( itemView );

            dp = (CircleImageView) itemView.findViewById( R.id.dp );
            fullname = (TextView) itemView.findViewById( R.id.fullname );
            unfriend = (Button) itemView.findViewById( R.id.unfriend );
            checking = (LinearLayout) itemView.findViewById( R.id.checking );

        }
    }
}
