package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/24/2018.
 */

public class PostLikesInner {

    @SerializedName( "id" )
    @Expose
    private Integer id;

    @SerializedName( "memberId" )
    @Expose
    private Integer memberId;

    @SerializedName( "postId" )
    @Expose
    private Integer postId;


    @SerializedName( "member" )
    @Expose
    private Member member;

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getPostId() {
        return postId;
    }

    public Member getMember() {
        return member;
    }
}
