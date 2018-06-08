package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/11/2018.
 */

public class FoodItems {
    @SerializedName("foodItemId")
    @Expose
    private String foodItemId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("subCategory")
    @Expose
    private String subCategory;

    @SerializedName("fat")
    @Expose
    private String fat;

    @SerializedName("protein")
    @Expose
    private String protein;

    @SerializedName("carbohydrates")
    @Expose
    private String carbohydrates;

    @SerializedName("calorie")
    @Expose
    private String calorie;


    public FoodItems(String name, String amount, String fat, String protein, String carbohydrates, String calorie) {
        this.name = name;
        this.amount = amount;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.calorie = calorie;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }


}
