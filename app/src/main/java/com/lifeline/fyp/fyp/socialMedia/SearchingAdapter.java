package com.lifeline.fyp.fyp.socialMedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeline.fyp.fyp.models.FetchingCalorie;
import com.lifeline.fyp.fyp.models.Member;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mujtaba_khalid on 4/10/2018.
 */

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.SeachingHolder> {


    public ArrayList<FriendList> mModelList;
    private Context context;


    public SearchingAdapter(ArrayList<FriendList> mModelList, Context context) {
        this.mModelList = mModelList;
        this.context = context;
      }


    @Override
    public SearchingAdapter.SeachingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SearchingAdapter.SeachingHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SeachingHolder extends RecyclerView.ViewHolder {
        CircleImageView persondp;
        TextView personname;
        TextView personemail;

        public SeachingHolder(View itemView) {
            super( itemView );

        }
    }
}
