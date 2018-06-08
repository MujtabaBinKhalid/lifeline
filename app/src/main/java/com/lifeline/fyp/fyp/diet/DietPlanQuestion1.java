package com.lifeline.fyp.fyp.diet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.HeightQuestion;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.BMIObject;
import com.lifeline.fyp.fyp.retrofit.responses.FitnessResponseObject;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietPlanQuestion1 extends AppCompatActivity {

    private TextView height, weight;
    private boolean check1, check2;
    Double textheight;

    private CircleImageView circulartooltiph;
    private CircleImageView circulartooltipw;
    private CircleImageView circulartooltiph1;
    private int fitnessiduser;
    private boolean vicevirsa;
    private TextView btnchange;
    private LinearLayout l1,l2;
    Spinner spinner;
    int convertedheight;
    String selectedvalue;
    String [] heightsep;

    String pinfo,state,pname,weeks,fitnessId,id,email,plannamefitness,weeksfitness,date;
 Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diet_plan_question1 );
vicevirsa = true;
        l1 = (LinearLayout) findViewById( R.id.toolbarlast );
        l2 = (LinearLayout) findViewById( R.id.toolbarlast1 );
btnchange = (TextView) findViewById( R.id.change );

btnchange.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View view) {

          if(vicevirsa == true){
              l1.setVisibility( View.INVISIBLE );
              l2.setVisibility( View.VISIBLE );
              vicevirsa =false;
          }
          else if (vicevirsa == false){
              l2.setVisibility( View.INVISIBLE );
              l1.setVisibility( View.VISIBLE );
              vicevirsa =true;
          }
    }
} );
 spinner = (Spinner)findViewById(R.id.height1);

        // intent;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            pinfo = extras.getString("info");
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
         api = retrofit.create( Api.class );



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pinfo = extras.getString("info");
        state = extras.getString("state");
        pname = extras.getString("pname");
        weeks = extras.getString( "weeks" );
        fitnessId = extras.getString( "fitnessId" );
        email = extras.getString( "useremail" );
        id = extras.getString( "userid" );

        Log.d( "sdsd",state );

        // Toolip intialization
        circulartooltiph = (CircleImageView) findViewById( R.id.tipheight );
        circulartooltiph1 = (CircleImageView) findViewById( R.id.tipheight1 );
        circulartooltipw = (CircleImageView) findViewById( R.id.tipweight );

        // text views of the second layout.
        height = (TextView) findViewById( R.id.height );
        weight = (TextView) findViewById( R.id.weight );

        Validation();
        fillingSpinner();

    }



        /// validation of the text fields.
    public void Validation() {

        // for the weight text field.
        weight.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[1-9][0-9]{1,2}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    weight.setTextColor( Color.RED );
                    check1 = false;
                }
                    else if (Integer.parseInt( editable.toString() ) < 40){
                    weight.setTextColor( Color.RED );
                    check1 = false;
                }

                else {
                    weight.setTextColor( Color.BLACK );
                    check1 = true;
                }
            }
        } );


        height.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[1-9][0-9]{2}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    height.setTextColor( Color.RED );
                    check2 = false;

                } else {
                    height.setTextColor( Color.BLACK );
                    check2 = true;
                }
            }
        } );

    }

    public void Tooltipmessage(View view) {

        String weighttip = "Weight could'nt be less then 40kg(s).";
        String heighttip = "Height could'nt be less then 100cm(s)";
        String heighttip1 = "select your height.";
        switch (view.getId()) {

            case R.id.tipweight:
                showToolTip( view, Gravity.TOP, weighttip );
                break;
            case R.id.tipheight:
                showToolTip( view, Gravity.TOP, heighttip );
                break;
            case R.id.tipheight1:
                showToolTip( view, Gravity.TOP, heighttip1 );
                break;
        }

    }


    public void showToolTip(View v, int gravity, String tip) {
        CircleImageView civ = (CircleImageView) v;
        Tooltip tooltip = new Tooltip.Builder( civ ).
                setText( tip )
                .setTextColor( Color.WHITE ).setGravity( gravity )
                .setCornerRadius( 8f ).setDismissOnClick( true ).show();
    }

    // calculating


    public void calculating(final View view) {

        Log.d( "dfdfValue", String.valueOf( vicevirsa ) );

        if (vicevirsa == false){

            heightsep = selectedvalue.split( "'" );
            convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                    Integer.parseInt( heightsep[1] ) * 3
                    ) ;

            Log.d( "COnvetretedhehight", String.valueOf( convertedheight ) );
        }



        if (TextUtils.isEmpty( weight.getText() ) && (TextUtils.isEmpty( height.getText() ))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        } else if (((!check1) && check2) || ((!check2) && check1) || ((!check2) && (!check1))) {
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

        } else if (check1 && check2) {
            Double textweight = Double.parseDouble( weight.getText().toString() );
          if (vicevirsa == true){
               textheight = Double.parseDouble( height.getText().toString() );
          }




            // IF YOU WANT TO CREATE DIET WITH FITNESS PLAN.
             if(state.equals( "Onlydiet" )){
                 double value = 1.2;

                 // height in foot inches
                 if (vicevirsa == false){

                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     pinfo = pinfo+";"+convertedheight+";"+textweight+";"+value;

                     Log.d( "COnvetretedhehight", String.valueOf( convertedheight ) );
                 }
                 // height in cms.
                 else if (vicevirsa == true){
                     pinfo = pinfo+";"+textheight+";"+textweight+";"+value;

                 }


                 Intent intent = new Intent(DietPlanQuestion1.this, FinalzingPlan.class);


                 Bundle extra = new Bundle(  );

                 extra.putString("state", state);
                 extra.putString("information", pinfo);
                 extra.putString("pname", pname);


                 intent.putExtras(extra);
                 finish();
                 startActivity(intent);

             }

             // IF YOU WANT TO PERFORM AEROBIC EXERCISES.
             else if (state.equals( "aerobic" )){

                 if (vicevirsa == false ){
                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( convertedheight ) , String.valueOf( textweight.intValue() ) );
                     updateMember(member);
                 }
                 else if (vicevirsa == true ){
                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( textheight ) , String.valueOf( textweight.intValue() ) );

                     updateMember(member);
                 }

Log.d( "erovix","erereobics" );
Log.d( "erovix",id );
Log.d( "erovix",email );
             }


             // RUnning or Cycling

             else if (state.equals( "running" )){

                 if (vicevirsa == false ){
                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( convertedheight ) , String.valueOf( textweight.intValue() ) );
                     updateMember(member);
                 }

                 else if (vicevirsa == true){
                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( textheight ) , String.valueOf( textweight.intValue() ) );

                     updateMember(member);

                 }
                 Log.d( "erovix","Egggr" );
                 Log.d( "erovix",id );
                 Log.d( "erovix",email );

                 Call<FitnessResponseObject>   call2 = api.checkingfitnessplan( Integer.parseInt( id ) );
                 call2.enqueue( new Callback<FitnessResponseObject>() {
                     @Override
                     public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                         Log.d( "FITNESSID", String.valueOf( response.body().getData().getFitnessPlanId() ) );
                         fitnessiduser = response.body().getData().getFitnessPlanId();
                         plannamefitness = response.body().getData().getName();
                         weeksfitness = response.body().getData().getWeeks()+"";
                         date = response.body().getData().getDate();
                     }

                     @Override
                     public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                     }
                 });
                 }

             else if (state.equals( "cycling" )){


                 if (vicevirsa == false ){
                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( convertedheight ) , String.valueOf( textweight.intValue() ) );
                     updateMember(member);
                 }

                 else if (vicevirsa == true){
                     Member member  = new Member( Integer.parseInt( id ), email, email,  String.valueOf( textheight ) , String.valueOf( textweight.intValue() ) );

                     updateMember(member);

                 }

                 Log.d( "erovix","Egggr" );
                 Log.d( "erovix",id );
                 Log.d( "erovix",email );
                 Call<FitnessResponseObject>   call2 = api.checkingfitnessplan( Integer.parseInt( id ) );
                 call2.enqueue( new Callback<FitnessResponseObject>() {
                     @Override
                     public void onResponse(Call<FitnessResponseObject> call, Response<FitnessResponseObject> response) {
                         Log.d( "FITNESSID", String.valueOf( response.body().getData().getFitnessPlanId() ) );
                         fitnessiduser = response.body().getData().getFitnessPlanId();
                         Log.d( "ffgfgf", String.valueOf( fitnessiduser ) );
                     }

                     @Override
                     public void onFailure(Call<FitnessResponseObject> call, Throwable t) {

                     }
                 });
             }



             // IF YOU WANT TO CREATE FITNES WITH DIET PLAN.
             else if(state.equals( "Onlyfit" )){
                 double value = 1.2;

                 // height in foot inches
                 if (vicevirsa == false){

                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     pinfo = pinfo+";"+convertedheight+";"+textweight+";"+value;

                     Log.d( "COnvetretedhehight", String.valueOf( convertedheight ) );
                 }
                 // height in cms.
                 else if (vicevirsa == true){
                     pinfo = pinfo+";"+textheight+";"+textweight+";"+value;

                 }

                 Intent intent = new Intent(DietPlanQuestion1.this, CreatedPlan.class);


                 Bundle extra = new Bundle(  );

                 extra.putString("information", pinfo);
                 extra.putString("weeks",weeks);
                 extra.putString("state", state);
                 extra.putString("info", pinfo);
                 extra.putString("fitnessId", fitnessId+"");
                 extra.putString("planname", pname);






                 intent.putExtras(extra);
                 finish();
                 startActivity(intent);

             }



             else {
                 double value = 1.2;

                 // height in foot inches
                 if (vicevirsa == false){

                     heightsep = selectedvalue.split( "'" );
                     convertedheight = (Integer.parseInt( heightsep[0] )*30) +(
                             Integer.parseInt( heightsep[1] ) * 3
                     ) ;

                     pinfo = pinfo+";"+convertedheight+";"+textweight;

                     Log.d( "COnvetretedhehight", String.valueOf( convertedheight ) );
                 }
                 // height in cms.
                 else if (vicevirsa == true){
                     pinfo = pinfo+";"+textheight+";"+textweight;

                 }

                 Intent intent = new Intent(DietPlanQuestion1.this, DietPlansQuestions2.class);
                 //intent.putExtra( "info",pinfo );
                 Bundle extra = new Bundle(  );
                 extra.putString("state", state);
                 extra.putString("info", pinfo);
                 extra.putString("pname", pname);


                 intent.putExtras(extra);
                 finish();
                 startActivity(intent);

                 Log.d( "bugtime",pinfo );
                 //  finish();
                 // startActivity(intent);

             }







        }



    }

    private void updateMember(Member member){
        Call<MemberObject> call4 = api.updateUser( Integer.parseInt( id ),member);
        call4.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
Log.d( "hogya bhai", String.valueOf( response ) );

String combo = "running"+";"+fitnessiduser+";"+plannamefitness+";"+weeksfitness+";"+date+";"+weight.getText().toString();
String combo1 = "cycling"+";"+fitnessiduser+";"+plannamefitness+";"+weeksfitness+";"+date+";"+weight.getText().toString();
   if (state.equals( "aerobic" )){
       Intent intent = new Intent(DietPlanQuestion1.this, FitnessSession.class);
       intent.putExtra( "flow","fromdiet;" );
       startActivity( intent );
   }

   else if (state.equals( "running" )){
       Intent intent = new Intent(DietPlanQuestion1.this, FitnessSession.class);
       intent.putExtra( "flow",combo );
       startActivity( intent );
   }

   else if (state.equals( "cycling" )){
       Intent intent = new Intent(DietPlanQuestion1.this, FitnessSession.class);
       intent.putExtra( "flow",combo1 );
       startActivity( intent );
   }


            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }
        } );


    }

    private void fillingSpinner(){

        final List<String> spinnerheight =  new ArrayList<String>();
        for (int i=5; i<10;i++){
            spinnerheight.add("4'" + i);
        }
        for (int i=0; i<10;i++){
            spinnerheight.add("5'" + i);
        }
        for (int i=0; i<10;i++){
            spinnerheight.add("6'" + i);
        }
        for (int i=0; i<5;i++){
            spinnerheight.add("7'" + i);
        }


        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(DietPlanQuestion1.this,
                R.layout.spinner_item,spinnerheight);

        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(ageAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedvalue=  spinner.getSelectedItem().toString();
                check2= true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
}

