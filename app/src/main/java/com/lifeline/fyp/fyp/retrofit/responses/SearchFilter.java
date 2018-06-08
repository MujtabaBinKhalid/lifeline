package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 5/7/2018.
 */

public class SearchFilter {

    @SerializedName( "result" )
    @Expose
    private ArrayList<Searches> searches;

    public ArrayList<Searches> getSearches() {
        return searches;
    }
}
