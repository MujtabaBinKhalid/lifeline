package com.lifeline.fyp.fyp.fitness;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.CategoryAerobic;
import com.lifeline.fyp.fyp.models.CategoryAerobicGif;
import com.lifeline.fyp.fyp.models.FoodCategory;
import com.lifeline.fyp.fyp.models.FoodItems;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 3/15/2018.
 */


public class SubCategoryAdapter extends BaseExpandableListAdapter {

    ProgressDialog progressDialog;

    private Context context;   // context of the actibviy passed.
    private ArrayList<CategoryAerobic> categorylist; // passing all the data into this list. array of the major item.
    private String outercategoryname;

    private String categoryname,imagelink;
    public SubCategoryAdapter(Context context, ArrayList<CategoryAerobic> category,String name) {

        this.context = context;  // intialization.
        this.categorylist = new ArrayList<CategoryAerobic>(); // intialization
        this.categorylist.addAll(category); // adding the items to the new array.
        this.outercategoryname = name;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<CategoryAerobicGif> giflist = categorylist.get(groupPosition).getGifs(); // child array. getting the elements of the child by index number and get method.
        return giflist.get(childPosition); // return the specific child item.
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition; // returning the child position.
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final CategoryAerobicGif cag = (CategoryAerobicGif) getChild(groupPosition, childPosition); // getting the specific number.
        final CategoryAerobic ca = (CategoryAerobic) getGroup(groupPosition);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate( R.layout.giflayout, null); // inlfating the view.


        Log.d( "QQQQQQDLDLDLD",cag.getImageLink() );
        Log.d( "sfds","asdaBahisde" );


        final ImageView gif = (ImageView) view.findViewById(R.id.gif);
        gif.setBackgroundColor(cag.isSelected() ? Color.GRAY : Color.WHITE);
        Glide.with(context)

                .load(cag.getImageLink()).asGif().listener( new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                Log.d( "sfds","asdasde" );
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.d( "sfds","a22211122sdasde" );

                return false;
            }
        } )
                .into(gif);




        gif.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cag.setSelected(!cag.isSelected());
                gif.setBackgroundColor(cag.isSelected() ? Color.GRAY : Color.WHITE);
                categoryname = ca.getName();
                imagelink = cag.getImageLink();
                Log.v( "dfdf",categoryname );
                Log.d( "ssssssa22aqqqq",outercategoryname );

                Continue();
            }
        } );


        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<CategoryAerobicGif> al = categorylist.get(groupPosition).getGifs();
        return al.size(); // size of the child of the main item.

    }

    @Override
    public Object getGroup(int groupPosition) {
        return categorylist.get(groupPosition); // return object of the request index.
    }

    @Override
    public int getGroupCount() {
        return categorylist.size(); // size of the main array.
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        CategoryAerobic ca = (CategoryAerobic) getGroup(groupPosition); // object of the main array. (parent array)
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.group_row, null);


        Log.d( "in group view","here in group view");

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(ca.getName().trim());

        return view; // inflating  the view of the parent array.
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private void Continue(){
        final AlertDialog.Builder builder = new AlertDialog.Builder( context);
        builder.setMessage("Do you want to start  this Exercise?");
        builder.setCancelable( true );

        // in this case the user wanted to update the height and weight.
        builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
String things = "exercise"+";"+categoryname+";"+imagelink+";"+outercategoryname;
Log.d( "FdfddfTurkey",things );
                Intent in = new Intent( context,FitnessSession.class );
in.putExtra( "flow",things );

                Bundle extra = new Bundle(  );
//                extra.putString("state", categoryname);
//                extra.putString("info", imagelink);
//                in.putExtras(extra);
                context.startActivity( in );
            }

        } );
        builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        } );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}