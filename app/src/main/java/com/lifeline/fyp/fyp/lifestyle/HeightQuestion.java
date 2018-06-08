package com.lifeline.fyp.fyp.lifestyle;

/**
 * Created by Mujtaba_khalid on 11/26/2017.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HeightQuestion extends AppCompatActivity {
    private String pinfo,selectedItem,combined,weighthecker; //weightchecker is used for checking the weight, is there or not.
    // fetching previous activity information.
    // to get the text of the spinner.
    // to merge all data.  // pinfo + selectedItem
    private String[] seperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heightquestion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }

        // seperating.
        seperator = pinfo.split(";");

        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtagheight );

        if(!seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "1 of 4" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "3 of 6" );
        }





            weighthecker =seperator[3];

       final Spinner spinner = (Spinner)findViewById(R.id.height);

        final List<String> spinnerheight =  new ArrayList<String>();
    for (int i=1; i<10;i++){
        spinnerheight.add("5'" + i);
    }
        for (int i=0; i<10;i++){
            spinnerheight.add("6'" + i);
        }


        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(HeightQuestion.this,
                R.layout.spinner_item,spinnerheight);

        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(ageAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                    selectedItem=  spinner.getSelectedItem().toString();
                combined = pinfo + ";" + selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
    public void Q4(View view) {



        if(seperator[0].equals("true")){
            Intent intent = new Intent(this, WeightQuestion.class);
            intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
            finish();
            startActivity(intent);
        }

        else if(!seperator[0].equals("true")){

            if(weighthecker.equals("null")){
                Intent intent = new Intent(this, WeightQuestion.class);
                intent.putExtra("information",combined); //combined == true;gender;age;hairtype
                finish();
                startActivity(intent);

            }

            else {
                Intent intent = new Intent(this, FashionCategoryQuestion.class);
                intent.putExtra("information",combined); //combined == true;gender;age;hairtype
                finish();
                startActivity(intent);

            }

        }
    }
}
