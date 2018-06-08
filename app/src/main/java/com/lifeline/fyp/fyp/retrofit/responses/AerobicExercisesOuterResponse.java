package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.FoodItems;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */

public class AerobicExercisesOuterResponse {


    @SerializedName("Data")
    @Expose
    private List<AerobicExercisesCategoryList> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<AerobicExercisesCategoryList> getData() {
        return Data;
    }

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getDataCount() {
        return DataCount;
    }
}
