package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/22/2018.
 */

public class likeStatus {

    @SerializedName( "hasLiked" )
    @Expose
    private boolean hasLiked;

    public boolean getHasLiked() {
        return hasLiked;
    }
}
