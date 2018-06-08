package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class NewsFeedResponse {

    @SerializedName( "nextIndex" )
    @Expose
    private Integer nextIndex;

    @SerializedName( "startTimeSpan" )
    @Expose
    private String startTimeSpan;

    @SerializedName( "posts" )
    @Expose
    private ArrayList<PostsDetail> posts;

    public Integer getNextIndex() {
        return nextIndex;
    }

    public String getStartTimeSpan() {
        return startTimeSpan;
    }

    public ArrayList<PostsDetail> getPosts() {
        return posts;
    }
}
