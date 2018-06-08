package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/6/2018.
 */

public class HairStyle {


    @SerializedName("faceShape")
    @Expose
    private String faceShape;

    @SerializedName("gender")
    @Expose
    private String gender;


    @SerializedName("hairType")
    @Expose
    private String hairType;


    public HairStyle(String faceShape, String gender, String hairType) {
        this.faceShape = faceShape;
        this.gender = gender;
        this.hairType = hairType;
    }
}
