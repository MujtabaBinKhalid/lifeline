package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 1/30/2018.
 */

public class MemberObject {
    public Member getData() {
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
    private Member Data;
    private int Status;

    @SerializedName( "Message" )
    @Expose
    private String Message;
}
