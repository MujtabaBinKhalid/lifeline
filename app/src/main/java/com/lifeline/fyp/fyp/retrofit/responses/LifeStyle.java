package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/17/2018.
 */

public class LifeStyle {

    @SerializedName( "val1" )
    @Expose
    String val1;

    @SerializedName( "val2" )
    @Expose
    String val2;

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }
}
