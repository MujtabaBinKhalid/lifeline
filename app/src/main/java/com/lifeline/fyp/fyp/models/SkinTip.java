package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 2/7/2018.
 */

public class SkinTip {

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("type")
    @Expose
    String type; // skin care


    public SkinTip(Integer id) {
        this.id = id;
        this.type = "skin care";
    }

    public SkinTip(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
