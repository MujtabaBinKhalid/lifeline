package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 3/3/2018.
 */

public class CreatingFitnessPlan {

    @SerializedName("memberId")
    @Expose
    private Integer memberId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("weeks")
    @Expose
    private Integer weeks;

    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;


    public CreatingFitnessPlan(Integer memberId, String name, Integer weeks, String activeStatus) {
        this.memberId = memberId;
        this.name = name;
        this.weeks = weeks;
        this.activeStatus = activeStatus;
    }
}
