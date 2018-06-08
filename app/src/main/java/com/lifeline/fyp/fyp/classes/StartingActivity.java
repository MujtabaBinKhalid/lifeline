package com.lifeline.fyp.fyp.classes;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.Signup.SignUp;
import com.lifeline.fyp.fyp.models.Signin;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StartingActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText password, email;
    private Button signin;
    Retrofit retrofit;
    Api api;
    String statuscode;
    String[] seperator;
    private String getemail, getpassword;
    private ProgressDialog progressDialog;
    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        if (checkInternetConenction()) {
            setContentView( R.layout.activity_start );

// Create a GestureDetector

            retrofit = new Retrofit.Builder()
                    .baseUrl( Api.Base_URL )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();


            // setting Processdialog
            progressDialog = new ProgressDialog( this );
            progressDialog.setTitle( "SigningIn" );
            progressDialog.setMessage( "Please wait .. " );


            api = retrofit.create( Api.class );

            // setting firebase.
            auth = FirebaseAuth.getInstance();

//            // checking loggin status.
            if (auth.getCurrentUser() != null) {

                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                finish();
            }

            //intialising xml fields.
            email = (EditText) findViewById( R.id.signinemail );
            password = (EditText) findViewById( R.id.signinpassword );
            signin = (Button) findViewById( R.id.signinlogin );


            // signing in the user.
            signin.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d( "sdfsf","SDfsfsfsf" );
                    getemail = email.getText().toString().trim();
                    getpassword = password.getText().toString().trim();

                    if (TextUtils.isEmpty( getemail ) && (TextUtils.isEmpty( getpassword ))) {
                        Toast.makeText( StartingActivity.this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();

                    } else if (TextUtils.isEmpty( getemail )) {

                        Toast.makeText( StartingActivity.this, "Fill the in email,to proceed.", Toast.LENGTH_LONG ).show();
                        return;

                    } else if (TextUtils.isEmpty( getpassword )) {

                        Toast.makeText( StartingActivity.this, "Fill in the password, to proceed.", Toast.LENGTH_LONG ).show();
                        return;
                    } else {
                        progressDialog.show();

                        Login( getemail, getpassword );

                    }


                }
            } );

        } else {
            setContentView( R.layout.no_iternet );
            button = (ImageButton) findViewById( R.id.internetbtn );

            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(getIntent());
                }

            } );

        }


    }

    public void mainActivity(View view) {
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );

    }

    public void SignupProcess(View view) {
        Intent intent = new Intent( this, SignUp.class );
        startActivity( intent );

    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService( getBaseContext().CONNECTIVITY_SERVICE );

        // Check for network connections
        if (connec.getNetworkInfo( 0 ).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo( 1 ).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }


    private void Login(final String useremail, final String userpassword) {

    Call<MemberObject> memberObjectCall = api.fetchingUser(useremail);

    memberObjectCall.enqueue( new Callback<MemberObject>() {
        @Override
        public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

            Log.d( "Fgfgter", String.valueOf( response.code() ) );

            if (response.code() == 200){
                Log.d( "Sdfsdfsdf","Sfsdfdsfsf" );
                firebaseConnection( useremail,userpassword,response.body().getData().isVerfied() );
            }
            else{
                progressDialog.hide();
                Toast.makeText( getApplicationContext(),"Email doesn't exists",Toast.LENGTH_SHORT ).show();
            }
        }

        @Override
        public void onFailure(Call<MemberObject> call, Throwable t) {
            Log.d( "SDFsdfs","gdsfee33sdf" );

        }
    } );




    }

    private void firebaseConnection(final String useremail, String userpassword, boolean isverified){

    //   if(isverified){
           auth.signInWithEmailAndPassword( useremail, userpassword ).
                   addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           Log.d( "SDfsererw","sfdfwwweee" );
                           if (task.isSuccessful()) {

                               Intent intentsigning = new Intent( getApplicationContext(), MainActivity.class );

                               intentsigning.putExtra( "useremail", useremail );

                               progressDialog.hide();
                               Toast.makeText( StartingActivity.this,"Welcome ..", Toast.LENGTH_SHORT ).show();
                                 startActivity( intentsigning );

                               finish();
                           }
                           if (!task.isSuccessful()) {
                               progressDialog.hide();
                               Toast.makeText( StartingActivity.this, "Wrong credentials , try again.", Toast.LENGTH_SHORT ).show();

                           }
                       }
                   } );

           //       }
//
//       else{
//           Toast.makeText( StartingActivity.this,"Account not verified", Toast.LENGTH_SHORT ).show();
//       }
    }
}
