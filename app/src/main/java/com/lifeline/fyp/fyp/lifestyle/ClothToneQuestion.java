package com.lifeline.fyp.fyp.lifestyle;

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
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;

public class ClothToneQuestion extends AppCompatActivity {

    private String pinfo,selectedItem,combined;

    private String[] seperator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_tone_question);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information"); // information contains boolean+gender+age+lifestylecategory.
        }

         seperator = pinfo.split(";");


        TextView tag = (TextView) findViewById( R.id.qtagcolor );

        if(seperator[0].equals( "true" ) && seperator[1].equals( "footwear" )){
            tag.setText( "3 of 3" );
        }

        else if(!seperator[0].equals( "true" ) && seperator[1].equals( "footwear" )){
            tag.setText( "2 of 2" );
        }
        final Spinner spinner = (Spinner) findViewById(R.id.clothtone);


        ArrayAdapter<String> clothtone = new ArrayAdapter<String>(ClothToneQuestion.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.cloth_tone));


        // setting the desired drop down and connecting it to adapter.
        clothtone.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(clothtone);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {



                selectedItem = spinner.getSelectedItem().toString();
                    if(selectedItem.equals( "Light Colors" )){
                        combined = pinfo + ";" + "light";
                    }

                    else if(selectedItem.equals("Dark Colors")){
                        combined = pinfo + ";" + "dark";

                    }
             }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void Qsuggestions(View view){


            Intent intent = new Intent(this, ShoeTone.class);
            intent.putExtra("information",combined);

            finish();


           startActivity(intent);


    }
}
