package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/14/2018.
 */

public class AerobicResponse {

    @SerializedName("Data")
    @Expose
    private List<String> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<String> getData() {
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
