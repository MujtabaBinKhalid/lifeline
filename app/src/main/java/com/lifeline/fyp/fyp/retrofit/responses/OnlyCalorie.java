package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/19/2018.
 */

public class OnlyCalorie {

    @SerializedName("calorie")
    @Expose
    private String calorie;


    public String getCalorie() {
        return calorie;
    }
}
