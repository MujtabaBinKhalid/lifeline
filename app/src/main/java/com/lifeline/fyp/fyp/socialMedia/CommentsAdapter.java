package com.lifeline.fyp.fyp.socialMedia;

import android.app.AlertDialog;
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
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.InnerArrayAllComments;
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
 * Created by Mujtaba_khalid on 4/25/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.Comments> {
    public ArrayList<CommentHistory> mModelList;
    Context context;
    Api api;
    Retrofit retrofit;

    public CommentsAdapter(ArrayList<CommentHistory> mModelList, Context context) {
        this.mModelList = mModelList;
        this.context = context;
    }

    @Override
    public Comments onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.allcomments, parent, false );
        return new Comments( view );
    }

    @Override
    public void onBindViewHolder(final Comments holder, final int position) {
        final CommentHistory model = mModelList.get( position );
          holder.content.setText( model.getContent());
          holder.fullname.setText( model.getName() );
          Glide.with( context ).load( model.getPicture() ).into( holder.dp );

    }




    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class Comments extends RecyclerView.ViewHolder {
        CircleImageView dp;
        TextView fullname,content;
        LinearLayout checking;

        public Comments(View itemView) {
            super( itemView );

            dp = (CircleImageView) itemView.findViewById( R.id.dp );
            fullname = (TextView) itemView.findViewById( R.id.fullname );
            content = (TextView) itemView.findViewById( R.id.content );

        }
    }
}
