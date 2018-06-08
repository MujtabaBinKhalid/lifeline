package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/24/2018.
 */

public class AllCommentsOuterResponse {


    @SerializedName("Data")
    @Expose
    private List<InnerArrayAllComments> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<InnerArrayAllComments> getData() {
        return Data;
    }
}
