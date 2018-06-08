package com.lifeline.fyp.fyp.diet;

/**
 * Created by Mujtaba_khalid on 2/7/2018.
 */



public class Model {

    private String text;
    private boolean isSelected = false;
    private String category;


    public Model(String text, String category) {
        this.text = text;
        this.category = category;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
