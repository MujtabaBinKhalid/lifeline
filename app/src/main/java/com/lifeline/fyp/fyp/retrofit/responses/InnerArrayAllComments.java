package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/24/2018.
 */

public class InnerArrayAllComments {


     @SerializedName( "content" )
    @Expose
    private String content;

    @SerializedName( "member" )
    @Expose
    private Member member;

    public InnerArrayAllComments(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
}
