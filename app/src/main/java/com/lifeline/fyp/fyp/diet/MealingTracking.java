package com.lifeline.fyp.fyp.diet;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.models.FoodCategoryDescription;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealingTracking extends Fragment {




    Button buttonpressed,starch,fruits,dairy,desserts,meat,fats;
    ImageButton lifestyle,home,diet ;
    String buttontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_mealing_tracking, container, false );


        starch = v.findViewById(R.id.cardimagea);
        fruits = v.findViewById(R.id.cardimageb);
        dairy = v.findViewById(R.id.cardimagec);
        desserts = v.findViewById(R.id.cardimaged);
        meat = v.findViewById(R.id.cardimagee);
        fats = v.findViewById(R.id.cardimagef);


        // setting the nav bar.

        home = (ImageButton) v.findViewById( R.id.sweethome );

        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        });


       //  dlifestyle


        lifestyle = (ImageButton)v.findViewById( R.id.dietlifestyle );

        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(getActivity(), LifeStyle.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        });

        // fintess

        // fitnese btn of the nav bar.
        ImageButton fitness = (ImageButton)v. findViewById( R.id.fitness );

        fitness.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FitnessSession.class);
                intent.putExtra( "flow","noexercise;" );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right ); // right to left.

            }
        } );

        ImageButton profile = (ImageButton)v. findViewById( R.id.profile );

        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserProfile.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left ); // left to right.

            }
        } );

// diet
        diet = (ImageButton)v.findViewById( R.id.apnadiet );
        diet.setEnabled(false);
        diet.setClickable(false);



        //// 1.
        starch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });

        ////
        //// 2.
        fruits.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });

        //// 3.
        dairy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });

        //// 4.
        desserts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });

        //// 5
        meat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });

        //// 6.
        fats.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttontext = buttonpressed.getText().toString();
                Intent intent = new Intent(getActivity(), CategoricallyFoodItems.class);
                intent.putExtra("information",buttontext);
                startActivity(intent);
            }
        });





        return v;
    }

}
