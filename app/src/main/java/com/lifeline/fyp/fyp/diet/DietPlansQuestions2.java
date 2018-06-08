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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
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

public class DietPlansQuestions2 extends AppCompatActivity {

    ScrollView svh, svw, sva;
    TextView activity;
    Boolean check1,check2;
    Retrofit retrofit;
    Api api;
    String email,pname;
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    private String userheight,userweight,allinformation,state;
    Call<MemberObject> call;
    private  Spinner spinner;
    private int age;
    private String pinfo;
    private CircleImageView circulartooltiph;
    private CircleImageView nameplan;
    private double value;
    private String []sep;
    private RelativeLayout q1,q2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diet_plans_questions );

        activity = (TextView) findViewById( R.id.qtagactivity );


        // calling the spinner;
        Spinner();

        // intent;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            allinformation = extras.getString("info");
//        }
//

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        allinformation = extras.getString("info");
        pname = extras.getString("pname");
        state = extras.getString("state");

        sep = allinformation.split( ";" );

        if(sep[0].equals( "!true" )){
            activity.setText( "1 of 1" );
        }



        // Toolip intialization
        circulartooltiph = (CircleImageView) findViewById( R.id.tipactivity );

//        // Creating plan.
//        q1 = (RelativeLayout) findViewById( R.id.question1 );
//        q2 = (RelativeLayout) findViewById( R.id.question2 );
//
//
//
//        q1.setVisibility( View.VISIBLE );
//        q2.setVisibility( View.INVISIBLE);

    }


    public void createplan(View view) {

            Log.d( "allinformation",allinformation);
            Log.d( "allinformation", String.valueOf( value ) );
//
//            q1.setVisibility( View.INVISIBLE );
//            q2.setVisibility( View.VISIBLE );


        try{

            if(state.equals( "fitness" )){
                Intent intent = new Intent( this, CreatedPlan.class );
                Log.d( "bugtime",pname);
                Bundle extra = new Bundle(  );
                extra.putString("information", allinformation);
                extra.putString("planname",pname );
                extra.putString( "state",state );
                intent.putExtras(extra);
                finish();
                startActivity(intent);


            }
            else {

//                Intent intent = new Intent( this, FinalzingPlan.class );
//                intent.putExtra( "information", allinformation ); // combination contains boolean+gender+age.
//                finish();
//                startActivity( intent );
//                Log.d( "bugtime",allinformation );

                Intent intent = new Intent(DietPlansQuestions2.this, FinalzingPlan.class);
                Bundle extra = new Bundle();
                extra.putString("state", state);
                extra.putString("information", allinformation);
                extra.putString("pname", pname);


                intent.putExtras(extra);
                finish();
                startActivity(intent);

            }
        }

        catch (NullPointerException e){
            Intent intent = new Intent( this, FinalzingPlan.class );
            intent.putExtra( "information", allinformation ); // combination contains boolean+gender+age.
            finish();
            startActivity( intent );
            Log.d( "bugtime",allinformation );
        }



    }




    public  void Spinner(){
    // setting spinner.
     spinner = (Spinner) findViewById( R.id.activity );
    final List<String> spinnerArray = new ArrayList<String>();


    final ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>( DietPlansQuestions2.this,
            R.layout.spinner_item, getResources().getStringArray( R.array.activityname ) );

    ageAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item );

    spinner.setAdapter( ageAdapter );
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {



             if(spinner.getSelectedItem().toString().equals( "Lightly Active" )){
                value =1.375;

                allinformation =allinformation+";"+value;
            }


            else if(spinner.getSelectedItem().toString().equals( "Moderatetely Active" )){
                value =1.55;

                allinformation =allinformation+";"+value;
            }


            else if(spinner.getSelectedItem().toString().equals( "Very Active" )){
                value =1.725;

                allinformation =allinformation+";"+value;
            }


            else if(spinner.getSelectedItem().toString().equals( "Extra Active" )){
                value =1.9;

                allinformation =allinformation+";"+value;
            }





        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }
    });

    }

    public void Tooltipmessage(View view) {

        String tipactivity = "Sedentary (Little or no exercise) " +
                "\n   Lightly active (light exercise/sports 1-3 days/week)" +
                "\n Moderatetely active (moderate exercise/sports 3-5 days/week)" +
                " \n Very active (hard exercise/sports 6-7 days a week)" +
                "\n Extra active (very hard exercise/sports & physical job or 2x training)";

        String name = "Name your plan.";
        switch (view.getId()) {

            case R.id.tipactivity:
                showToolTip( view, Gravity.CENTER, tipactivity );
                break;
//
//            case R.id.ptip:
//                showToolTip( view,Gravity.TOP,name );
//                break;

        }
    }


    public void showToolTip(View v, int gravity, String tip) {
        CircleImageView civ = (CircleImageView) v;
        Tooltip tooltip = new Tooltip.Builder( civ ).
                setText( tip )
                .setTextColor( Color.WHITE ).setGravity( gravity )
                .setCornerRadius( 8f ).setDismissOnClick( true ).show();
    }

}


