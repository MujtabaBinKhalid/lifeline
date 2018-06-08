package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */

public class AerobicExercisesCategoryList {

    @SerializedName("exerciseCategory")
    @Expose
    private String exerciseCategory;

    @SerializedName("categorySubcategory")
    @Expose
    private String categorySubcategory;

    @SerializedName("imageLink")
    @Expose
    private String imageLink;

    @SerializedName("description")
    @Expose
    private String description;

    public String getExerciseCategory() {
        return exerciseCategory;
    }

    public String getCategorySubcategory() {
        return categorySubcategory;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }
}
