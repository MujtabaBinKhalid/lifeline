package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/12/2018.
 */

public class BMICalculator {


    @SerializedName("height")
    @Expose
    private double height;


    @SerializedName("weight")
    @Expose
    private double weight;


    public BMICalculator(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }
}
