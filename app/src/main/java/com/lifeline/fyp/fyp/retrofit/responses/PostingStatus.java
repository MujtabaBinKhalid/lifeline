package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/16/2018.
 */

public class PostingStatus {

    @SerializedName( "memberId" )
    @Expose
    Integer memberId;

    @SerializedName( "content" )
    @Expose
    String content;

    @SerializedName( "pictureLink" )
    @Expose
    String pictureLink;

    @SerializedName( "hasImage" )
    @Expose
    String hasImage;


    public PostingStatus(Integer memberId, String content, String pictureLink, String hasImage) {
        this.memberId = memberId;
        this.content = content;
        this.pictureLink = pictureLink;
        this.hasImage = hasImage;
    }
}
