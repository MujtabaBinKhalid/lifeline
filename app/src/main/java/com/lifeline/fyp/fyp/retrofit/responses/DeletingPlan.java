package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/17/2018.
 */

public class DeletingPlan {
    @SerializedName("PlanId")
    @Expose
    private Integer PlanId;

    @SerializedName("planType")
    @Expose
    private String planType;

    public DeletingPlan(Integer planId, String planType) {
        PlanId = planId;
        this.planType = planType;
    }
}
