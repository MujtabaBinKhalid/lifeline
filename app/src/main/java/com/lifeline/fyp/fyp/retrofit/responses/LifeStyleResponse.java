package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/17/2018.
 */

public class LifeStyleResponse {

    @SerializedName("Data")
    @Expose
    private List<LifeStyle> Data;
    private int Status;
    private String Message;
    private int DataCount;


    public List<LifeStyle> getData() {
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
