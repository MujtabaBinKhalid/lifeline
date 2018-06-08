package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/16/2018.
 */

public class FetchingCalorie {


    @SerializedName("foodItemId")
    @Expose
    private String foodItemId;


    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("measure")
    @Expose
    private String measure;


    @SerializedName("weight")
    @Expose
    private String weight;


    @SerializedName("calories")
    @Expose
    private String calories;


    @SerializedName("fat")
    @Expose
    private String fat;


    @SerializedName("carbohydrates")
    @Expose
    private String carbohydrates;


    @SerializedName("proteins")
    @Expose
    private String proteins;


    public FetchingCalorie(String name, String fat, String carbohydrates, String proteins) {
        this.name = name;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }



    public FetchingCalorie(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    public FetchingCalorie(String name) {
        this.name = name;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getWeight() {
        return weight;
    }

    public String getCalories() {
        return calories;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public String getProteins() {
        return proteins;
    }
}
