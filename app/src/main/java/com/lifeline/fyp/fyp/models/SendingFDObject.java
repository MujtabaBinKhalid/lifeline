package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/6/2018.
 */

public class SendingFDObject {


    @SerializedName("fitnessPlanId")
    @Expose
    private Integer fitnessPlanId;



    @SerializedName("dailyCaloriesIntake")
    @Expose
    private Integer dialyCalorieIntake;


    @SerializedName("actionStatus")
    @Expose
    private Integer actionStatus;


    public SendingFDObject(Integer fitnessPlanId, Integer dialyCalorieIntake, Integer actionStatus) {
        this.fitnessPlanId = fitnessPlanId;
        this.dialyCalorieIntake = dialyCalorieIntake;
        this.actionStatus = actionStatus;
    }

    public Integer getFitnessPlanId() {
        return fitnessPlanId;
    }

    public Integer getDialyCalorieIntake() {
        return dialyCalorieIntake;
    }

    public Integer getActionStatus() {
        return actionStatus;
    }
}
