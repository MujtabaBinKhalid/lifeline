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
import com.lifeline.fyp.fyp.lifestyle.suggestions.ClothSuggestions;


public class FashionCategoryQuestion extends AppCompatActivity {
    private String pinfo,selectedItem,combined;
    // fetching previous activity information.
    // to get the text of the spinner.
    // to merge all data.  // pinfo+buttontext
    // storing email.

    private String[] seperator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_category_question);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }
        seperator = pinfo.split(";");


        TextView tag = (TextView) findViewById( R.id.qtagfashion );

        // for footwear
        if(seperator[0].equals( "true" ) && seperator[1].equals( "footwear" )){
            tag.setText( "2 of 3" );
        }

        else if(!seperator[0].equals( "true" ) && seperator[1].equals( "footwear" )){
            tag.setText( "1 of 3" );
        }

        // for clothing tags.

        else if(!seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "1 of 1" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "5 of 6" );
        }
        else if(!seperator[0].equals( "true" ) && !seperator[5].equals( "null" )){
            tag.setText( "1 of 1" );
        }


        final Spinner spinner = (Spinner) findViewById(R.id.fashioncategory);


        ArrayAdapter<String> fashion = new ArrayAdapter<String>(FashionCategoryQuestion.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.fashion_design));


        // setting the desired drop down and connecting it to adapter.
        fashion.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(fashion);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedItem = spinner.getSelectedItem().toString().toLowerCase();
                combined = pinfo + ";" + selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

public void Qcolor(View view){


    if (seperator[0].equals("true") && seperator[1].equals("clothes")){
            Intent intent = new Intent(this, SkinColorQuestion.class);
            intent.putExtra("information",combined);

            Log.d("hete","1here");
        finish();
            startActivity(intent);
        }

        else if (seperator[0].equals("true") && seperator[1].equals("footwear")){
            Intent intent = new Intent(this, ClothToneQuestion.class);
            intent.putExtra("information",combined);
                Log.d("dgrt",combined);
            Log.d("hete","2here");
        finish();
            startActivity(intent);
        }

        else if (!seperator[0].equals("true")){

            if(seperator[1].equals("footwear")){
                Intent intent = new Intent(this, ClothToneQuestion.class);
                intent.putExtra("information",combined);

                finish();
                startActivity(intent);
            }
            // when he is asking for the first time.
            else if(seperator[5].equals("null") && seperator[1].equals("clothes")){
                Intent intent = new Intent(this, SkinColorQuestion.class);
                intent.putExtra("information",combined);

                Log.d("hete","4here");

                finish();
                startActivity(intent);
            }

            else if (!seperator[5].equals("null") && seperator[1].equals("clothes")) {
                combined = combined+";"+seperator[6]; // right combo making.
                Intent intent = new Intent(this, ClothSuggestions.class);
                intent.putExtra("information",combined);

                finish();
                startActivity(intent);
            }

        }

    }



    }
