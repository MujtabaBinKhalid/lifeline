package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/7/2018.
 */

public class FootwearSuggestions {

    @SerializedName("dressTone")
    @Expose
    public String dressTone;

    @SerializedName("dressType")
    @Expose
    private String dressType;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName( "shoeColorTone" )
    @Expose
    private String shoeColorTone;


    public FootwearSuggestions(String dressTone, String dressType, String gender,String shoeColorTone) {
        this.dressTone = dressTone;
        this.dressType = dressType;
        this.gender = gender;
        this.shoeColorTone = shoeColorTone;
    }
}
