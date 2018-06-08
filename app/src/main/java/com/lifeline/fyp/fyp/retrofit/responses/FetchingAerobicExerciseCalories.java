package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/16/2018.
 */

public class FetchingAerobicExerciseCalories {

    @SerializedName( "calories" )
    @Expose
    private float calories;


    public float getCalories() {
        return calories;
    }
}
