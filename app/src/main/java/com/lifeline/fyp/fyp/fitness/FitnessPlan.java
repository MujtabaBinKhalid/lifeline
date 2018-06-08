package com.lifeline.fyp.fyp.fitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.diet.DietPlanQuestion1;
import com.lifeline.fyp.fyp.diet.DietPlansQuestions2;
import com.lifeline.fyp.fyp.models.CreatingFitnessPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.OuterFitnessResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// the class where use when wil come when he will creating a plan for the first time.

public class FitnessPlan extends AppCompatActivity {

    EditText name,weeks;
    Button creating;
    boolean check1,check2;
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    private int UseriD,statuscode,fitnessId;
    private  String email,sc;
    Retrofit retrofit;
    Api api;
    String gender;
    Integer age;
    String planname;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fitness_plan );


         retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
         api = retrofit.create( Api.class );
        name = (EditText) findViewById( R.id.nameofplan );
        weeks = (EditText) findViewById( R.id.weeksnum );
        creating = (Button) findViewById( R.id.creating );

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        email = user.getEmail();

        creating.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PassingData();
                    }
                }
        );




       Validation();
        FetchingId();

    }

    /// validation of the text fields.
    public void Validation() {

        // for the weight text field.
        name.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[A-Z][A-Za-z ]{5,17}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    name.setTextColor( Color.RED );
                    check1 = false;

                } else {
                    name.setTextColor( Color.BLACK );
                    check1 = true;
                }
            }
        } );


        weeks.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[1-9]{1,2}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    weeks.setTextColor( Color.RED );
                    check2 = false;

                } else {
                    weeks.setTextColor( Color.BLACK );
                    check2 = true;
                }
            }
        } );

    }

    private void FetchingId(){



        final Call<MemberObject> call = api.fetchingUser( email );


        Runnable run = new Runnable() {
            @Override
            public void run() {
                call.enqueue( new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                        if (response.isSuccessful()) {
                            Log.d( "messi", String.valueOf( response ) );
                            Log.d( "messi", String.valueOf( response.body() ) );
                            statuscode = response.code();
                            sc = ""+statuscode;
                        }

                        Log.d( "statuscodeeror", String.valueOf( statuscode ) );


                        try {
                            UseriD = response.body().getData().getMemberId();
                            gender = response.body().getData().getGender();
                            age = response.body().getData().getAge();

                        } catch (NullPointerException e) {
                        }

                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                    }
                } );

            }
        };

        Thread thread = new Thread( run );
        thread.start();


    }

    private void PassingData(){
        Log.d( "sss", String.valueOf( UseriD ) );


        if (TextUtils.isEmpty( name.getText() ) && (TextUtils.isEmpty( weeks.getText() ))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        } else if (((!check1) && check2) || ((!check2) && check1) || ((!check2) && (!check1))) {
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

        } else if (check1 && check2) {

            CreatingFitnessPlan cfp = new CreatingFitnessPlan(UseriD,name.getText().toString(),
                    Integer.parseInt( weeks.getText().toString() ),"active");

            final Call<OuterFitnessResponse> call = api.fitness( cfp );


            Runnable runexcuting = new Runnable() {
                @Override
                public void run() {
                    call.enqueue( new Callback<OuterFitnessResponse>() {
                        @Override
                        public void onResponse(Call<OuterFitnessResponse> call, Response<OuterFitnessResponse> response) {

                            Log.d( "Sds", String.valueOf( response ) );
                            Log.d( "Sds",response.body().getMessage() );
                            Log.d( "Sds",response.message() );
                            Log.d( "elelelelleel", String.valueOf( response ) );

                            fitnessId = response.body().getData().getFitnessPlanId();
                            Log.d( "22dfdfdfdf", String.valueOf( fitnessId ) );



                        }

                        @Override
                        public void onFailure(Call<OuterFitnessResponse> call, Throwable t) {

                        }
                    } );

                }
            };

            Thread threadexcuting = new Thread( runexcuting );
            threadexcuting.start();




// asking additional question,

            // ONLY DIET IS USED WHEN THE USER WANTS TO REGISTER FITNESS PLAN WITH IT.
            final AlertDialog.Builder builder = new AlertDialog.Builder( FitnessPlan.this);
            builder.setMessage( "Do you want to link a diet plan with your fitness plan?" );
            builder.setCancelable( true );
            builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String info = "true"+";"+age+";"+gender;

                   planname = name.getText().toString();
                    Intent intent = new Intent(FitnessPlan.this, DietPlanQuestion1.class);
                    Bundle extra = new Bundle(  );

                    extra.putString("state", "Onlyfit");
                    extra.putString("info", info);
                    extra.putString("pname", planname);
                    extra.putString("fitnessId", fitnessId+"");
                    extra.putString("weeks",weeks.getText().toString());

                    Log.d( "dfdfdfdfQa", String.valueOf( fitnessId ) );


                    intent.putExtras(extra);
                    startActivity(intent);
                    finish();
                     }
            } );
            builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent( FitnessPlan.this,FitnessSession.class );
                    intent.putExtra( "flow","noexercise;" );
                    Log.d( "elelelelleel","Eeeee" );
                    startActivity(intent);
                    finish();

                }
            } );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();





            //String information = UseriD+";"+name.getText().toString()+";"+weeks.getText().toString()+";"+"active";


        //    Log.d( "ddd",information );
        }

        }




}
