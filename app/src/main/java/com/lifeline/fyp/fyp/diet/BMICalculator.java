package com.lifeline.fyp.fyp.diet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.BMIObject;
import com.tooltip.Tooltip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BMICalculator extends AppCompatActivity {

    private TextView height, weight;
    private boolean check1, check2;
    private CircleImageView circulartooltiph;
    private CircleImageView circulartooltipw;
    AlertDialog.Builder builder;
    String bmivalue,statusvalue,idealweightvalue,maxweightvalue;
    private Api api;
    Dialog dialog;
    Retrofit retrofit;


    private TextView bmi,status,maxweight,idealweight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bmicalculator );


        // Toolip intialization
        circulartooltiph = (CircleImageView) findViewById( R.id.tipheight );
        circulartooltipw = (CircleImageView) findViewById( R.id.tipweight );

        // text views of the second layout.
        height = (TextView) findViewById( R.id.height );
        weight = (TextView) findViewById( R.id.weight );


       // Retrofit
        // connection.
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );


        Validation();


    }

    /// validation of the text fields.
    public void Validation() {

        // for the weight text field.
        weight.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[1-9][0-9]{1,2}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    weight.setTextColor( Color.RED );
                    check1 = false;

                } else {
                    weight.setTextColor( Color.BLACK );
                    check1 = true;
                }
            }
        } );


        height.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[1-9][0-9]{1,2}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    height.setTextColor( Color.RED );
                    check2 = false;

                } else {
                    height.setTextColor( Color.BLACK );
                    check2 = true;
                }
            }
        } );

    }

    public void Tooltipmessage(View view) {

        String weighttip = "Weight should be in kg(s).";
        String heighttip = "Height should be in cm(s).";
        switch (view.getId()) {

            case R.id.tipweight:
                showToolTip( view, Gravity.TOP, weighttip );
                break;
            case R.id.tipheight:
                showToolTip( view, Gravity.TOP, heighttip );
                break;
        }
    }


    public void showToolTip(View v, int gravity, String tip) {
        CircleImageView civ = (CircleImageView) v;
        Tooltip tooltip = new Tooltip.Builder( civ ).
                setText( tip )
                .setTextColor( Color.WHITE ).setGravity( gravity )
                .setCornerRadius( 8f ).setDismissOnClick( true ).show();
    }

    // calculating

    public void calculating(final View view) {
        if (TextUtils.isEmpty( weight.getText() ) && (TextUtils.isEmpty( height.getText() ))) {
            Toast.makeText( this, "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();
        } else if (((!check1) && check2) || ((!check2) && check1) || ((!check2) && (!check1))) {
            Toast.makeText( this, "Make correction to continue.", Toast.LENGTH_LONG ).show();

        } else if (check1 && check2) {

            Double textweight = Double.parseDouble( weight.getText().toString() );
            Double textheight = Double.parseDouble( height.getText().toString() );

            com.lifeline.fyp.fyp.models.BMICalculator cal = new com.lifeline.fyp.fyp.models.BMICalculator(textheight,textweight);

            Api api = retrofit.create( Api.class );


            Call<BMIObject> call = api.checking(cal);


            call.enqueue( new Callback<BMIObject>() {
                @Override
                public void onResponse(Call<BMIObject> call, Response<BMIObject> response) {


                    if (response.isSuccessful()) {
                        try{
                            Log.d( "messi", String.valueOf(response));
                            Log.d( "messi", String.valueOf(response.body().getData().getBmi()));






                            bmivalue = ""+response.body().getData().getBmi();
                            statusvalue = ""+response.body().getData().getWeightStatus();
                            idealweightvalue = ""+response.body().getData().getIw();
                            maxweightvalue = ""  + response.body().getData().getMhw();

                            Log.d("BMI",bmivalue);
                            Log.d("staus",statusvalue);


                            showPopUp(view);

                        }

                        catch (Exception e){
                            Toast.makeText( BMICalculator.this,"Network-error",Toast.LENGTH_LONG ).show();
                        }

                    }
                }


                // on failure. , no net.
                @Override
                public void onFailure(Call<BMIObject> call, Throwable t) {
                    Log.d( "ciit", t.getMessage() );
                    Toast.makeText( BMICalculator.this, "Failed", Toast.LENGTH_SHORT ).show();

                }
            } );





        }



    }


    public void showPopUp(View view) {
        builder = new AlertDialog.Builder(this);

        dialog = new Dialog( BMICalculator.this );
        LayoutInflater layoutInflater = getLayoutInflater();

        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = layoutInflater.inflate(R.layout.custom_bmi_dialog, null);
        // reference the textview of custom_popup_dialog



        bmi =(TextView)customView .findViewById( R.id.bmi );
        status =(TextView)customView .findViewById( R.id.status );
        idealweight =(TextView)customView .findViewById( R.id.healthyweight );
        maxweight =(TextView)customView .findViewById( R.id.idealweight );


        bmi.setText( bmivalue );
        status.setText( statusvalue );
        idealweight.setText( idealweightvalue );
        maxweight.setText( maxweightvalue );


        Button close = (Button) customView.findViewById(R.id.closepopup);

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        builder.setCancelable( true );

        builder.setView(customView);
        dialog = builder.create();
        dialog.show();

    }


}
