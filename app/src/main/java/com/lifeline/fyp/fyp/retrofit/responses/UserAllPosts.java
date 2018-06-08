package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/23/2018.
 */

public class UserAllPosts {

    @SerializedName( "post" )
    @Expose
    private post post;

    @SerializedName( "lastComment" )
    @Expose
    private lastComment lastCommentposted;

    @SerializedName( "userHasLikedPost" )
    @Expose
    private boolean userHasLikedPost;


    public post getPostcollected() {
        return post;
    }

    public lastComment getLastCommentposted() {
        return lastCommentposted;
    }

    public boolean getUserHasLikedPost() {
        return userHasLikedPost;
    }
}
