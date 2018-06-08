package com.lifeline.fyp.fyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 2/11/2018.
 */

public class FoodCategory {



      // it is a major Array . which holds the name and the array of the sub items.


    @SerializedName("name")
    @Expose
    private String name;


    private ArrayList<FoodItems> fooditems = new ArrayList<FoodItems>();


      // constructor used for the api call
    public FoodCategory(String name) {
        this.name = name;
    }


     // constructor call for the expandible list view.
    public FoodCategory(String name, ArrayList<FoodItems> fooditems) {
        this.name = name;
        this.fooditems = fooditems;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItems> getFooditems() {
        return fooditems;
    }

    public void setFooditems(ArrayList<FoodItems> fooditems) {
        this.fooditems = fooditems;
    }
}
