package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.BMICalculator;
import com.lifeline.fyp.fyp.models.BMIResponse;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 2/12/2018.
 */

public class BMIObject {

    public BMIResponse getData() {
        return Data;
    }

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    @SerializedName("Data")
    @Expose
    private BMIResponse Data;
    private int Status;
    private String Message;

}
