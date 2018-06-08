package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/5/2018.
 */

public class DietFitnessPlan {



    public SendingPlan getData() {
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
    private SendingPlan Data;
    private int Status;
    private String Message;
}
