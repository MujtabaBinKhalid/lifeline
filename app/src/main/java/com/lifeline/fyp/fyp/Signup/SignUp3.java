package com.lifeline.fyp.fyp.Signup;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;


public class SignUp3 extends AppCompatActivity {

     private RadioGroup radioGroup;
     private Button next;
     private RadioButton radioButton;
    private String text,pinfo,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }


        radioGroup = (RadioGroup) findViewById(R.id.gender);
        next = (Button) findViewById(R.id.next);


    }
    public  void signup4(View view){
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getApplicationContext(),"Select gender to continue.", Toast.LENGTH_SHORT).show();

        }
        else {
            Intent intent = new Intent(this, SignUp4.class);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            text = radioButton.getText().toString();

            info = pinfo + ";"+ text;

            intent.putExtra("information",info);

            finish();
            startActivity(intent);

        }
    }
}