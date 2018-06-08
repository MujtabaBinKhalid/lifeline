package com.lifeline.fyp.fyp.Signup;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.classes.Verification;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;
import com.tooltip.Tooltip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUp5 extends AppCompatActivity {

    private String text,text2,pinfo,info,formattedDate;
    private EditText pass,cpass;
    private  String[] separatedstring;
    private String[] yearseperator;
    private String[] currentyear;
    private Calendar calender;
    boolean settingfname,settinglname;
    private SimpleDateFormat df;
    private int age;
    Intent intent;
    private Api api;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    CircleImageView circulartooltip5;
    CircleImageView circulartooltip6;
    int statuscode;
    String sc;
    String mobileToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup5);

        // intialzing circulartip
        circulartooltip5 = (CircleImageView) findViewById( R.id.tip5 );
        circulartooltip6 = (CircleImageView) findViewById( R.id.tip6 );
        mobileToken = FirebaseInstanceId.getInstance().getToken();

        // intialisng the edit texts.
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.cpass);

        // applying validations to password fields.

        pass.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";
//                Pattern regEx = Pattern.compile( pattern );
//                Matcher matches = regEx.matcher( editable );

                if (editable.length() <7) {
                    pass.setTextColor( Color.RED );
                    //  button.setVisibility(View.GONE);
                    settingfname =false;

                } else if(editable.length()>8) {
                    pass.setTextColor( Color.BLACK );
                    settingfname = true;
                }
            }
        });


        cpass.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//
                String password = pass.getText().toString();

                if(!editable.toString().equals(password)){
                    cpass.setTextColor( Color.RED );
                    settinglname =false;
                }

                else if (editable.toString().equals(password)){
                    cpass.setTextColor( Color.BLACK );
                    settinglname =true;
                }

            }
        });

        // intialsing Progress doalog.
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Registering User");
            progressDialog.setMessage("Please wait .. ");


        // fetching information from previous activities.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }



    // setting up Retrofit.

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            api = retrofit.create(Api.class);

        // setting firebase for creating account there.

         auth = FirebaseAuth.getInstance();



    }
    public  void logging(View view) {

        // if fields are empty.
        if (TextUtils.isEmpty( pass.getText() ) && (TextUtils.isEmpty( cpass.getText() ))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        }
        // if any exception.
        else if (((!settingfname) && settinglname) || ((!settinglname) && settingfname) || ((!settinglname) && (!settingfname))) {
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

            // if everthing is fine.
        } else if (settingfname && settinglname) {

            progressDialog.show();
            text = pass.getText().toString() + ";"+ cpass.getText().toString();


            // getting current date to calculate the age from dob.
            calender = Calendar.getInstance();
            df = new SimpleDateFormat("dd/MMM/yyyy");
            formattedDate = df.format(calender.getTime());


            // merging all the user Signup information.
            info = pinfo +";"+text;

            // seperating  information  from string.
            separatedstring = info.split(";");
            yearseperator = separatedstring[2].split("/");
            currentyear = formattedDate.split("/");
            // calculating age.
            age = calculatingage( Integer.parseInt(currentyear[2]),Integer.parseInt(yearseperator[2]));


            ////////////////////////////////
/************************
 *    String firstName, String lastName, String username, String gender, Integer age, String email, String password,String joiningDate
 *   0 == first name.
 *   1 == last name.
 *   3 == gender
 *   4  == username
 *   5 == email
 *   6 == password
 *   age  .
 *   current age.

            ///////////////////////////////////////////////////////

*/


            // creating user.
            Member member = new Member(separatedstring[0],separatedstring[1],separatedstring[4],separatedstring[3],age,separatedstring[5],separatedstring[6],mobileToken);

            // requesting server for making a new user.
            Call<Member> call = api.createMember(member);


            call.enqueue( new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {


                    try{
                        if (response.isSuccessful()) {
                            Log.d( "messi", String.valueOf( response ) );
                            Log.d( "messi", String.valueOf( response.body() ) );

                            statuscode = response.code();
                            String sc = ""+statuscode;


                            // checking if the user

                            // email exists.
                            if(sc.equals( "201" )){
                                auth.createUserWithEmailAndPassword(separatedstring[5],separatedstring[6]).
                                        addOnCompleteListener(SignUp5.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                // firebase sucesessfull.
                                                if(task.isSuccessful()) {
                                                    progressDialog.hide();
                                                    finish();
                                                  startActivity( new Intent( SignUp5.this,Verification.class ) );
                                                    Toast.makeText( getApplicationContext(), "Verify your email to start using the app.", Toast.LENGTH_SHORT ).show();


                                                }
                                                // firebase failed.
                                                else if(!task.isSuccessful()){
                                                    progressDialog.hide();
                                                    setContentView( R.layout.no_iternet );
                                                    ImageButton  button = (ImageButton) findViewById( R.id.internetbtn );
                                                    TextView internettext = (TextView) findViewById( R.id.nointernettext );

                                                    internettext.setText( "Some thing went wrong, try again." );

                                                    button.setOnClickListener( new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            finish();
                                                            startActivity(getIntent());
                                                        }

                                                    });
                                                }
                                            }
                                        });


                            }
                            // some thing went wrong
                            else {

                                setContentView( R.layout.no_iternet );
                                ImageButton button = (ImageButton) findViewById( R.id.internetbtn );
                                TextView internettext = (TextView) findViewById( R.id.nointernettext );

                                internettext.setText( "Some thing went wrong, try again." );

                                button.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                        startActivity(getIntent());
                                    }

                                });

                            }

                        }

                    }
                    // null exception.
                    catch (NullPointerException e){
//
                        Toast.makeText(SignUp5.this , "Network-error", Toast.LENGTH_SHORT ).show();
//                        setContentView( R.layout.no_iternet );
//                        ImageButton  button = (ImageButton) findViewById( R.id.internetbtn );
//                        TextView internettext = (TextView) findViewById( R.id.nointernettext );
//
//                        internettext.setText( "Some thing went wrong, try again." );
//
//                        button.setOnClickListener( new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                finish();
//                                startActivity(getIntent());
//                            }
//                        });
                    }
                }
                // on failure. , no net.
                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                    Toast.makeText(SignUp5.this , "Network-error,Try again.", Toast.LENGTH_SHORT ).show();


//                    setContentView( R.layout.no_iternet );
//                    ImageButton  button = (ImageButton) findViewById( R.id.internetbtn );
//                    TextView internettext = (TextView) findViewById( R.id.nointernettext );
//
//                    internettext.setText( "Some thing went wrong, try again." );
//
//                    button.setOnClickListener( new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            finish();
//                            startActivity(getIntent());
//                        }
//
//                    });

                    // yahan me layout change karnga.
                }
            } );




        }


    }

    public int calculatingage(int currentyear, int birthyear){

         return currentyear - birthyear ;
    }


    public void Hintspassword(View view){

        String password = "Minimum nine characters";

        String conpassword = "It should match with the password , u typed above.";
        switch (view.getId()){

            case R.id.tip5:
                showToolTip( view, Gravity.TOP,password);
                break;
            case R.id.tip6:
                showToolTip( view,Gravity.TOP,conpassword);
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