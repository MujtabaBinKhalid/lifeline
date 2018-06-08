package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/15/2018.
 */

public class FoodArray {

    @SerializedName("foodItemId")
    @Expose
    private Integer foodItemId;


    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("measure")
    @Expose
    private String measure;


    @SerializedName("weight")
    @Expose
    private Double weight;


    @SerializedName("calories")
    @Expose
    private Double calories;

    @SerializedName("fat")
    @Expose
    private Double fats;

    @SerializedName("carbohydrates")
    @Expose
    private Double carbohydrates;

    @SerializedName("proteins")
    @Expose
    private Double proteins;


    public Integer getFoodItemId() {
        return foodItemId;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getFats() {
        return fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public Double getProteins() {
        return proteins;
    }

    public FoodArray(String name, String measure, Double weight, Double calories, Double fats, Double carbohydrates, Double proteins) {
        this.name = name;
        this.measure = measure;
        this.weight = weight;
        this.calories = calories;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;




    }
}
