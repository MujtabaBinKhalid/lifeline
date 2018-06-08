package com.lifeline.fyp.fyp.models;

/**
 * Created by Mujtaba_khalid on 2/11/2018.
 */

public class FoodCategoryDescription {




    private String name;

    private String description;



    public FoodCategoryDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
