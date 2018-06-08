package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 4/28/2018.
 */

public class FullPlanSocialReport {

    @SerializedName( "name" )
            @Expose
    String name;

    @SerializedName( "weeks" )
@Expose
    String weeks;

    @SerializedName( "startDate" )
    String startDate;

    @SerializedName( "totalDays" )
    String totalDays;

    @SerializedName( "remainingDays" )
    String  remainingDays;

    @SerializedName( "avgRunningSpeed" )
    String avgRunningSpeed;

    @SerializedName( "avgCyclingSpeed" )
    String avgCyclingSpeed;

    @SerializedName( "dailycalorieintake" )
    String dailycalorieintake;

    @SerializedName( "exercisesUsed" )
    ArrayList<String> exercisesUseds;

    @SerializedName( "foodItemEaten" )
    ArrayList<String> foodItemEaten;


    public String getName() {
        return name;
    }

    public String getWeeks() {
        return weeks;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public String getAvgRunningSpeed() {
        return avgRunningSpeed;
    }

    public String getAvgCyclingSpeed() {
        return avgCyclingSpeed;
    }

    public String getDailycalorieintake() {
        return dailycalorieintake;
    }

    public ArrayList<String> getExercisesUseds() {
        return exercisesUseds;
    }

    public ArrayList<String> getFoodItemEaten() {
        return foodItemEaten;
    }
}
