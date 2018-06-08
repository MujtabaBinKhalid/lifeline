package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/17/2018.
 */

public class SearchResultResponse {

    @SerializedName( "id" )
    @Expose
    private Integer id;

    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "email" )
    @Expose
    private String email;

    @SerializedName( "picture" )
    @Expose
    private String picture;

    @SerializedName( "areFriends" )
    @Expose
    private boolean areFriends;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public boolean getAreFriends() {
        return areFriends;
    }
}
