package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/20/2018.
 */

public class CalorieSessionResponse {



    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("dietPlanId")
    @Expose
    private Integer dietPlanId;


    @SerializedName("acutualCalories")
    @Expose
    private float acutualCalories;


    @SerializedName("displayCalories")
    @Expose
    private double displayCalories;


    @SerializedName("caloriesEatenOnDay")
    @Expose
    private float caloriesEatenOnDay;


    @SerializedName("caloriesToBeEaten")
    @Expose
    private float caloriesToBeEaten;


    @SerializedName("foodItems")
    @Expose
    private String foodItems;


    @SerializedName("time")
    @Expose
    private String time;


    @SerializedName("errorFactor")
    @Expose
    private double errorFactor;


    @SerializedName("dietPlan")
    @Expose
    private String dietPlan;

    public Integer getId() {
        return id;
    }

    public Integer getDietPlanId() {
        return dietPlanId;
    }

    public float getAcutualCalories() {
        return acutualCalories;
    }

    public double getDisplayCalories() {
        return displayCalories;
    }

    public float getCaloriesEatenOnDay() {
        return caloriesEatenOnDay;
    }

    public float getCaloriesToBeEaten() {
        return caloriesToBeEaten;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public String getTime() {
        return time;
    }

    public double getErrorFactor() {
        return errorFactor;
    }

    public String getDietPlan() {
        return dietPlan;
    }
}
