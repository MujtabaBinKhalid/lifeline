package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class FriendDetailsOuter {

    @SerializedName( "Status" )
    @Expose
    private String Status;

    @SerializedName( "Message" )
    @Expose
    private String Message;

    @SerializedName( "Data" )
    @Expose
    private FriendDetails Data;

    public String getMessage() {
        return Message;
    }

    public FriendDetails getData() {
        return Data;
    }
}
