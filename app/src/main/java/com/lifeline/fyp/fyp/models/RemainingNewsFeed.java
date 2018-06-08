package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/20/2018.
 */

public class RemainingNewsFeed {


 @SerializedName( "memberId" )
    @Expose
    private Integer memberId;


 @SerializedName( "index" )
    @Expose
    private Integer index;

 @SerializedName( "span" )
    @Expose
    private String span;


    public RemainingNewsFeed(Integer memberId, Integer index, String span) {
        this.memberId = memberId;
        this.index = index;
        this.span = span;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getIndex() {
        return index;
    }

    public String getSpan() {
        return span;
    }
}
