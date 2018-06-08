package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.StyleAccessories;

/**
 * Created by Mujtaba_khalid on 2/7/2018.
 */

public class StyleAccessoriesObject {

    @SerializedName("Data")
    @Expose
    private StyleAccessories Data;
    private int Status;
    private String Message;


    public StyleAccessories getData() {
        return Data;
    }

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }


}
