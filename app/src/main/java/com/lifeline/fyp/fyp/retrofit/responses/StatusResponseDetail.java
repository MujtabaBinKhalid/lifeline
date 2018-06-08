package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/16/2018.
 */

public class StatusResponseDetail {

    @SerializedName( "postId" )
    @Expose
    Integer postId;


    @SerializedName( "memberId" )
    @Expose
    Integer memberId;


    @SerializedName( "content" )
    @Expose
    String content;


    @SerializedName( "pictureLink" )
    @Expose
    String pictureLink;


    @SerializedName( "noOfLikes" )
    @Expose
    String noOfLikes;


    @SerializedName( "hasImage" )
    @Expose
    String hasImage;


    @SerializedName( "noOfComments" )
    @Expose
    String noOfComments;


    @SerializedName( "time" )
    @Expose
    String time;


    public Integer getPostId() {
        return postId;
    }

    public Integer getMemberId() {
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

    public String getHasImage() {
        return hasImage;
    }

    public String getNoOfComments() {
        return noOfComments;
    }

    public String getTime() {
        return time;
    }
}
