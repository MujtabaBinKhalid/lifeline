package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.SendingPlan;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/15/2018.
 */

public class ResponsePlanArray {

    @SerializedName("Data")
    @Expose
    private List<SendingPlan> Data;
    private int Status;
    private String Message;
    private int DataCount;


    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getDataCount() {
        return DataCount;
    }

    public List<SendingPlan> getData() {
        return Data;
    }
}
