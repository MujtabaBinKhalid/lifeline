package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.ResponseArray;

/**
 * Created by Mujtaba_khalid on 2/14/2018.
 */

public class OuterResponse {

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("action status")
    @Expose
    private int actionStatus;

    @SerializedName("Plan A")
    @Expose
    private ResponseArray plana;

    @SerializedName("Plan B")
    @Expose
    private ResponseArray planb;


    public String getAction() {
        return action;
    }

    public int getActionStatus() {
        return actionStatus;
    }

    public ResponseArray getPlana() {
        return plana;
    }

    public ResponseArray getPlanb() {
        return planb;
    }
}
