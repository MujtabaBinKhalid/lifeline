package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 5/7/2018.
 */

public class Searches {

    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "picture" )
    @Expose
    private String picture;

    @SerializedName( "id" )
    @Expose
    private Integer id;

    @SerializedName( "username" )
    @Expose
    private String username;

    @SerializedName( "email" )
    @Expose
    private String email;


    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
