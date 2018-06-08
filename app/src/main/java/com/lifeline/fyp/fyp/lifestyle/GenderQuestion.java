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

import java.util.ArrayList;
import java.util.List;


public class GenderQuestion extends AppCompatActivity {
    private String pinfo, selectedItem, combined,moreinfo; // moreinfo is used when user need more suggestions.
    private String[] seperator;


    // pinfo will be contain id;button+gender+weight+facetype;
    // selecteditem recent item.
    //combined combination.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genderquestion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information"); // information contains boolean+gender+age+lifestylecategory.
        }
        seperator = pinfo.split(";");

        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtaggendernum );

        if(seperator[0].equals( "true" ) && seperator[1].equals( "hairStyle" )){
            tag.setText( "1 of 3" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "sunglasses" )){
            tag.setText( "1 of 2" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "footwear" )){
            tag.setText( "1 of 3" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "1 of 6" );
        }






        final Spinner spinner = (Spinner)findViewById(R.id.gender);
        final List<String> spinnerArray =  new ArrayList<String>();

        ArrayAdapter<String> gender = new ArrayAdapter<String>(GenderQuestion.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.gender));

        gender.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(gender);

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
    public void Q3(View view) {

        if((seperator[0].equals("true")) && seperator[1].equals("sunglasses")){
            moreinfo = seperator[0]+";"+seperator[1]+";"+ selectedItem.toLowerCase()+";"+ selectedItem.toLowerCase();;

            Intent intent = new Intent(this, FaceShapeQuestion.class);
            intent.putExtra("information", moreinfo);  // combined == id;btn;gender;weight;facetype;hairtype
            Log.d("fygh",moreinfo);
            finish();
            startActivity(intent);

        }

        else if((seperator[0].equals("true")) && (seperator[1].equals("hairStyle"))){
            moreinfo = seperator[0]+";"+seperator[1]+";"+selectedItem.toLowerCase()+";"+selectedItem.toLowerCase()+";"+selectedItem.toLowerCase();
            Intent intent = new Intent(this, HairTypeQuestion.class);
            intent.putExtra("information", moreinfo);  // combined == id;btn;gender;
            Log.d("fygh",moreinfo);
            finish();
            startActivity(intent);
        }


        else if((seperator[0].equals("true")) && (seperator[1].equals("footwear"))){
            moreinfo = seperator[0]+";"+seperator[1]+";"+selectedItem.toLowerCase()+";"+selectedItem.toLowerCase();
            Intent intent = new Intent(this, FashionCategoryQuestion.class);
            intent.putExtra("information", moreinfo);  // combined == id;btn;gender;
            Log.d("fygh",moreinfo);
            finish();
            startActivity(intent);
        }

        else if((seperator[0].equals("true")) && (seperator[1].equals("clothes"))){
            moreinfo = seperator[0]+";"+seperator[1]+";"+selectedItem.toLowerCase()+";"+selectedItem.toLowerCase();
            Intent intent = new Intent(this, AgeQuestion.class);
            intent.putExtra("information", moreinfo);  // combined == id;btn;gender;
            Log.d("fygh",moreinfo);
            finish();
            startActivity(intent);
        }



//
//      else {
//            Intent intent = new Intent(this, AgeQuestion.class);
//            Toast.makeText(GenderQuestion.this, combined, Toast.LENGTH_SHORT).show();
//            intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
//            startActivity(intent);
//        }


    }
}
