package com.lifeline.fyp.fyp.lifestyle;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.diet.Model;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairSwipeAdapter;
import com.lifeline.fyp.fyp.models.HairStyle;
import com.lifeline.fyp.fyp.models.SkinTip;
import com.lifeline.fyp.fyp.models.StyleAccessories;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesArray;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Skin_Tips extends Fragment {


    public ArrayList<SkinTip> skintipsdata;
    private RecyclerView srv;
    private RecyclerView.Adapter spa; // skinadapter
    private String email;
    SharedPreferences sp;
    private RecyclerView.Adapter spa1; // skinadapter
    private ImageButton lifestyle,diet,home;



// Recycle View is a effiecient and advanced version of list view.
    // Viewpager multiple tabs on a single activity.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.skin_tips, container, false);



    // BUtons setting.
        // image button life style.
        lifestyle = (ImageButton) v.findViewById(R.id.lifestyle3);
        lifestyle.setEnabled(false);
        lifestyle.setClickable(false);


        ImageButton fitness = (ImageButton) v.findViewById( R.id.fitness );

        fitness.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FitnessSession.class);
                intent.putExtra( "flow","noexercise;" );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right ); // right to left.

            }
        } );

        ImageButton profile = (ImageButton) v.findViewById( R.id.profile );

        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserProfile.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right ); // right to left.

            }
        } );


        home = (ImageButton)v.findViewById( R.id.home3 );
        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        });


        // diet button.

        diet = (ImageButton)v.findViewById( R.id.diet3 );
        diet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(getActivity(), DietMain.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        });




// sample skin tips
        // setting adapter and recycler view.
        srv = (RecyclerView)v.findViewById(R.id.skintips);

        sp = getActivity().getSharedPreferences( "position",Context.MODE_PRIVATE );

        if(sp.contains("clickedtip")) {

            Log.d( "lalalala", sp.getString( "clickedtip", null ) );

        }

   // setting retrofit to make an call for getting all the titles of the skin all.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);


        // fetching all the  posts of the skin care.
        SkinTip skintip = new SkinTip( -1 );

        Call<StyleAccessoriesArray> call = api.allskintips(skintip);
        call.enqueue(new Callback<StyleAccessoriesArray>() {
            @Override
            public void onResponse(Call<StyleAccessoriesArray> call, Response<StyleAccessoriesArray> response) {
                Log.d("ronaldo", String.valueOf(response));
                Log.d("ronaldo", String.valueOf(response.body()));

                try{
                    List<StyleAccessories> users = response.body().getData();
                    String[] titles = new String [users.size()];

                    // fetching all the titles from the server.
                    for (int i=0; i< users.size();i++) {
                        titles[i] = users.get( i ).getName();
                        Log.d("itni khushi ", String.valueOf(titles[i]));
                    }

                    Integer[] ids = new Integer [users.size()];

                    // fetching all the titles from the server.
                    for (int i=0; i< users.size();i++) {
                        ids[i] = users.get(i).getStyleAccessoriesId();
                        Log.d("itni khushi ", String.valueOf(ids[i]));
                    }


                    int i = 0;
                    /// array list.
                    skintipsdata = new ArrayList<>();
                    for (String title: titles){
                       SkinTip  st= new SkinTip(ids[i],title);
                       skintipsdata.add( st );

                        i++;
                    }

                    Log.d("arraylength", String.valueOf( skintipsdata.size() ) );
                    spa = new SkinAdapter(skintipsdata,email,getContext());
                    LinearLayoutManager dpm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);



                    srv.setLayoutManager(dpm);
                    srv.setAdapter(spa);
                }

                catch (NullPointerException e){
                }

            }

            @Override
            public void onFailure(Call<StyleAccessoriesArray> call, Throwable t) {
                Log.d("errormessageregistrat", t.getMessage() );

            }
        });

        // getting email of the logged in user.
        LifeStyle ls = (LifeStyle) getActivity();
        email = ls.getEmail();



        ///////////////// Recycler VIew.

        //spa = new SkinAdapter(skintipsdata,email,getContext());
        //LinearLayoutManager dpm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        //srv.setLayoutManager(dpm);
        //srv.setAdapter(spa);




        return v;


    }



}
