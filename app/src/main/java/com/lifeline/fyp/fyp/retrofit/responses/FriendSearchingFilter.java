package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 5/7/2018.
 */

public class FriendSearchingFilter {

    @SerializedName( "Status" )
    @Expose
    private String Status;

    @SerializedName( "Message" )
    @Expose
    private String Message;

    @SerializedName( "Data" )
    @Expose
    private SearchFilter searchFilter;

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public SearchFilter getSearchFilter() {
        return searchFilter;
    }
}
