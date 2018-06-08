package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/26/2018.
 */

public class NotificationOuterResponse {
    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("DataCount")
    @Expose
    private String DataCount;


    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public String getDataCount() {
        return DataCount;
    }

    @SerializedName( "Data" )
    private List<NotificaitionObject> notificaitionObjectList;

    public List<NotificaitionObject> getNotificaitionObjectList() {
        return notificaitionObjectList;
    }
}
