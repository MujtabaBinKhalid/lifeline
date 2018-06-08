package com.lifeline.fyp.fyp.lifestyle.suggestions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 2/9/2018.
 */

public class ClothingSwipeAdapter extends ArrayAdapter<String>{

    ArrayList<String> pictures;
    Context context;


    public ClothingSwipeAdapter(Context context, int resource, ArrayList<String> card_list) {
        super(context, resource);
        this.pictures = card_list;
        this.context = context;
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){


        ImageView image = (ImageView)(contentView.findViewById(R.id.imagesuggestions));
        //image.setImageResource(Glide.with(context).load( pictures.get(position )));
        Glide.with( context ).load(pictures.get( position )).into( image );

        return contentView;

    }

    @Override
    public int getCount() {
        return this.pictures.size();

    }




}
