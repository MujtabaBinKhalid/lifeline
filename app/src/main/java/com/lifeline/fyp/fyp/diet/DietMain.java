package com.lifeline.fyp.fyp.diet;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.ViewpagerAdapter;
import com.lifeline.fyp.fyp.lifestyle.HairTypeQuestion;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.lifestyle.Skin_Tips;
import com.lifeline.fyp.fyp.lifestyle.Suggestions;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.ResponsePlanArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietMain extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager viewpager;
    DietPageAdapter viewpagerAdapter;
    public String id;
    private FirebaseAuth auth; // firebase auth
    private FirebaseUser user; //  firebase user
    String email,firebaseemail;
    public String assigningid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_main);

         //fetching information.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("information"); // information contains boolean+gender+age.

        }


        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        firebaseemail = user.getEmail();

        //setting Retrofit





        tablayout = (TabLayout) findViewById(R.id.tablayout2);
        viewpager = (ViewPager) findViewById(R.id.viewpagersuggestions);


        viewpagerAdapter = new DietPageAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragments(new DietPlans(),"Plans");
        viewpagerAdapter.addFragments(new MealingTracking(),"Meal Tracking");
        viewpagerAdapter.addFragments(new Reports(),"Reports");
        viewpager.setAdapter(viewpagerAdapter);
        tablayout.setupWithViewPager(viewpager);


   }




//
//    public void LifestyleNav(View view){
//        Intent intent = new Intent(this,LifeStyle.class);
//        intent.putExtra("information",email); // combination contains boolean+gender+age.
//        startActivity(intent);
//    }


    public void DietNav(View view){
        finish();
        startActivity(getIntent());
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.
    }

    public String getEmail(){
        return email;
    }


public String getId(){
    return id;
}


}
