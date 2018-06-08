package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/12/2018.
 */

public class BMIResponse {

    @SerializedName("BMI")
    @Expose
    private double bmi;

    @SerializedName("weight status")
    @Expose
    private String weightStatus;

    @SerializedName("maximum healthy weight")
    @Expose
    private double mhw;

    @SerializedName("ideal weight")
    @Expose
    private double iw;

    @SerializedName("response status")
    @Expose
    private Integer responsestatus;

    public double getBmi() {
        return bmi;
    }

    public String getWeightStatus() {
        return weightStatus;
    }

    public double getMhw() {
        return mhw;
    }

    public double getIw() {
        return iw;
    }

    public Integer getResponsestatus() {
        return responsestatus;
    }
}
