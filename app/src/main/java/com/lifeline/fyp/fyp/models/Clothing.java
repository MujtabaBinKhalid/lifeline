package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/9/2018.
 */

public class Clothing {

    @SerializedName("gender")
    @Expose
    private String gender;


    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("weight")
    @Expose
    private String weight;


    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("skinColor")
    @Expose
    private String skinColor;

    @SerializedName("age")
    @Expose
    private String age;


    public Clothing(String gender, String height, String weight, String category, String age) {
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.category = category;
        this.age = age;
    }
}
