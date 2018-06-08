package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/13/2018.
 */

public class ResponseArray {


    @SerializedName("BMI")
    @Expose
    private double bmi;

    @SerializedName("weight status")
    @Expose
    private String weightstatus;

    @SerializedName("Calorie intake per day")
    @Expose
    private int calorieintake;

    @SerializedName("ideal weight")
    @Expose
    private double idealweight;

    @SerializedName("minimum healthy weight")
    @Expose
    private double minhw;

    @SerializedName("maximum healthy weight")
    @Expose
    private double maxhw;

    @SerializedName("weight to loose")
    @Expose
    private double wl;

    @SerializedName("weight to gain")
    @Expose
    private double wg;

    @SerializedName("weeks required")
    @Expose
    private double wr;


    public double getBmi() {
        return bmi;
    }

    public String getWeightstatus() {
        return weightstatus;
    }

    public int getCalorieintake() {
        return calorieintake;
    }

    public double getIdealweight() {
        return idealweight;
    }

    public double getMinhw() {
        return minhw;
    }

    public double getMaxhw() {
        return maxhw;
    }

    public double getWl() {
        return wl;
    }

    public double getWg() {
        return wg;
    }

    public double getWr() {
        return wr;
    }
}
