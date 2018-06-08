package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 5/6/2018.
 */

public class Signin {

    @SerializedName( "email" )
    @Expose
    private String email;

    @SerializedName( "password" )
    @Expose
    private String password;

    public Signin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
