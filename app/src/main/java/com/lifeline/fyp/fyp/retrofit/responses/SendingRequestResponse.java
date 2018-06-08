package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.StyleAccessories;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class SendingRequestResponse {

    private int Status;
    private String Message;

    public int getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }
}
