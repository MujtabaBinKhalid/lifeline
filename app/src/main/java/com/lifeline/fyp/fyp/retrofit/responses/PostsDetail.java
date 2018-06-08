package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class PostsDetail {


@SerializedName( "post" )
@Expose
private post postcollected;

    @SerializedName( "lastComment" )
    @Expose
    private lastComment lastCommentposted;


    @SerializedName( "hasLiked" )
    @Expose
    private boolean hasliked;


    public post getPostcollected() {
        return postcollected;
    }

    public lastComment getLastCommentposted() {
        return lastCommentposted;
    }

    public boolean isHasliked() {
        return hasliked;
    }
}
