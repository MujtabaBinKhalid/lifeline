package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/16/2018.
 */

public class CheckingCalories {

    // this class has 2 purpose and is used for 3 different API calls.

    // 1. getbyname , which u want to get the deail of the specific object.
    //2 . calulating cal by measure.
    // 3. calculating cal by grams.


    @SerializedName("name")
    @Expose
    public String name;


    @SerializedName("measure")
    @Expose
    public String measure;

    @SerializedName("weight")
    @Expose
    public String weight;






    public CheckingCalories(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public CheckingCalories(String name, String measure, String weight) {
        this.name = name;
        this.measure = measure;
        this.weight = weight;
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
}
