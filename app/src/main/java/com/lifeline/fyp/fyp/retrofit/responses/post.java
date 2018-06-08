package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class post {

    @SerializedName("memberId")
    @Expose
    Integer memberId;


    @SerializedName("postId")
    @Expose

    Integer postId;

    @SerializedName("content")
    @Expose
    private String content;


    @SerializedName("pictureLink")
    @Expose
    private String pictureLink;

    @SerializedName("noOfLikes")
    @Expose
    private String noOfLikes;


    @SerializedName("noOfComments")
    @Expose
    private String noOfComments;

    @SerializedName("member")
    @Expose
    private Member member;


    @SerializedName("hasImage")
    @Expose
    private boolean hasImage;

    @SerializedName("time")
    @Expose
    private String time;


    public Integer getMemberId() {
        return memberId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public String getNoOfLikes() {
        return noOfLikes;
    }

    public String getNoOfComments() {
        return noOfComments;
    }

    public boolean getHasImage() {
        return hasImage;
    }

    public String getTime() {
        return time;
    }

    public Member getMember() {
        return member;
    }


}
