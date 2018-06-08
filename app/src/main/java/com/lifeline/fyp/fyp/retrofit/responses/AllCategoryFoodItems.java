package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.FoodItems;
import com.lifeline.fyp.fyp.models.StyleAccessories;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/11/2018.
 */

public class AllCategoryFoodItems {


    @SerializedName("Data")
    @Expose
    private List<FoodItems> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<FoodItems> getData() {
        return Data;
    }

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getDataCount() {
        return DataCount;
    }
}
