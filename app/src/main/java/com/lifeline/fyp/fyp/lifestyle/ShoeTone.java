package com.lifeline.fyp.fyp.lifestyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;
import com.lifeline.fyp.fyp.retrofit.Api;

import java.util.ArrayList;

public class ShoeTone extends AppCompatActivity {
    private String pinfo, selectedItem, combined;
    private String[] seperator;
    private ArrayAdapter<String> shades;
    Api api;
    Integer id;
    ArrayList<String> shadestone = new ArrayList<>();
    private String moreinfo;
    Spinner spinner;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_shoe_tone );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }
        button2 = (Button) findViewById( R.id.button2 );

        button2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passingData();
            }
        } );

        final Spinner spinner = (Spinner)findViewById(R.id.height);
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(ShoeTone.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.shadestone));
        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(ageAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedItem=  spinner.getSelectedItem().toString().toLowerCase();
                combined = pinfo + ";" + selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }

    private void passingData(){

        Log.d( "SFWRwqwqwwqwq",combined );

        Intent intent = new Intent(this, CombinedSuggestions.class);
        intent.putExtra("information",combined); // sending info.
        finish();
        startActivity(intent);


    }
}
