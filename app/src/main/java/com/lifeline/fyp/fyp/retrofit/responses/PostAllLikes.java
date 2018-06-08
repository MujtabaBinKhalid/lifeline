package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/24/2018.
 */

public class PostAllLikes {

    @SerializedName( "Status" )
    @Expose
    private Integer Status;

    @SerializedName( "Message" )
    @Expose
    private String  Message;

    @SerializedName( "DataCount" )
    @Expose
    private Integer DataCount;

    @SerializedName( "Data" )
    @Expose
    private List<PostLikesInner> Data;


    public Integer getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public Integer getDataCount() {
        return DataCount;
    }

    public List<PostLikesInner> getData() {
        return Data;
    }
}
