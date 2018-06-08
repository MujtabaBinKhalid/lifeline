package com.lifeline.fyp.fyp.diet;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.CheckingCalories;
import com.lifeline.fyp.fyp.models.FetchingCalorie;
import com.lifeline.fyp.fyp.models.FoodArray;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Mujtaba_khalid on 2/15/2018.
 */

public class AllFoodIemsAdapter extends RecyclerView.Adapter<AllFoodIemsAdapter.FoodHolder>{


    public ArrayList<FetchingCalorie> mModelList;
    private Context context;




    public AllFoodIemsAdapter(Context context, ArrayList<FetchingCalorie> modelList) {
        this.context = context;
        mModelList = modelList;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.allfoodsessionitems, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodHolder holder, final int position) {
        final FetchingCalorie model = mModelList.get(position);
        holder.name.setText(model.getName());
        holder.calories.setText( model.getCalories() );

        holder.rl.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mModelList.remove( position );
                notifyDataSetChanged();
                Toast.makeText( context, "Item Deleted", Toast.LENGTH_SHORT ).show();
                return true;
            }
        } );

    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }


    public class FoodHolder extends RecyclerView.ViewHolder {

        private TextView name; // sdn = selected diet name.
        private Button fats;
        private Button proteins;
        private Button carbohydrates;
        private Button calories;
        private RelativeLayout rl;


        public FoodHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById( R.id.itemname );
            calories = (Button) itemView.findViewById( R.id. caloriebtn);
            rl = (RelativeLayout) itemView.findViewById( R.id.rlcard );



        }
    }



}
