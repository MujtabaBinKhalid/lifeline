package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mujtaba_khalid on 4/7/2018.
 */

public class uploadingPhoto {

    @SerializedName( "picture" )
    @Expose
    String picture;

    public uploadingPhoto(String picture) {
        this.picture = picture;
    }
}
