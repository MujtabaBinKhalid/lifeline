package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.FitnessSessionSummary;
import com.lifeline.fyp.fyp.models.FoodArray;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/12/2018.
 */

public class FitnessAllSessions {

    @SerializedName("Data")
    @Expose
    private List<FitnessSessionSummary> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<FitnessSessionSummary> getData() {
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
