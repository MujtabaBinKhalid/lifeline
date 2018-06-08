package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class FriendListOption {

    @SerializedName("friendRequests")
    @Expose
    private List<friendRequests> friendRequests;

    @SerializedName( "friendsList" )
    @Expose
    private List<friendsList> friendsList;

    public List<friendRequests> getFriendRequests() {
        return friendRequests;
    }

    public List<friendsList> getFriendsList() {
        return friendsList;
    }
}
