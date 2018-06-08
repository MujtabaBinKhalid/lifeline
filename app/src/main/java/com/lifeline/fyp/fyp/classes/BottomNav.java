package com.lifeline.fyp.fyp.classes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;

/**
 * Created by Mujtaba_khalid on 1/28/2018.
 */



public class BottomNav extends Fragment {
    private ImageButton btnlifestyle;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate( R.layout.bottom_navbar,container,false);


        return view;
    }
}
