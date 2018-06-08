package com.lifeline.fyp.fyp.lifestyle;

/**
 * Created by Mujtaba_khalid on 11/26/2017.
 */



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.suggestions.ClothSuggestions;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SkinColorQuestion extends AppCompatActivity {
    private String pinfo,buttonText,combined;
    // fetching previous activity information.
    // to get the text of the spinner.
    // to merge all data.  // pinfo+buttontext
    // storing email.

    private Api api;
    private String [] seperator; // used to seperator the string (pinfo)
    private int id;
    private Member updatedmember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skincolorquestion);

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

        // used to fetch all the info stored..
        seperator = pinfo.split(";");

        TextView tag = (TextView) findViewById( R.id.qtagskin );

        // for clothes
        if(!seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "4 of 4" );
        }

        else if(seperator[0].equals( "true" ) && seperator[1].equals( "clothes" )){
            tag.setText( "6 of 6" );
        }

    }

    public void NextQuestion(View view) {
        final Intent intent = new Intent(this, ClothSuggestions.class);

        Button b = (Button) view;
        buttonText = b.getText().toString(); // fetching the text of the button.
        combined = pinfo + ";" + buttonText; // combining. // true;email;gender;age;buttonclicked;hairtype;weight;faceshape.

        if(seperator[0].equals("true")){
            intent.putExtra("information",combined); // sending info.
            Log.d( "info",combined );
            finish();
            startActivity(intent);
        }

        else if(!seperator[0].equals("true")){
            id = Integer.parseInt(seperator[0]);
            Log.d("ronaldo", String.valueOf(id));



            if(seperator[3].equals("null")){
                Member updatedmember = new Member(id,seperator[8],seperator[7],buttonText);
                Call<MemberObject> call = api.updateUser(id,updatedmember);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                      try{
                          intent.putExtra("information",combined); // sending info.
                          Log.d("combined",combined);
                          finish();
                          startActivity(intent);

                      }
                        catch(NullPointerException e ){

                            Toast.makeText(SkinColorQuestion.this,"Network-error",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {
                        Log.d("errormessageregistrat", t.getMessage() );

                        Toast.makeText(SkinColorQuestion.this,"Try again, some thing went wrong.Hair style",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else if(!seperator[3].equals("null")){
                Member updatedmember = new Member(id,seperator[3],seperator[7],buttonText);
                Call<MemberObject> call = api.updateUser(id,updatedmember);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                       try{
                           intent.putExtra("information",combined); // sending info.
                           finish();
                           startActivity(intent);

                       }
                    catch(NullPointerException e){
                        Toast.makeText(SkinColorQuestion.this,"Network error.",Toast.LENGTH_SHORT).show();

                    }


                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {
                        Log.d("errormessageregistrat", t.getMessage() );

                        Toast.makeText(SkinColorQuestion.this,"Try again, some thing went wrong.Hair style",Toast.LENGTH_SHORT).show();
                    }
                });
        }

         }
        }


}
