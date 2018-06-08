package com.lifeline.fyp.fyp.lifestyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.LifeStyleResponse;
import com.lifeline.fyp.fyp.retrofit.responses.LifeStyle;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HairTypeQuestion extends AppCompatActivity {
    private String pinfo, selectedItem, combined;

    // pinfo will be contain id;button+gender+weight+facetype;
    // selecteditem recent item.
    //combined combination.

    private String[] seperator;
    private ArrayAdapter<String> hairtype;
    Api api;
    Integer id;
    ArrayList<String> haircollection = new ArrayList<>(  );
    private String moreinfo;
    Spinner spinner;
    RelativeLayout layout;
    Button button2;
    ProgressDialog progressDialog;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // setting the desired drop down and connecting it to adapter.
           button2.setVisibility( View.VISIBLE  );
           layout.setVisibility( View.VISIBLE );
           progressDialog.hide();
            hairtype = new ArrayAdapter<String>(HairTypeQuestion.this,
                    R.layout.spinner_item, haircollection);

            hairtype.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(hairtype);

            // fetching the recently selected value by the dropdown menu.
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


        }
    };
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_type_question);

                button2 = (Button)findViewById( R.id.button2 );
                button2.setVisibility( View.INVISIBLE );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information"); // information contains boolean+gender+age+lifestylecategory.
        }


                layout = (RelativeLayout) findViewById( R.id.layout );
        progressDialog = new ProgressDialog( this );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( "Wait..." );
        progressDialog.show();
        // setting up Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        fetchingHairtypes();
        seperator = pinfo.split(";");

        // setting question num.
        TextView tag = (TextView) findViewById( R.id.qtaghair );

        if(!seperator[0].equals( "true" )){
            tag.setText( "1 of 2" );
        }

        else if(seperator[0].equals( "true" )){
            tag.setText( "2 of 3" );
        }


        // fetching the gender variable to check whether the gender and age is set or not.

          spinner = (Spinner) findViewById(R.id.hair);
//
//        if (seperator[2].equals("Male")) {
//            hairtype = new ArrayAdapter<String>(HairTypeQuestion.this,
//                    R.layout.spinner_item, getResources().getStringArray(R.array.menhair));
//        } else {
//            hairtype = new ArrayAdapter<String>(HairTypeQuestion.this,
//                    R.layout.spinner_item, getResources().getStringArray(R.array.femalehair));
//        }




    }


    private void fetchingHairtypes(){

        Call<LifeStyleResponse> hairtypes = api.hairtypes();

        try {
            hairtypes.enqueue( new Callback<LifeStyleResponse>() {
                @Override
                public void onResponse(Call<LifeStyleResponse> call, final Response<LifeStyleResponse> response) {



                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.d( "dfdfd", String.valueOf( response ) );
                            List<LifeStyle> allhairs = response.body().getData();
                            for(int i=0; i< allhairs.size();i++){
                                haircollection.add( allhairs.get( i ).getVal1() );
                            }
                            handler.sendEmptyMessage( 0 );
                        }
                    };

                    Thread thread = new Thread( runnable );
                    thread.start();

                    // displaying hair types.

                    Log.d( "OUTTT", String.valueOf( haircollection.size() ) );
                }

                @Override
                public void onFailure(Call<LifeStyleResponse> call, Throwable t) {

                }
            });
        }
        catch (Exception e){

        }

    }
    public void Next(View view) {



        Log.d("fdfdfdf", combined);
        Log.d("fdfdfdf", seperator[2]);
        Log.d("fdfdfdf", seperator[4]);


        if(seperator[0].equals("true")){
            Intent intent = new Intent(this, FaceShapeQuestion.class);
            intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
            finish();
           startActivity(intent);
        }

        else if(!seperator[0].equals("true")){
            // weight is not set. so weight question.

//                Intent intent = new Intent(this, WeightQuestion.class);
//                Toast.makeText(HairTypeQuestion.this, combined, Toast.LENGTH_SHORT).show();
//
//                intent.putExtra("information", combined);  // combined == id;btn;gender;weight;facetype;hairtype
//                startActivity(intent);

                // and facetype is not set. // working fine
                if (seperator[4].equals("null")) {
                    Log.d( "ddcsd",combined );
                    Intent intent = new Intent(this, FaceShapeQuestion.class);

                    intent.putExtra("information", combined);  // combined == btn;gender;weight;facetype;hairtype;weight
                    finish();
                    startActivity(intent);
                }

                // when facetype set.
                else if (!seperator[4].equals("null")) {
                    id = Integer.parseInt(seperator[0]);
                    // when weight is not there but , facetype is null.
                    Member updatedmember = new Member(selectedItem,seperator[4]);
                    Call<MemberObject> call = api.updateUser(id,updatedmember);
                    call.enqueue(new Callback<MemberObject>() {
                        @Override
                        public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

                            try{
                                combined = pinfo + ";" + selectedItem+";"+seperator[4]; // combined == id;btn;gender;weight;facetype;hairtype

                                final Intent intent = new Intent(HairTypeQuestion.this, HairStyleSuggestions.class);

                                intent.putExtra("information",combined); // sending info.
                                finish();
                                startActivity(intent);

                            }
                            catch(NullPointerException e)
                            {
                                Toast.makeText(HairTypeQuestion.this, "Network error.", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<MemberObject> call, Throwable t) {
                            Log.d("errormessageregistrat", t.getMessage() );

                            Toast.makeText(HairTypeQuestion.this,"Try again, some thing went wrong.Hair style",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



        }

        } // end view.
    } // class
