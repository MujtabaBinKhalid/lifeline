package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/8/2018.
 */

public class FitnessSessionSummary {

    @SerializedName("fitnessPlanId")
    @Expose
    private Integer fitnessPlanId;

    @SerializedName("distanceCovered")
    @Expose
    private float distanceCovered;

    @SerializedName("speed")
    @Expose
    private float speed;

    @SerializedName("calorieBurnt")
    @Expose
    private float calorieBurnt;

    @SerializedName("sessionType")
    @Expose
    private String sessionType;

    @SerializedName( "startTime" )
    @Expose
    private String startTime;

    @SerializedName( "description" )
    @Expose
    private String description;


    // //

    // //

    public FitnessSessionSummary(Integer fitnessPlanId) {
        this.fitnessPlanId = fitnessPlanId;
    }

    public FitnessSessionSummary(Integer fitnessPlanId, float distanceCovered, float speed, float calorieBurnt, String sessionType, String description) {
        this.fitnessPlanId = fitnessPlanId;
        this.distanceCovered = distanceCovered;
        this.speed = speed;
        this.calorieBurnt = calorieBurnt;
        this.sessionType = sessionType;
        this.description = description;
    }

    public FitnessSessionSummary(Integer fitnessPlanId, float calorieBurnt, String sessionType, String description) {
        this.fitnessPlanId = fitnessPlanId;
        this.calorieBurnt = calorieBurnt;
        this.sessionType = sessionType;
        this.description = description;
    }

    public Integer getFitnessPlanId() {
        return fitnessPlanId;
    }

    public float getDistanceCovered() {
        return distanceCovered;
    }

    public float getSpeed() {
        return speed;
    }

    public float getCaloriesBurnt() {
        return calorieBurnt;
    }

    public String getSessionType() {
        return sessionType;
    }

    public String getStartTime() {
        return startTime;
    }


    public String getDescription() {
        return description;
    }
}

