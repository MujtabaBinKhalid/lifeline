package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/13/2018.
 */

public class PlanObject {

    @SerializedName("weight")
    @Expose
    private double weight;

    @SerializedName("height")
    @Expose
    private double height;

    @SerializedName("age")
    @Expose
    private int age;

    @SerializedName("activityFactor")
    @Expose
    private double af;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("weeks")
    @Expose
    private String weeks;



    public PlanObject(double weight, double height, int age, double af, String gender) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.af = af;
        this.gender = gender;
    }


    public PlanObject(double weight, double height, int age, double af, String gender, String weeks) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.af = af;
        this.gender = gender;
        this.weeks = weeks;
    }

    public String getWeeks() {
        return weeks;
    }
}
