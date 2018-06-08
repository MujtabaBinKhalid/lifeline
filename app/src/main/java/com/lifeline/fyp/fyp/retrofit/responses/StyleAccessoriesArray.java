package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.StyleAccessories;
import com.lifeline.fyp.fyp.models.SunGlasses;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 2/5/2018.
 */

public class StyleAccessoriesArray {

    @SerializedName("Data")
    @Expose
    private List<StyleAccessories> Data;
    private int Status;
    private String Message;
    private int DataCount;

    public List<StyleAccessories> getData() {
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
