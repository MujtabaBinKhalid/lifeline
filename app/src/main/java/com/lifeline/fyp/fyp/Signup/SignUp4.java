package com.lifeline.fyp.fyp.Signup;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.tooltip.Tooltip;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUp4 extends AppCompatActivity {

    private String text,pinfo,info;
    private EditText userName,email;
    private Button button;
    CircleImageView circulartooltip3;
    CircleImageView circulartooltip4;
    boolean settingfname,settinglname;
    int stauscode;
        String emailaddress;
        ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup4);

        // intializing.
        userName = (EditText) findViewById( R.id.username );
        email = (EditText) findViewById( R.id.email );
        button = (Button) findViewById( R.id.loginsup );

        circulartooltip3 = (CircleImageView) findViewById( R.id.tip3 );
        circulartooltip4 = (CircleImageView) findViewById( R.id.tip4 );
        progressDialog = new ProgressDialog( this );


        // fetching information.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }


        // applying validations on the text fields.

        userName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[a-zA-Z_][a-zA-Z_0-9-]{3,12}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    userName.setTextColor( Color.RED );
                  //  button.setVisibility(View.GONE);
                    settingfname =false;

                } else {
                    userName.setTextColor( Color.BLACK );
                    settingfname = true;
                }
            }
        });


        email.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern =   "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+";


                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    email.setTextColor( Color.RED );
                    settinglname = false;
                   // button.setVisibility(View.GONE);

                } else {
                    email.setTextColor( Color.BLACK );
                    settinglname = true;
                }
            }
        });




    }
    public  void signup5(View view) {
//
//        String pattern = "^[a-zA-Z]{5,}[0-9_]*$";
//        Pattern regEx = Pattern.compile(pattern);
//        Matcher m = regEx.matcher(userName.getText().toString());
//
//        if(!m.matches()){
//            Toast.makeText(getApplicationContext(),"Username should be greater then 5 characters.", Toast.LENGTH_SHORT).show();
//
//        }
//
//        else {

      /////////////////////////////
        if (TextUtils.isEmpty( userName.getText() ) && (TextUtils.isEmpty( email.getText() ))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        }
        else if (((!settingfname) && settinglname) || ((!settinglname) && settingfname) || ((!settinglname) && (!settingfname))) {
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

        } else if (settingfname && settinglname) {

// fetching email;

            emailaddress = email.getText().toString();

            //setting Retrofit

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl( Api.Base_URL )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();
            Api api = retrofit.create( Api.class );


            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Wait.." );
            progressDialog.show();
            Call<MemberObject> call = api.fetchingUser( emailaddress );


            call.enqueue( new Callback<MemberObject>() {
                @Override
                public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {


                    try{
                        if (response.isSuccessful()) {
                            Log.d( "messi", String.valueOf( response ) );
                            Log.d( "messi", String.valueOf( response.body() ) );

                            stauscode = response.code();
                            String sc = ""+stauscode;


                            // checking if the user is unique or not.

                            // email exists.
                            if(sc.equals( "200" )){
                                progressDialog.hide();
                      Toast.makeText( getApplicationContext(), "Email address exists, try again .", Toast.LENGTH_SHORT ).show();


                            }

                            // unique email;
                            else if (sc.equals("204")){
                                Log.d( "messi", String.valueOf( response ) );
                                Log.d( "messi", String.valueOf( response.body() ) );
                                finish();
                                Intent intent = new Intent( SignUp4.this, SignUp5.class );
                                  text = userName.getText().toString() + ";" + email.getText().toString();

                                    info = pinfo + ";" + text;
                                progressDialog.hide();
                                    intent.putExtra( "information", info );
                                    Log.d("information",info);
                                    finish();
                                    startActivity(intent);

                            }

                    // some thing went wrong
                        else {
                                Toast.makeText( SignUp4.this, "Network-error", Toast.LENGTH_SHORT ).show();


//                                setContentView( R.layout.no_iternet );
//                                ImageButton  button = (ImageButton) findViewById( R.id.internetbtn );
//                                TextView internettext = (TextView) findViewById( R.id.nointernettext );
//
//                                internettext.setText( "Some thing went wrong, try again." );
//
//                                button.setOnClickListener( new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        finish();
//                                        startActivity(getIntent());
//                                    }
//
//                                });

                            }

                        }

                    }
                    // null exception.
                    catch (NullPointerException e){

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


                // on failure. , no net.
                @Override
                public void onFailure(Call<MemberObject> call, Throwable t) {

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

                    // yahan me layout change karnga.
                    Log.d( "ciit", t.getMessage() );
                    Toast.makeText( SignUp4.this, "Failed", Toast.LENGTH_SHORT ).show();
                }
            } );



////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//            Toast.makeText( getApplicationContext(), "Sahi hogya rey.", Toast.LENGTH_SHORT ).show();
//            Intent intent = new Intent( this, SignUp5.class );
//
//
//
//            text = userName.getText().toString() + ";" + email.getText().toString();
//
//            info = pinfo + ";" + text;
//            intent.putExtra( "information", info );
//            startActivity( intent );

        }
    }


    public void Hints(View view){

        String usernametip = "User name should not start with a number , and should be less then 12 letters.";
        String emailtip = "Standard email address required.";
        switch (view.getId()){

            case R.id.tip3:
                showToolTip( view, Gravity.TOP,usernametip);
                break;
            case R.id.tip4:
                showToolTip( view,Gravity.TOP,emailtip);
                break;
        }

    }

    public void showToolTip(View v, int gravity,String tip){
        CircleImageView civ = (CircleImageView)v;
        Tooltip tooltip = new Tooltip.Builder(civ).
                setText(tip)
                .setTextColor( Color.WHITE ). setGravity( gravity)
                .setCornerRadius(8f).setDismissOnClick( true ).show();
    }



}