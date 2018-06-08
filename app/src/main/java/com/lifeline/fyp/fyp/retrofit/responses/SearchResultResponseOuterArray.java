package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.SendingPlan;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/17/2018.
 */

public class SearchResultResponseOuterArray {

    @SerializedName("Data")
    @Expose
    private SearchResponseObject Data;
    private int Status;
    private String Message;
    private int DataCount;


    public SearchResponseObject getData() {
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
