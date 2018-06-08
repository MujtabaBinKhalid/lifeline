package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/29/2018.
 */

public class SpecificPostInner {

    @SerializedName( "postId" )
    private int postId;

    @SerializedName( "memberId" )
    private int memberId;

    @SerializedName( "content" )
    private String content;

    @SerializedName( "pictureLink" )
    private String pictureLink;

    @SerializedName( "noOfLikes" )
    private String noOfLikes;

    @SerializedName( "noOfComments" )
    private String noOfComments;

    @SerializedName( "time" )
    private String time;

    @SerializedName( "hasImage" )
    private String hasImage;


    @SerializedName( "member" )
    private Member member;


    public int getPostId() {
        return postId;
    }

    public int getMemberId() {
        return memberId;
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

    public String getTime() {
        return time;
    }

    public String getHasImage() {
        return hasImage;
    }

    public Member getMember() {
        return member;
    }
}
