package com.lifeline.fyp.fyp.socialMedia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.SkinTip;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujtaba_khalid on 4/17/2018.
 */

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.SearchFriend> {

    public ArrayList<SearchFriendsObject> mModelList;
    Context context;
    Integer id;

    public SearchFriendAdapter(ArrayList<SearchFriendsObject> mModelList, Context context,Integer id) {
        this.mModelList = mModelList;
        this.context = context;
        this.id = id;
    }

    @Override
    public SearchFriend onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.searchingfriend, parent, false);
        return new SearchFriend( view );
    }

    @Override
    public void onBindViewHolder(SearchFriend holder, final int position) {
        final SearchFriendsObject model = mModelList.get(position);
        holder.fullname.setText( model.getName());
        holder.email.setText( model.getEmail() );
        Glide.with( context).load(model.getProfileLink()).into( holder.dp );

        holder.relativeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitingFriendProfile(mModelList.get( position ).getId(),
                        mModelList.get( position ).getEmail(),
                        mModelList.get(position).getAreFriends());
            }
        } );




    }

    private void VisitingFriendProfile(final Integer friendid , final String email, final boolean areFriends){


        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        Api api = retrofit.create( Api.class );

        Call<FriendDetailsOuter> call = api.getProfile( id,friendid );
        call.enqueue( new Callback<FriendDetailsOuter>() {
            @Override
            public void onResponse(Call<FriendDetailsOuter> call, Response<FriendDetailsOuter> response) {
                String combination = id+";"+friendid+";"+email+";"+areFriends+";"+response.body().getData().getNoOfFriends()+";"+
                        response.body().getData().getNoOfPosts()+";"+response.body().getData().getStatus();



                Log.d( "wewewew", String.valueOf( id ) );
                Log.d( "wewewew", String.valueOf( friendid ) );
                if(id == friendid){

                    context.startActivity( new Intent( context,UserProfile.class ) );
                }

                else {
                     Log.d( "CheckingBothIds","22" );
                     Intent intent = new Intent(context,FriendsProfile.class);
                     intent.putExtra( "combination",combination );
                     context.startActivity( intent );

                 }

            }

            @Override
            public void onFailure(Call<FriendDetailsOuter> call, Throwable t) {

            }
        } );



        // id,friendid,email,posts,friends
      }

    @Override
    public int getItemCount() {

            return  mModelList.size();
    }

    public class SearchFriend extends RecyclerView.ViewHolder {

        CircleImageView dp;
        TextView fullname,email;
        RelativeLayout relativeLayout;
        public SearchFriend(View itemView) {
            super( itemView );

            dp = (CircleImageView) itemView.findViewById( R.id.dp );
            fullname = (TextView) itemView.findViewById( R.id.fullname );
            email = (TextView) itemView.findViewById( R.id.email );
            relativeLayout = (RelativeLayout)itemView.findViewById( R.id.tapping );
        }
    }
}
