package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.StyleAccessories;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/16/2018.
 */

public class StatusResponse {

    @SerializedName("Data")
    @Expose
    private StatusResponseDetail Data;
    private int Status;
    private String Message;
    private int DataCount;

    public StatusResponseDetail getData() {
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

