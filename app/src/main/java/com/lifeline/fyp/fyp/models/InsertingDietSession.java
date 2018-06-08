package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/19/2018.
 */

public class InsertingDietSession {

    @SerializedName("planId")
    @Expose
    private Integer planId;

    @SerializedName("caloriesEaten")
    @Expose
    private double caloriesEaten;

    @SerializedName("foodItems")
    @Expose
    private String foodItems;


    public InsertingDietSession(Integer planId, double caloriesEaten, String foodItems) {
        this.planId = planId;
        this.caloriesEaten = caloriesEaten;
        this.foodItems = foodItems;
    }
}

