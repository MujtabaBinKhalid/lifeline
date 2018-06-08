package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mujtaba_khalid on 1/30/2018.
 */


public class MemberArray {
    @SerializedName("Data")
    @Expose
    private List<Member> Data;
    private int Status;
    private String Message;
    private int DataCount;


    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getDataCount() {
        return DataCount;
    }

    public List<Member> getData() {
        return Data;
    }
}
