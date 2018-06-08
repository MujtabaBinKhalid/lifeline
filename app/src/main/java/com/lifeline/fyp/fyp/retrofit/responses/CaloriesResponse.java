package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 2/19/2018.
 */

public class CaloriesResponse {

    public OnlyCalorie getData() {
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
    private OnlyCalorie Data;
    private int Status;
    private String Message;

}
