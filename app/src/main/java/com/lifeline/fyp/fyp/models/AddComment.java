package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/22/2018.
 */

public class AddComment {

    @SerializedName( "postId" )
    @Expose
    private Integer postId;

    @SerializedName( "memberId" )
    @Expose
    private Integer memberId;

    @SerializedName( "content" )
    @Expose
    private String content;

    public AddComment(Integer postId, Integer memberId, String content) {
        this.postId = postId;
        this.memberId = memberId;
        this.content = content;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getContent() {
        return content;
    }
}
