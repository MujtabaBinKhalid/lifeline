package com.lifeline.fyp.fyp.retrofit.responses;

import com.lifeline.fyp.fyp.models.SendingPlan;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/4/2018.
 */

public class FitnessResponseObject {

    private SendingPlan Data;
    private int Status;
    private String Message;
    private int DataCount;

    public SendingPlan getData() {
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
