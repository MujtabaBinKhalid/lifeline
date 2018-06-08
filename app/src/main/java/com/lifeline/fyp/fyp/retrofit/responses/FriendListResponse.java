package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class FriendListResponse {

    @SerializedName( "Status" )
    @Expose
    private String Status;

    @SerializedName( "Message" )
    @Expose
    private String Message;

    @SerializedName("Data")
    @Expose
    private FriendListOption Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public FriendListOption getData() {
        return Data;
    }
}
