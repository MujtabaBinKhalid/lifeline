package com.lifeline.fyp.fyp.lifestyle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.diet.Model;
import com.lifeline.fyp.fyp.models.SkinTip;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Mujtaba_khalid on 2/4/2018.
 */

public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.SkinHolder> {

    public ArrayList<SkinTip> mModelList;
    private String loginemail;
        private Context mContext;
        SharedPreferences sp;
   public  static  int number ;


    public SkinAdapter(ArrayList<SkinTip> mModelList, String loginemail, Context context) {
        this.mModelList = mModelList;
        this.loginemail = loginemail;
        this.mContext = context;
        this.number = number;
    }

    @Override
    public SkinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skintips_view, parent, false);
        return new SkinHolder(view);
    }

    @Override
    public void onBindViewHolder(final SkinHolder holder, final int position) {
        final SkinTip model = mModelList.get(position);
        holder.stheader.setText(model.getType());
        holder.stc.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                number = position;

                PassData( position );
               // fetchingvalue();
                sp=mContext.getSharedPreferences("position",MODE_PRIVATE);
                SharedPreferences.Editor e=sp.edit();
                e.putString("itemclicked",position+"");
                e.apply();

                Log.d( "click hua","sdsds" );

                Log.d( "clicked", String.valueOf( model.getId() ) );
//             Intent intent = new Intent( view.getContext(),DetailedSkinTip.class);
//
//
//                view.getContext().startActivity(intent);
                }
        });



    }

    private void PassData(int position)     {
        Intent intent = new Intent( mContext,DetailedSkinTip.class);
       // intent.putExtra("email",loginemail);

        intent.putExtra( "clickedtip",mModelList.get( position).getId());

        mContext.startActivity(intent);

    }



    public static  int fetchingvalue(){
        return number;
    }
    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class SkinHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView stheader; // skin tip header
        private CardView stc; // skin tip card

        public SkinHolder(View itemView) {
            super(itemView);
            view = itemView;
            stheader = (TextView) itemView.findViewById(R.id.skinplanname);
            stc = (CardView) itemView.findViewById(R.id.skintipcard);
        }

    }
}