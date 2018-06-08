package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/23/2018.
 */

public class UserPostOuterResponse {

    @SerializedName( "Status" )
    @Expose
    private Integer Status;

    @SerializedName( "Message" )
    @Expose
    private String Message;

    @SerializedName( "DataCount" )
    @Expose
    private Integer DataCount;


    @SerializedName( "Data" )
    @Expose
    private List<UserAllPosts> Data;

    public List<UserAllPosts> getData() {
        return Data;
    }
}
