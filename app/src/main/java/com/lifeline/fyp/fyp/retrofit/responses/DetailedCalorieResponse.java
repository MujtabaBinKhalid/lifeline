package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.FetchingCalorie;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 2/19/2018.
 */

public class DetailedCalorieResponse {

    public FetchingCalorie getData() {
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
    private FetchingCalorie Data;
    private int Status;
    private String Message;

}
