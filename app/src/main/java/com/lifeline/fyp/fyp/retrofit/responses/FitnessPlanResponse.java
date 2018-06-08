package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/3/2018.
 */

public class FitnessPlanResponse {

    @SerializedName("fitnessPlanId")
    @Expose
    private Integer fitnessPlanId;

    @SerializedName("memberId")
    @Expose
    private String memberId;

    @SerializedName("dietPlanId")
    @Expose
    private String dietPlanId;

    @SerializedName("name")
    @Expose
    private String name;


    public Integer getFitnessPlanId() {
        return fitnessPlanId;
    }
}
