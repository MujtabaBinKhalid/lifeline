package com.lifeline.fyp.fyp.lifestyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.suggestions.ClothSuggestions;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.*;
import com.lifeline.fyp.fyp.retrofit.responses.LifeStyle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceShapeQuestion extends AppCompatActivity {
   private String pinfo,buttonText,combined,selectedItem;
   // fetching previous activity information.
      // to get the text of the spinner.
      // to merge all data.  // pinfo+buttontext
    // storing email.

    private Api api;
    private String [] seperator; // used to seperator the string (pinfo)
    private int id;
    private  Member updatedmember;
    ArrayList<String> facenames = new ArrayList<>(  );
    ArrayList<String> facepics = new ArrayList<>(  );
    Spinner spinner;
    Button but;
    ProgressDialog progressDialog;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(),facenames,facepics);
            if(facenames.size() > 0){
                spinner.setAdapter( spinnerAdapter );

            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {


                    selectedItem = spinner.getSelectedItem().toString(); // recent value fetched.
                    combined = pinfo + ";" + selectedItem; // combined == id;btn;gender;weight;facetype;hairtype
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });
            progressDialog.dismiss();

            but.setVisibility( View.VISIBLE );


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facetype);


        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Wait" );
        progressDialog.setCancelable( false );
        progressDialog.show();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }
        but = (Button) findViewById( R.id.button2 );
        but.setVisibility( View.INVISIBLE );
        Log.d("dfdfdfdfsfsdf",pinfo);

        // used to fetch all the info stored..
        seperator = pinfo.split(";");


        spinner = (Spinner) findViewById(R.id.facespinner);

        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtagface );

        if(!seperator[0].equals( "true" ) && seperator[1].equals( "hairStyle" )){
            tag.setText( "2 of 2" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "hairStyle" ) ){
            tag.setText( "3 of 3" );
        }

        else if(!seperator[0].equals( "true" ) && seperator[1].equals( "sunglasses" )){
            tag.setText( " 1of 2" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "sunglasses" ) ){
            tag.setText( "2 of 2" );
        }



        // setting up Retrofit.

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        FaceShapes();

    }


    public void Suggestions(View view){

        // more sunlgasses suggestions.
         if (seperator[0].equals("true") && seperator[1].equals("sunglasses")){

             Intent intent = new Intent(this, ShadesTone.class);
             intent.putExtra("information",combined); // sending info.
            finish();
             startActivity(intent);

         }
        // more hair style suggestions.
        if (seperator[0].equals("true") && seperator[1].equals("hairStyle")){
            Log.d( "aa22",combined );

            Intent intent = new Intent(this, HairStyleSuggestions.class);

            intent.putExtra("information",combined); // sending info.
            finish();

            Log.d( "dfdfdfsdfsdfdsfsdfsd",combined );


            startActivity(intent);

        }


        else if(!seperator[0].equals("true")){
            Log.d( "aa33",combined );

             id = Integer.parseInt(seperator[0]);


             // when weight , facetype are null , so normal flow.
             if(seperator[1].equals("hairStyle") && (seperator[3].equals("null"))){
                 Log.d( "aa44",combined );

                 Log.d( "ddcsd",combined );
                 Member updatedmember = new Member(selectedItem,seperator[5]);
                 Call<MemberObject> call = api.updateUser(id,updatedmember);
                 call.enqueue(new Callback<MemberObject>() {
                     @Override
                     public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

                         try{
                             Intent intent = new Intent(FaceShapeQuestion.this, HairStyleSuggestions.class);

                             intent.putExtra("information",combined); // sending info.
                             finish();
                             startActivity(intent);
                         }
                         catch (NullPointerException e){

                             Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();

                         }


                     }

                     @Override
                     public void onFailure(Call<MemberObject> call, Throwable t) {
                         Log.d("errormessageregistrat", t.getMessage() );

                         Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();
                     }
                 });
             }

             else if(seperator[1].equals("hairStyle") && (!seperator[3].equals("null"))){
                 Log.d( "aa55",combined );

                 Member updatedmember = new Member(selectedItem,seperator[5]);
                 Call<MemberObject> call = api.updateUser(id,updatedmember);
                 call.enqueue(new Callback<MemberObject>() {
                     @Override
                     public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                         Log.d("ronaldo", String.valueOf(response));
                         Log.d("ronaldo", String.valueOf(response.body()));
                         Log.d("ronaldo", String.valueOf(response.errorBody()));
                        try{
                            Intent intent = new Intent(FaceShapeQuestion.this, ClothSuggestions.class);
                            intent.putExtra("information",combined); // sending info.
                            finish();
                            startActivity(intent);
                        }
                        catch(NullPointerException e){
                            Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();

                        }



                     }

                     @Override
                     public void onFailure(Call<MemberObject> call, Throwable t) {
                         Log.d("errormessageregistrat", t.getMessage() );

                         Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();
                     }
                 });
             }



             else if(seperator[1].equals("sunglasses")){
                 Log.d( "aa66",combined );

                 Member updatedmember = new Member(selectedItem);
                 Call<MemberObject> call = api.updateUser(id,updatedmember);
                 call.enqueue(new Callback<MemberObject>() {
                     @Override
                     public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                         Log.d("ronaldo", String.valueOf(response));
                         try{
                             Intent intentsg = new Intent(FaceShapeQuestion.this, ShadesTone.class);
                             intentsg.putExtra("information",combined); // sending info.
                             finish();
                             startActivity(intentsg);

                         }
                                catch(NullPointerException e){
                                    Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();

                                }
                     }

                     @Override
                     public void onFailure(Call<MemberObject> call, Throwable t) {
                         Log.d("errormessageregistrat", t.getMessage() );

                         Toast.makeText(FaceShapeQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();
                     }
                 });


             }

             else {
                 Log.d( "aa199",combined );

                 Log.d("ronaldo", "some thing wrong" );

             }


         }

    }

    private void FaceShapes(){

            Call<LifeStyleResponse> faces = api.faceshapes();

            try {
                faces.enqueue( new Callback<LifeStyleResponse>() {
                    @Override
                    public void onResponse(Call<LifeStyleResponse> call, final Response<LifeStyleResponse> response) {

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {


                                List<LifeStyle> faces = response.body().getData();
                                for(int i=0; i< faces.size();i++){
                                    facenames.add( faces.get( i ).getVal1() );
                                    facepics.add( faces.get( i ).getVal2() );

                                }

                                handler.sendEmptyMessage( 0 );
                            }
                        };

                        Thread thread = new Thread(  runnable);
                        thread.start();
                        Log.d( "dfd", String.valueOf( facenames.size() ) );
                        Log.d( "dfd", String.valueOf( facepics.size() ) );






                    }

                    @Override
                    public void onFailure(Call<LifeStyleResponse> call, Throwable t) {

                    }
                });

            }
            catch(Exception e ){

            }
        }
}
