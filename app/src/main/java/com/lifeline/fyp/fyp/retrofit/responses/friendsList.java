package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class friendsList {

    @SerializedName( "name" )
    @Expose
    private  String name;

    @SerializedName( "picture" )
    @Expose
    private String picture;

    @SerializedName( "memberId" )
    @Expose
    private  Integer memberId;

    private Integer activeId;


    @SerializedName( "viewProfileAPI" )
    @Expose
    private String viewProfileAPI;

    @SerializedName( "email" )
    @Expose
    private String email;


    public friendsList(String name, String picture, String email,Integer memberId,Integer activeId, String viewProfileAPI) {
        this.name = name;
        this.picture = picture;
        this.memberId = memberId;
        this.activeId = activeId;
        this.email = email;
        this.viewProfileAPI = viewProfileAPI;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getViewProfileAPI() {
        return viewProfileAPI;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public String getEmail() {
        return email;
    }
}

