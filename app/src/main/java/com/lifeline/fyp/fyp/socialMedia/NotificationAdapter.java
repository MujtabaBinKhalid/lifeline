package com.lifeline.fyp.fyp.socialMedia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.NewsFeedOuterResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujtaba_khalid on 4/27/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Notification> {
    public ArrayList<NotificationStructure> mModelList;
    Context context;
    Api api;
    Retrofit retrofit;
    Integer activemember;


    public NotificationAdapter(ArrayList<NotificationStructure> mModelList, Context context,Integer activemember) {
        this.mModelList = mModelList;
        this.context = context;
       this.activemember = activemember;
    }

    @Override
    public Notification onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.notification, parent, false );
        return new Notification( view );
    }

    @Override
    public void onBindViewHolder(Notification holder, final int position) {

        Log.d("WWQQWWQQWWQQ",mModelList.get( position ).getUserame());

        final NotificationStructure model = mModelList.get( position );
        holder.usename.setText(model.getUserame());
        holder.content.setText( model.getContent() );
        Glide.with( context ).load(model.getPiclink() ).into( holder.userdp );
        holder.clickable.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModelList.get( position ).getNotificationtype().equals( "friendRequest" )){
                    OpeningFriendList( model.getNotificationid() );

                }
                else {
                    OpeningUpthepost( model.getNotificationid(),model.getPostId() );

                }
            }
        } );


    }

    private void OpeningFriendList(final Integer postId){
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Call<NewsFeedOuterResponse> call = api.notificationseen( postId );

        call.enqueue( new Callback<NewsFeedOuterResponse>() {
            @Override
            public void onResponse(Call<NewsFeedOuterResponse> call, Response<NewsFeedOuterResponse> response) {
                Log.d( "ALALALALlalalaRESRESRES", String.valueOf( response ) );
                Intent intent = new Intent( context, PendingRequest.class );
                intent.putExtra( "memberId",activemember );
                context.startActivity( intent );
            }

            @Override
            public void onFailure(Call<NewsFeedOuterResponse> call, Throwable t) {
                Log.d( "sdsdsdsdsdsds",t.getMessage() );
            }
        } );

    }
    private void OpeningUpthepost(final Integer notificationId, final Integer postId){

        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Call<NewsFeedOuterResponse> call = api.notificationseen( postId );

        call.enqueue( new Callback<NewsFeedOuterResponse>() {
            @Override
            public void onResponse(Call<NewsFeedOuterResponse> call, Response<NewsFeedOuterResponse> response) {
         Log.d( "ALALALALlalala", String.valueOf( response ) );
                Intent intent = new Intent( context, SpecificNotification.class );
                intent.putExtra( "postId",postId );
                context.startActivity( intent );
            }

            @Override
            public void onFailure(Call<NewsFeedOuterResponse> call, Throwable t) {

            }
        } );


    }
    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();

    }

    public class Notification extends  RecyclerView.ViewHolder{

        TextView usename,content;
        CircleImageView userdp;
        LinearLayout clickable;

        public Notification(View itemView) {
            super( itemView );
            userdp = (CircleImageView) itemView.findViewById( R.id.userdp );
            usename = (TextView) itemView.findViewById( R.id.username );
            content = (TextView) itemView.findViewById( R.id.noticontent );
            clickable = (LinearLayout) itemView.findViewById( R.id.notification );
        }
    }
}
