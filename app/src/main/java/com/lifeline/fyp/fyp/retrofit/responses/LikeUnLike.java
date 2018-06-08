package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/26/2018.
 */

public class LikeUnLike {

    @SerializedName( "Data" )
    @Expose
    private int Data;

    public int getData() {
        return Data;
    }
}
