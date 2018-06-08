package com.lifeline.fyp.fyp.diet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.DetailedSkinTip;
import com.lifeline.fyp.fyp.lifestyle.SkinAdapter;
import com.lifeline.fyp.fyp.models.FoodCategoryDescription;
import com.lifeline.fyp.fyp.models.SkinTip;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 2/9/2018.
 */

public class MealTrackingAdapter extends RecyclerView.Adapter<MealTrackingAdapter.MealHolder> {


    public ArrayList<FoodCategoryDescription> mModelList;
    private Context mContext;
    private String checking;

    public MealTrackingAdapter(ArrayList<FoodCategoryDescription> mModelList,Context context) {
        this.mModelList = mModelList;
        this.mContext = context;
    }


    @Override
    public MealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mealtracking_view   , parent, false);
            return  new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(final MealHolder holder, final int position) {
         final FoodCategoryDescription model = mModelList.get(position);
           holder.mch.setText(model.getName());
          holder.mct.setText(model.getName());

        holder.cvfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Log.d("name",mModelList.get( position).getName());
                PassData( position );


            }
        });


    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }


    // fetching the value and displaying the clicked item.
    private void PassData(int position){
        Intent intent = new Intent( mContext,CategoricallyFoodItems.class);
        intent.putExtra( "clickedtip",mModelList.get( position).getName());
        mContext.startActivity(intent);
    }

    public class MealHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView mch; // sdn = selected diet name.
        private TextView mct; // sdc = selected diet cal.
        private TextView clickeditem; // clickeditem
        private CardView cvfc; //  card item , food item.



        public MealHolder(View itemView) {
            super(itemView);
            view = itemView;
            mch = (TextView) itemView.findViewById( R.id.mealcategoryheading);
            mct = (TextView) itemView.findViewById(R.id.mealcategorytext);
            cvfc = (CardView) itemView.findViewById(R.id.cardmealcategories);
        }
    }

}

