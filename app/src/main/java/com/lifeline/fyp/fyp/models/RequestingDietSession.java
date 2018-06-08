package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/20/2018.
 */

public class RequestingDietSession {

        @SerializedName("dietplanid")
    @Expose
    private Integer dietPlanId;


    public RequestingDietSession(Integer dietPlanId) {
        this.dietPlanId = dietPlanId;
    }
}
