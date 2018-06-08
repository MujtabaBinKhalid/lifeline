package com.lifeline.fyp.fyp.lifestyle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 3/17/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> names;
    ArrayList<String> pics;
    public SpinnerAdapter(Context context, ArrayList<String> names, ArrayList<String> pics) {
        super( context, R.layout.facetypelayout,names);
        this.names = names;
        this.pics = pics;
        this.context = context;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row = layoutInflater.inflate( R.layout.facetypelayout,null );
        TextView name = (TextView) row.findViewById( R.id.txt );
        ImageView image = (ImageView) row.findViewById( R.id.img );
        name.setText( names.get( position ) );

        Glide.with( context ).load(pics.get( position )).into( image );

        return row;

    }

@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row = layoutInflater.inflate( R.layout.facetypelayout,null );
        TextView name = (TextView) row.findViewById( R.id.txt );
        ImageView image = (ImageView) row.findViewById( R.id.img );
        name.setText( names.get( position ) );

        Glide.with( context ).load(pics.get( position )).into( image );

        return row;

    }
}
