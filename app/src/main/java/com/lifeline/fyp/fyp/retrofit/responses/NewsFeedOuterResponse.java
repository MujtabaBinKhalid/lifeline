package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.POST;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class NewsFeedOuterResponse {

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("DataCount")
    @Expose
    private String DataCount;

    @SerializedName( "Data" )
    @Expose
    private NewsFeedResponse Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public String getDataCount() {
        return DataCount;
    }

    public NewsFeedResponse getData() {
        return Data;
    }
}
