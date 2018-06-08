package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/27/2018.
 */

public class NotificaitionObject {

    @SerializedName( "id" )
    @Expose
    private int id;


    @SerializedName( "forMember" )
    @Expose
    private int forMember;


    @SerializedName( "fromMember" )
    @Expose
    private int fromMember;

    @SerializedName( "content" )
    @Expose
    private String content;

    @SerializedName( "postId" )
    @Expose
    private  Integer postId;

    @SerializedName( "notificationType" )
    @Expose
    private String notificationType;

    @SerializedName( "time" )
    @Expose
    private String time;

    @SerializedName( "fromMember_obj" )
    @Expose
    private Member fromMember_obj;

    @SerializedName( "isSeen" )
    @Expose
    private boolean isSeen;


    public int getId() {
        return id;
    }

    public int getForMember() {
        return forMember;
    }

    public int getFromMember() {
        return fromMember;
    }

    public String getContent() {
        return content;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getTime() {
        return time;
    }

    public Member getFromMember_obj() {
        return fromMember_obj;
    }

    public Integer getPostId() {
        return postId;
    }

    public boolean isSeen() {
        return isSeen;
    }
}
