package com.lifeline.fyp.fyp.Signup;

/**
 * Created by Mujtaba_khalid on 11/28/2017.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;

import java.util.Calendar;


public class SignUp2 extends AppCompatActivity {

    private TextView textview;
    private DatePickerDialog.OnDateSetListener datelistener;
    private String text,pinfo,info; // text == current text.  pinfo == previous activities info info == combination of text+pinfo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("fullname");
        }


        textview = (TextView) findViewById(R.id.dob);
        textview.setOnClickListener(new View.OnClickListener() {

            // displaying the current year , month.
            public void onClick(View view) {
                final Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                 int day = calender.get(Calendar.DAY_OF_MONTH);

                // setting max data in calender.
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.DAY_OF_MONTH, day );
                maxDate.set(Calendar.MONTH, month);
                maxDate.set(Calendar.YEAR, year-15);




                DatePickerDialog datepickerdialog = new DatePickerDialog(SignUp2.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (DatePickerDialog.OnDateSetListener) datelistener, year, month, day);


                datepickerdialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());


                datepickerdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datepickerdialog.show();


            }
         });

         datelistener = new DatePickerDialog.OnDateSetListener() {


             @Override
             public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month+1;

                    String date = day + "/" + month + "/" + year;
                    textview.setText(date);

                    text = textview.getText().toString();
                    info = pinfo + ";"+ text;


             }
         };

    }
    public  void signup3(View view){

               if(TextUtils.isEmpty(text)){
                   Toast.makeText(this,"Select your DOB.",Toast.LENGTH_LONG).show();

               }

               else {
                   Intent intent = new Intent(this,SignUp3.class);
                   intent.putExtra("information",info);
                   finish();
                   startActivity(intent);

               }

    }
}