package com.lifeline.fyp.fyp.Signup;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.tooltip.Tooltip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUp extends AppCompatActivity{

     private EditText firstName,lastName;
     private Button button;
     private String name;
    CircleImageView circulartooltip;
    CircleImageView circulartooltip2;
        boolean settingfname,settinglname;

    // Text watcher , used for reading an input dynamically.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        circulartooltip = (CircleImageView) findViewById( R.id.tip );
        circulartooltip2 = (CircleImageView) findViewById( R.id.tip2 );

        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);

        button = (Button) findViewById(R.id.loginsup);



        firstName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^([a-zA-Z]{3,20})([ ]?[a-zA-Z][a-zA-Z][a-zA-Z][a-zA-Z]{0,7}){0,1}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                firstName.setTextColor( Color.RED );
                    settingfname = false;
                    button.setVisibility(View.VISIBLE);

                } else {
                    firstName.setTextColor( Color.BLACK );
                    settingfname= true;
                }
            }
        });


        lastName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^([a-zA-Z]{3,20})([ ]?[a-zA-Z][a-zA-Z][a-zA-Z][a-zA-Z]{0,7}){0,1}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    lastName.setTextColor( Color.RED );
                    settinglname = false;
                    button.setVisibility(View.VISIBLE);

                } else {

                    lastName.setTextColor( Color.BLACK );
//                    if(settingfname){
//                         button.setVisibility(View.VISIBLE);
//
//                    }
                    settinglname = true;
                }

            }
        });

    }

    public  void signup2(View view){


        if(TextUtils.isEmpty(firstName.getText()) && (TextUtils.isEmpty(lastName.getText()))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        }

            else if(((!settingfname) && settinglname)  ||  ((!settinglname) && settingfname)    || ((!settinglname) && (!settingfname))){
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

        }

        else if (settingfname && settinglname){
            String fname = firstName.getText().toString();
            String lname = lastName.getText().toString();

            fname = fname.substring( 0,1 ).toUpperCase() + fname.substring( 1 ).toLowerCase();

            lname = lname.substring( 0,1 ).toUpperCase() + lname.substring( 1 ).toLowerCase();

            name = fname+";"+lname;

            Intent intent = new Intent(this,SignUp2.class);
            intent.putExtra("fullname",name);
            Log.d("name",name);

            finish();
          startActivity(intent);
        }
    }

    public void Tooltip(View view){

        String firstnametip = "First name should minimum have 3 letters with an optional space.";
        String lastnametip = "Last name should have 3 letters or more.";
        switch (view.getId()){

            case R.id.tip:
                showToolTip( view,Gravity.TOP,firstnametip);
                break;
            case R.id.tip2:
                showToolTip( view,Gravity.TOP,lastnametip);
                break;
        }

    }

    public void showToolTip(View v, int gravity,String tip){
        CircleImageView civ = (CircleImageView)v;
        Tooltip tooltip = new Tooltip.Builder(civ).
                setText(tip)
                .setTextColor( Color.WHITE ). setGravity( gravity)
                .setCornerRadius( 8f ).setDismissOnClick( true ).show();
    }

}
