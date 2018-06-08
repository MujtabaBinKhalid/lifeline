package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */

public class AerobicCategoryName {

    @SerializedName( "categoryname" )
    @Expose
    private  String categoryname;


    public AerobicCategoryName(String categoryname) {
        this.categoryname = categoryname;
    }
}
