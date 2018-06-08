package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/20/2018.
 */

public class FetchingSessionResponse {

    @SerializedName("Data")
    @Expose
    private List<CalorieSessionResponse> Data;
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

    public List<CalorieSessionResponse> getData() {
        return Data;
    }
}
