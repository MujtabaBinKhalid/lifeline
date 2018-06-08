package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class lastComment {

    @SerializedName( "id" )
    @Expose
    private Integer id;

    @SerializedName( "memberId" )
    @Expose
    private Integer memberId;

    @SerializedName( "postId" )
    @Expose
    private String postId;

    @SerializedName( "content" )
    @Expose
    private String content;

    @SerializedName( "time" )
    @Expose
    private String time;


    @SerializedName( "member" )
    @Expose
    private Member memberdetail;


    public Member getMemberdetail() {
        return memberdetail;
    }
    public String getContent() {
        return content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getPostId() {
        return postId;
    }

    public String getTime() {
        return time;
    }



}
