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
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeightQuestion extends AppCompatActivity {

    String pinfo,selectedItem,combined;
    // fetching previous activity information.
    // to get the text of the spinner.
    // to merge all data.  // pinfo + selectedItem

    private String[] seperator;
    private Api api;
    private Integer id;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weightquestion);

//        textview = (TextView) findViewById(R.id.qtagweight);
//        textview.setText("questions");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }

        seperator = pinfo.split(";");


        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtagweight );

        if(!seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "2 of 4" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "4 of 6" );
        }


        //    // setting up Retrofit.

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);


      final Spinner spinner = (Spinner)findViewById(R.id.weight);
        final List<String> spinnerArray =  new ArrayList<String>();

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(WeightQuestion.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.weightrange));

        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(ageAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedItem=  spinner.getSelectedItem().toString(); // recent clicked value.
                combined = pinfo + ";" + selectedItem; // combined == true;gender;age;hairtype;weight
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void Q5(View view) {

        if(seperator[0].equals("true") && (seperator[1].equals("hairStyle")) ){
            Intent intent = new Intent(this, FaceShapeQuestion.class);
            intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
            finish();
            startActivity(intent);
        }
        else if(seperator[0].equals("true") && (seperator[1].equals("clothes")) ){
            Intent intent = new Intent(this, FashionCategoryQuestion.class);
            intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
            finish();
            startActivity(intent);
        }

        else if (!seperator[0].equals("true")){
            if (seperator[1].equals("clothes")){
                Intent intent = new Intent(this, FashionCategoryQuestion.class);
                intent.putExtra("information",combined);// combined == true;gender;age;hairtype;weight
                finish();
                startActivity(intent);
            }

            // button clicked is hairstyle.
            else if(seperator[1].equals("hairStyle")){
//                // and facetype is not set.
//                if (seperator[4].equals("null")) {
//                    Intent intent = new Intent(this, FaceShapeQuestion.class);
//                    Toast.makeText(WeightQuestion.this, combined, Toast.LENGTH_SHORT).show();
//
//                    intent.putExtra("information", combined);  // combined == btn;gender;weight;facetype;hairtype;weight
//                    startActivity(intent);
//                }
//
//                //and facetype is not set so asking face type.
//                else if (!seperator[4].equals("null")) {
//
//                    Toast.makeText(WeightQuestion.this, combined, Toast.LENGTH_SHORT).show();
//                    id = Integer.parseInt(seperator[0]);
//                    // when weight is not there but , facetype is null.
//                    Member updatedmember = new Member(selectedItem,seperator[4],seperator[5]);
//                    Call<MemberObject> call = api.updateUser(id,updatedmember);
//                    call.enqueue(new Callback<MemberObject>() {
//                        @Override
//                        public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
//                            final Intent intent = new Intent(WeightQuestion.this, LifeStylesuggestions.class);
//
//                            Log.d("ronaldo", String.valueOf(response));
//                            Log.d("ronaldo", String.valueOf(response.body()));
//                            Log.d("ronaldo", String.valueOf(response.errorBody()));
//
//                            Toast.makeText(WeightQuestion.this, combined, Toast.LENGTH_SHORT).show();
//                            intent.putExtra("information",combined); // sending info.
//                            startActivity(intent);
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<MemberObject> call, Throwable t) {
//                            Log.d("errormessageregistrat", t.getMessage() );
//
//                            Toast.makeText(WeightQuestion.this,"Try again, some thing went wrong.Hair style",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }




            }

        }


    }


