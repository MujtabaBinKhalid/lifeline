package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.FoodItems;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.ResponseArray;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/13/2018.
 */

public class PlanResponse {

    public OuterResponse getData() {
        return Data;
   }



    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    @SerializedName("Data")
    @Expose
    private OuterResponse Data;
    private int Status;
    private String Message;

}
