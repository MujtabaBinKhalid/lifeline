package com.lifeline.fyp.fyp.lifestyle.suggestions;

/**
 * Created by Mujtaba_khalid on 2/6/2018.
 */

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.StyleAccessories;

import java.util.ArrayList;

public class SwipeCardAdapter extends ArrayAdapter<String> {

  //  ArrayList<StyleAccessories> card_list;
  ArrayList<String> cateogries;
  ArrayList<String> pictures;
  Context context;


    public SwipeCardAdapter(Context context, int resource, ArrayList<String> card_list,ArrayList<String> card_list2) {
        super(context, resource);
        this.cateogries = card_list;
        this.pictures = card_list2;
        this.context = context;
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){

        TextView tv_card_number = (TextView)(contentView.findViewById(R.id.namecategory));
        tv_card_number.setText(cateogries.get(position).toString());


        ImageView image = (ImageView)(contentView.findViewById(R.id.imagesuggestions));

             Glide.with( context ).load(pictures.get( position )).into( image );

        return contentView;

    }

    @Override
    public int getCount() {
        return this.cateogries.size();

    }



}

