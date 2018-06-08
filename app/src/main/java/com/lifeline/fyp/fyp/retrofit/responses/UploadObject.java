package com.lifeline.fyp.fyp.retrofit.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/8/2018.
 */

public class UploadObject {

    @SerializedName( "Status" )
    @Expose
    private  String Status;

    @SerializedName( "Message" )
    @Expose
    private  String Message;

    @SerializedName( "Data" )
    @Expose
    private  String Data;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public String getData() {
        return Data;
    }
}