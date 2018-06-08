package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/19/2018.
 */

public class FriendDetails {


    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "profilePicture" )
    @Expose
    private String profilePicture;

    @SerializedName( "noOfPosts" )
    @Expose
    private String noOfPosts;

    @SerializedName( "noOfFriends" )
    @Expose
    private String noOfFriends;

    @SerializedName( "status" )
    @Expose
    private String status;


    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getNoOfPosts() {
        return noOfPosts;
    }

    public String getNoOfFriends() {
        return noOfFriends;
    }

    public String getStatus() {
        return status;
    }
}
