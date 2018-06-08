package com.lifeline.fyp.fyp.lifestyle;

/**
 * Created by Mujtaba_khalid on 11/26/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;

import java.util.ArrayList;
import java.util.List;


public class AgeQuestion extends AppCompatActivity {
    private String pinfo, selectedItem, combined;
    private String[] seperator;

    // pinfo will be contain id;button+gender+weight+facetype;
    // selecteditem recent item.
    //combined combination.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agequestion);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information"); // information contains boolean+gender+age+lifestylecategory.
        }
        seperator = pinfo.split(";");

        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtagage );

        if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "2 of 6" );
        }



        final Spinner spinner = (Spinner)findViewById(R.id.age);
        final List<String> spinnerArray =  new ArrayList<String>();

        final ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(AgeQuestion.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.agegroups));

        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(ageAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedItem=  spinner.getSelectedItem().toString();
                combined = pinfo + ";" + selectedItem+";"+selectedItem+";"+selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void Q2(View view){

//        if(seperator[1].equals("hairStyle")){
//            Intent intent = new Intent(this, HairTypeQuestion.class);
//            Toast.makeText(AgeQuestion  .this, combined, Toast.LENGTH_SHORT).show();
//            intent.putExtra("information", combined); //combined == true;gender;age;hairtype
//            startActivity(intent);
//        }

        if(seperator[1].equals("clothes")){

            Intent intent = new Intent(this, HeightQuestion.class);
            intent.putExtra("information", combined); //combined == true;gender;age;hairtype
            finish();
            startActivity(intent);
        }

//        else if(seperator[1].equals("footwear")){
//            Intent intent = new Intent(this, FashionCategoryQuestion.class);
//            Toast.makeText(AgeQuestion  .this, combined, Toast.LENGTH_SHORT).show();
//            intent.putExtra("information", combined); //combined == true;gender;age;hairtype
//            startActivity(intent);
//        }






    }


}
