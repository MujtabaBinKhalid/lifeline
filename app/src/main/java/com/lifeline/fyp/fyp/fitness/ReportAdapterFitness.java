package com.lifeline.fyp.fyp.fitness;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeline.fyp.fyp.R;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 3/13/2018.
 */

public class ReportAdapterFitness extends RecyclerView.Adapter<ReportAdapterFitness.AdapterHolder> {

    private ArrayList<FitnessObject> mModelList;


    public ReportAdapterFitness(ArrayList<FitnessObject> mModelList) {
        this.mModelList = mModelList;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fitness_report_layout , parent, false);
        return new AdapterHolder( view );
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {

        final FitnessObject model = mModelList.get(position);
    double calorie = Double.parseDouble( mModelList.get( position ).getDistance()) ;
    double finalcalories = calorie*3/2;
    String strvalue = String.valueOf( finalcalories );
        Log.d("dfdfdfdf", String.valueOf( calorie ) );

        holder.distance.setText(model.getDistance().substring( 0,3 ) + " meters");
        holder.speed.setText( model.getSpeed().substring( 0,3 )+ " m/sec" );
        holder.time.setText( model.getTime().substring( 0,3 )+ "mints" );
        holder.cal.setText(strvalue.substring( 0,3 )+ " cal" );

        holder.date.setText( model.getDate().substring( 0,4 )+", "+ model.getDate().substring( 4,10 )+ " "+model.getDate().substring( 30,34 ));
     }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {

      TextView date,distance,speed,time,cal;
        public AdapterHolder(View itemView) {
            super( itemView );

            date = (TextView) itemView.findViewById( R.id.datetext );
            distance = (TextView) itemView.findViewById( R.id.text1 );
            speed = (TextView) itemView.findViewById( R.id.text2 );
            time = (TextView) itemView.findViewById( R.id.text3 );
            cal = (TextView) itemView.findViewById( R.id.text4 );
        }
    }
}
