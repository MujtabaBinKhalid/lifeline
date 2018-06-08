package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.retrofit.responses.AerobicExercisesCategoryList;
import com.lifeline.fyp.fyp.retrofit.responses.FetchingAerobicExerciseCalories;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/16/2018.
 */

public class SendingAerobicExerciseData {

    /////////
//     Sending this information to get the response which are calories.
    ////////////
    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "weight" )
    @Expose
    private int weight;


    @SerializedName( "time" )
    @Expose
    private int time;


    ///////////////////
//     Response of the sending information
    //////////////////

    @SerializedName("Data")
    @Expose
    private FetchingAerobicExerciseCalories Data;
    private int Status;
    private String Message;

    ///////////
    //Constructor
    /////////////

    public SendingAerobicExerciseData(String name, int weight, int time) {
        this.name = name;
        this.weight = weight;
        this.time = time;
    }




    // Getter
    public FetchingAerobicExerciseCalories getData() {
        return Data;
    }
}
