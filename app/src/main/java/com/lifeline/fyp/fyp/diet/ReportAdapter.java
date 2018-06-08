package com.lifeline.fyp.fyp.diet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
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


 public class ReportAdapter extends BaseExpandableListAdapter {

    private Context context;   // context of the actibviy passed.
    private ArrayList<ReportModel> continentList; // passing all the data into this list. array of the major item.
    private ArrayList<ReportModel> originalList; // passing all the data into this list. array of the major item.
    AlertDialog.Builder builder;
    private TextView amt,cal,pro,fat,carbo;

    public ReportAdapter(Context context, ArrayList<ReportModel> continentList) {

        this.context = context;  // intialization.
        this.continentList = new ArrayList<ReportModel>(); // intialization
        this.continentList.addAll(continentList); // adding the items to the new array.
        this.originalList = new ArrayList<ReportModel>(); // intialization
        this.originalList.addAll(continentList); // adding the items to the new array
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<FoodEaten> countryList = continentList.get(groupPosition).getReports(); // child array. getting the elements of the child by index number and get method.
        return countryList.get(childPosition); // return the specific child item.
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition; // returning the child position.
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final FoodEaten country = (FoodEaten) getChild(groupPosition, childPosition); // getting the specific number.
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate( R.layout.child_row, null); // inlfating the view.


        final TextView name = (TextView) view.findViewById(R.id.fooditemname);
        name.setText(country.getName().trim());
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<FoodEaten> countryList = continentList.get(groupPosition).getReports();
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

        ReportModel continent = (ReportModel) getGroup(groupPosition); // object of the main array. (parent array)
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.reportadapter, null);


        Log.d( "in group view","here in group view");

        TextView cal = (TextView) view.findViewById(R.id.totalcal);
        TextView date = (TextView) view.findViewById(R.id.dateday);
        cal.setText("Calories Taken: "+continent.getTotalcalories().trim());
        date.setText("Date: "+continent.getDaydate().trim());

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


}