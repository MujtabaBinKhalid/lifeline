package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/5/2018.
 */

public class SunGlasses {

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("faceShape")
    @Expose
    private String faceShape;

    @SerializedName( "ShadesColor" )
    @Expose
    private String  ShadesColor ;

    public SunGlasses(String gender, String faceShape, String shadesColor) {
        this.gender = gender;
        this.faceShape = faceShape;
        ShadesColor = shadesColor;
    }
}
