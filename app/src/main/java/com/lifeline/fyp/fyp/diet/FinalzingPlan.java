package com.lifeline.fyp.fyp.diet;

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
import android.widget.EditText;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.tooltip.Tooltip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class FinalzingPlan extends AppCompatActivity {

    String allinformation,state,weeks;
    EditText nameplan;
    boolean check1;
    private CircleImageView circulartooltip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_finalzing_plan );

         nameplan = (EditText) findViewById( R.id.pname );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            allinformation = extras.getString("information");

            state = extras.getString("state");
            weeks = extras.getString( "weeks" );




        }

        Validation();
        circulartooltip = (CircleImageView) findViewById(R.id.ptip);

    }


    public void creating(View view) {



        if (TextUtils.isEmpty( nameplan.getText() )) {
            Toast.makeText( this, "Fill in the field, to proceed.", Toast.LENGTH_LONG ).show();
        }

        else if (!check1)
        {
            Toast.makeText( this, "Make Correction to continue", Toast.LENGTH_LONG ).show();

        }
            else {
//            allinformation = allinformation + ";" + nameplan.getText();
            Intent intent = new Intent( this, CreatedPlan.class );

            Bundle extra = new Bundle(  );
            extra.putString("information", allinformation   );
            extra.putString("planname", nameplan.getText().toString()  );
            extra.putString("state", state);
            extra.putString("weeks",weeks);


            intent.putExtras(extra);
          finish();
            startActivity(intent);

//            intent.putExtra( "information", allinformation ); // combination contains boolean+gender+age.
//            intent.putExtra( "planname", nameplan.getText() ); // combination contains boolean+gender+age.
//
//              startActivity( intent );
Log.d( "bugtimeName", allinformation);

        }


    }

    public void Validation(){
        // for the weight text field.
        nameplan.addTextChangedListener( new TextWatcher() {
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
                    nameplan.setTextColor( Color.RED );
                    check1 = false;

                } else {
                    nameplan.setTextColor( Color.BLACK );
                    check1 = true;
                }
            }
        } );

    }


    public void Tooltipmessage(View view) {

        String tip = "Enter your plan name.";
        switch (view.getId()) {

            case R.id.ptip:
                showToolTip( view, Gravity.TOP, tip );
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

}