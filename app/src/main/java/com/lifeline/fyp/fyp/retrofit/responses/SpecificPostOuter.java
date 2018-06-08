package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/29/2018.
 */

public class SpecificPostOuter {
    @SerializedName( "Status" )
    private int Status;

    @SerializedName( "Message" )
    private String Message;

    @SerializedName( "Data" )
    private SpecificPostInner Data;

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public SpecificPostInner getData() {
        return Data;
    }
}
