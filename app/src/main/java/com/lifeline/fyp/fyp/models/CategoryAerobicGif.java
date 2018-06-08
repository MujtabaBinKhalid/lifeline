package com.lifeline.fyp.fyp.models;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */

public class CategoryAerobicGif {

     String imageLink;
     boolean isSelected = false;



    public CategoryAerobicGif(String imageLink) {
        this.imageLink = imageLink;
    }


    public String getImageLink() {
        return imageLink;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
