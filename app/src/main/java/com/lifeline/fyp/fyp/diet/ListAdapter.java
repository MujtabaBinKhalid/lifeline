package com.lifeline.fyp.fyp.diet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.FoodCategory;
import com.lifeline.fyp.fyp.models.FoodItems;


public class ListAdapter extends BaseExpandableListAdapter {

    private Context context;   // context of the actibviy passed.
    private ArrayList<FoodCategory> continentList; // passing all the data into this list. array of the major item.
    private ArrayList<FoodCategory> originalList; // passing all the data into this list. array of the major item.
    AlertDialog.Builder builder;
    private TextView amt,cal,pro,fat,carbo,protext,fattext,carbotext,name;
    Dialog dialog;


    public ListAdapter(Context context, ArrayList<FoodCategory> continentList) {

        this.context = context;  // intialization.
        this.continentList = new ArrayList<FoodCategory>(); // intialization
        this.continentList.addAll(continentList); // adding the items to the new array.
        this.originalList = new ArrayList<FoodCategory>(); // intialization
        this.originalList.addAll(continentList); // adding the items to the new array
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<FoodItems> countryList = continentList.get(groupPosition).getFooditems(); // child array. getting the elements of the child by index number and get method.
        return countryList.get(childPosition); // return the specific child item.
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition; // returning the child position.
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final FoodItems country = (FoodItems) getChild(groupPosition, childPosition); // getting the specific number.
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate( R.layout.child_row, null); // inlfating the view.


        final TextView name = (TextView) view.findViewById(R.id.fooditemname);
        name.setText(country.getName().trim());

        name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Toast.makeText(context,name.getText(),Toast.LENGTH_SHORT).show();
                 Details(name.getText().toString(),country.getAmount().trim(),country.getCalorie().trim(),country.getProtein().trim(),country.getFat().trim(),country.getCarbohydrates().trim());
            }
        } );
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<FoodItems> countryList = continentList.get(groupPosition).getFooditems();
        return countryList.size(); // size of the child of the main item.

    }

    @Override
    public Object getGroup(int groupPosition) {
        return continentList.get(groupPosition); // return object of the request index.
    }

    @Override
    public int getGroupCount() {
        return continentList.size(); // size of the main array.
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        FoodCategory continent = (FoodCategory) getGroup(groupPosition); // object of the main array. (parent array)
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_row, null);


        Log.d( "in group view","here in group view");

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(continent.getName().trim());

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

    public void filterData(String query){

        query = query.toLowerCase(); // convertingg the query to lower case.
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        continentList.clear(); // clearing the list.

        if(query.isEmpty()){
            continentList.addAll(originalList);
        }
        else {

            for(FoodCategory continent: originalList){ // outer looop.

                ArrayList<FoodItems> countryList = continent.getFooditems(); // this contains all the items.
                ArrayList<FoodItems> newList = new ArrayList<FoodItems>(); // new list
                for(FoodItems country: countryList){
                    if(country.getName().toLowerCase().contains(query)){
                        newList.add(country);
                    }
                }
                if(newList.size() > 0){
                    FoodCategory nContinent = new FoodCategory(continent.getName(),newList);
                    continentList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        notifyDataSetChanged();


    }


    public void Details(String nameitem,String amount, String calories, String proteins,String fats,String carbohydrates){

        Log.d( "ddfdfdfdfwwwww22",nameitem );

        dialog = new Dialog( context );
        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = inflater.inflate(R.layout.custommealtrackingdialog, null);
        // reference the textview of custom_popup_dialog

            builder.setTitle( nameitem +"" );



        name =(TextView)customView .findViewById( R.id.nameoftheitem );
        amt =(TextView)customView .findViewById( R.id.amount );
        cal =(TextView)customView .findViewById( R.id.calories );
        pro =(TextView)customView .findViewById( R.id.proteins );
        fat =(TextView)customView .findViewById( R.id.fats );
        carbo =(TextView)customView .findViewById( R.id.carbohydrates );
        protext =(TextView)customView .findViewById( R.id.protext );
        fattext =(TextView)customView .findViewById( R.id.fattext );
        carbotext =(TextView)customView .findViewById( R.id.carbotext );


        if (proteins.equals( "0 g" ) && fats.equals( "0 g" ) ){
            fat.setVisibility( View.INVISIBLE );
            carbo.setVisibility( View.INVISIBLE );
            fattext.setVisibility( View.INVISIBLE );
            carbotext.setVisibility( View.INVISIBLE );
            protext.setText( "Carbohydrates" );

            pro.setText( "" +carbohydrates  );
            amt.setText( ""+amount );
            cal.setText(calories+" cals" );

        }

        else if (proteins.equals( "0 g" )){
            carbo.setVisibility( View.INVISIBLE );
            carbotext.setVisibility( View.INVISIBLE );

            protext.setText( "Fats" );
            fattext.setText( "Carbohydates" );

            pro.setText( "" +fats  );
            amt.setText( ""+amount );
            cal.setText(calories+" cals" );
            fat.setText( carbohydrates );

        }

        else if (fats.equals( "0 g" )){
            carbo.setVisibility( View.INVISIBLE );
            carbotext.setVisibility( View.INVISIBLE );
            fattext.setText( "Carbohydates" );
            amt.setText( ""+amount );
            cal.setText(calories+" cals" );
            pro.setText( ""+proteins );
            fat.setText( ""+carbohydrates );


        }
else {

            amt.setText( ""+amount );
            cal.setText(calories+" cals" );
            pro.setText( ""+proteins );
            fat.setText( ""+fats );
            carbo.setText( "" +carbohydrates );

        }



        builder.setView(customView);
        dialog = builder.create();
        dialog.show();

        Button close = (Button) customView.findViewById(R.id.closepopup);
        builder.setCancelable( true );
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
               dialog.dismiss();
            }
        });

    }
}