package com.lifeline.fyp.fyp.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mujtaba_khalid on 4/18/2018.
 */

public class SearchResponseObject {

    @SerializedName( "searchResults" )
    @Expose
    private List<SearchResultResponse> searchResults;

    public List<SearchResultResponse> getSearchResults() {
        return searchResults;
    }
}
