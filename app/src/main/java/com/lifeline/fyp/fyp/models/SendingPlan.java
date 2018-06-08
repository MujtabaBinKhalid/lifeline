package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/14/2018.
 */

public class SendingPlan {

    @SerializedName("dietPlanId")
    @Expose
    private Integer dietPlanId;

    @SerializedName("fitnessPlanId")
    @Expose
    private Integer fitnessPlanId;


    @SerializedName("memberId")
    @Expose
    private Integer memberId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("weeks")
    @Expose
    private double weeks;

    @SerializedName("dailyCalorieIntake")
    @Expose
    private double dialyCalorieIntake;

    @SerializedName("actionStatus")
    @Expose
    private Integer actionStatus;

    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;

    @SerializedName( "date" )
    @Expose
    private String date;


    public SendingPlan(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public SendingPlan(Integer memberId, String name, double weeks, double dialyCalorieIntake, Integer actionStatus, String activeStatus) {
        this.memberId = memberId;
        this.name = name;
        this.weeks = weeks;
        this.dialyCalorieIntake = dialyCalorieIntake;
        this.activeStatus = activeStatus;
       this.actionStatus = actionStatus;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public double getWeeks() {
        return weeks;
    }

    public double getDialyCalorieIntake() {
        return dialyCalorieIntake;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public Integer getDietPlanId() {
        return dietPlanId;
    }

    public Integer getActionStatus() {
        return actionStatus;
    }

    public String getDate() {
        return date;
    }

    public Integer getFitnessPlanId() {
        return fitnessPlanId;
    }
}
