package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/5/2018.
 */

public class StyleAccessories {

    @SerializedName("styleAccessoriesId")
    @Expose
    private Integer styleAccessoriesId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("picture")
    @Expose
    private String picture;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("category")
    @Expose
    private String category;


    @SerializedName("subCategory")
    @Expose
    private String subCategory;

    public StyleAccessories(String category, String picture) {
        this.category = category;
        this.picture = picture;
    }

    public Integer getStyleAccessoriesId() {
        return styleAccessoriesId;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }
}
