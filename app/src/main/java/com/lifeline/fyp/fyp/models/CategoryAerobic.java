package com.lifeline.fyp.fyp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */

public class CategoryAerobic {

    String name;
    private ArrayList<CategoryAerobicGif> gifs = new ArrayList<CategoryAerobicGif>();

    public CategoryAerobic(String name, ArrayList<CategoryAerobicGif> gifs) {
        this.name = name;
        this.gifs = gifs;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CategoryAerobicGif> getGifs() {
        return gifs;
    }
}
