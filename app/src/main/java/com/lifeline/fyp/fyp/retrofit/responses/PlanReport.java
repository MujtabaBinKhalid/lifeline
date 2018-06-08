package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/28/2018.
 */

public class PlanReport {

    @SerializedName( "Status" )
    Integer Status;

    @SerializedName( "Message" )
    String Message;

    @SerializedName( "Data" )
    FullPlanSocialReport Data;


    public Integer getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public FullPlanSocialReport getData() {
        return Data;
    }
}
