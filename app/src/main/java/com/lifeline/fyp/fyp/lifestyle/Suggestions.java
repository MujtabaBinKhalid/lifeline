package com.lifeline.fyp.fyp.lifestyle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Suggestions extends Fragment {


    Button hairbtn,sunglassesbtn,clotbtn,fwbtn,buttonpressed  ;
    private String email,facetype,hairtype,weight,gender,height,age,skincolor,buttonText; // email of the signed in user.
    Call<MemberObject> call;
    private Integer id;
    private String sinfo,finalinfo;
    Api api;
    private ImageButton lifestyle,diet,home;
    private FirebaseAuth auth; // firebase auth
    String firebaseemail;
    private FirebaseUser user; //  firebase user






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_suggestions, container, false);

        // image button life style.
        lifestyle = (ImageButton) v.findViewById(R.id.lifestyle2);
        lifestyle.setEnabled(false);
        lifestyle.setClickable(false);


        getActivity().setTitle( "Life Style" );


        // fitnese btn of the nav bar.
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
                intent.putExtra( "flow","noexercise;" );
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right ); // right to left.

            }
        } );

        // setting firebase.
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //setting email to a string.
        firebaseemail = user.getEmail();





        home = (ImageButton)v.findViewById( R.id.home2 );
        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
 //              getActivity(). getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
        });


        // diet button.

        diet = (ImageButton)v.findViewById( R.id.diet2 );
        diet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(getActivity(), DietMain.class);
                startActivity( intent );
                getActivity().overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        });

        // categories
        hairbtn = v.findViewById(R.id.cardimage1);
        sunglassesbtn = v.findViewById(R.id.cardimage2);
        clotbtn = v.findViewById(R.id.cardimage3);
        fwbtn = v.findViewById(R.id.cardimage4);


        // settting retrofit.
       LifeStyle ls = (LifeStyle) getActivity();
       email = ls.getEmail();

        // fetching information.

        //setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

////////////////////////////////////////////////////////////////
        hairbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttonText = buttonpressed.getText().toString();
                Log.d( "buttontext", buttonText );

                call = api.fetchingUser(firebaseemail);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response) {

                      try{
                          try{
                              hairtype = response.body().getData().getHairType().toString();
                          }
                          catch(NullPointerException e){
                              hairtype = "null";
                          }

                          try{
                              facetype = response.body().getData().getFaceShape().toString();
                          }
                          catch (NullPointerException e){
                              facetype = "null";
                          }
                          try{
                              weight = response.body().getData().getWeight().toString();

                          }
                          catch (NullPointerException e){
                              weight = "null";
                          }

                          gender = response.body().getData().getGender().toString();
                          id = response.body().getData().getMemberId();

                          sinfo = id+";"+buttonText+";"+gender+";"+weight+";"+facetype; // hairstyle + gender

                          Log.d("messi", String.valueOf(response.body().getData().getHairType()));
                          Log.d("messi", String.valueOf(response.body().getData().getFaceShape()));
                          Log.d("messi", String.valueOf(response.body().getData().getWeight()));

                          if(hairtype.equals("null")){
                              Log.d( "ddcsd",sinfo );
                              Intent intent = new Intent(getActivity(), HairTypeQuestion.class);
                              intent.putExtra("information",sinfo);
                              startActivity(intent);


                          }

                          else if (!hairtype.equals("null")){
                              Intent intent = new Intent(getActivity(), HairStyleSuggestions.class);
                              finalinfo = id+";"+buttonText+";"+gender+";"+gender+";"+gender+";"+hairtype+";"+facetype;
                              intent.putExtra("information",finalinfo);
                              startActivity(intent);


                          }

                      }
                      catch (NullPointerException e){
                          Toast.makeText( getActivity(),"Network error",Toast.LENGTH_LONG ).show();
                      }


                    }
                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                    } // failure ending
                }); // call response ending
            } ///////////////////// onlick
        });   ////////////////////////////////// end of buton hair style.




        sunglassesbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttonText = buttonpressed.getText().toString();

                call = api.fetchingUser(firebaseemail);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response) {

                        Log.d("messi", String.valueOf(response));
                        Log.d("messi", String.valueOf(response.body()));

                        try{
                            try{
                                facetype = response.body().getData().getFaceShape().toString();
                            }
                            catch(NullPointerException e){
                                facetype = "null";
                            }


                            gender = response.body().getData().getGender().toString();
                            id = response.body().getData().getMemberId();

                            sinfo = id+";"+buttonText+";"+gender+";"+facetype;


                            if(facetype.equals("null")){
                                Intent intent = new Intent(getActivity(), FaceShapeQuestion.class);
                                intent.putExtra("information",sinfo);
                                startActivity(intent);


                            }

                            // 2 gender , 4 faceshape.
                            else if (!facetype.equals("null")){
                                Intent intent = new Intent(getActivity(), ShadesTone.class);
                                finalinfo = id+";"+buttonText+";"+gender+";"+gender+";"+facetype;
                                intent.putExtra("information",finalinfo);
                                startActivity(intent);

                            }

                        }
                        catch (NullPointerException e){
                            Toast.makeText( getActivity(),"Network error",Toast.LENGTH_LONG ).show();
                        }


                    } // on response.


                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                    } // failure endinmng
                }); // on response ending


            } // onclick

        }); // button ending.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        clotbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttonText = buttonpressed.getText().toString();

                call = api.fetchingUser(firebaseemail);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response) {
                        Log.d("messi", String.valueOf(response));
                        Log.d("messi", String.valueOf(response.body()));

                        try{
                            try{
                                height = response.body().getData().getHeight().toString();
                            }
                            catch(NullPointerException e){
                                height = "null";
                            }

                            try{
                                skincolor = response.body().getData().getSkinColor().toString();
                            }
                            catch (NullPointerException e){
                                skincolor = "null";
                            }
                            try{
                                weight = response.body().getData().getWeight().toString();

                            }
                            catch (NullPointerException e){
                                weight = "null";
                            }

                            gender = response.body().getData().getGender().toString();
                            age = response.body().getData().getAge().toString();
                            id = response.body().getData().getMemberId();

                            sinfo = id+";"+buttonText+";"+gender+";"+weight+";"+age+";"+height+";"+skincolor; // id;clothes;gender;weight;age;height;skincolor

                            Log.d("messi", String.valueOf(response.body().getData().getHairType()));
                            Log.d("messi", String.valueOf(response.body().getData().getFaceShape()));
                            Log.d("messi", String.valueOf(response.body().getData().getWeight()));

                            if(height.equals("null")){
                                Intent intent = new Intent(getActivity(), HeightQuestion.class);
                                intent.putExtra("information",sinfo);
                                startActivity(intent);



                            }

                            else if (!height.equals("null")){
                                Intent intent = new Intent(getActivity(), FashionCategoryQuestion.class);
                                finalinfo = id+";"+buttonText+";"+gender+";"+weight+";"+age+";"+height+";"+skincolor+";" +height+";"+weight;
                                intent.putExtra("information",finalinfo);
                                Log.d("mesaageeeeeeeeeeeeeeeee",finalinfo);
                                Log.d("mesaageeeeeeeeeeeeeeeee","ddddddddddddddfdfdfd");
                                startActivity(intent);

                            }
                        }
                        catch (NullPointerException e){
                            Toast.makeText( getActivity(),"Network error",Toast.LENGTH_LONG ).show();

                        }




                    }


                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                    } // failure endinmng
                }); // on response ending


            } // onclick

        }); // button ending.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        fwbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonpressed = (Button) view;
                buttonText = buttonpressed.getText().toString();

                call = api.fetchingUser(email);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response) {
                        gender = response.body().getData().getGender().toString();
                        age = response.body().getData().getAge().toString();
                        id = response.body().getData().getMemberId();
                        // storing info.
                        sinfo = id+";"+buttonText+";"+gender+";"+age; // id;clothes;gender;weight;age;height;skincolor

                        Intent intent = new Intent(getActivity(), FashionCategoryQuestion.class);
                        intent.putExtra("information",sinfo);
                        startActivity(intent);
                    }



                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                    } // failure endinmng
                }); // on response ending


            } // onclick

        }); // button ending.



/////////////////////////////////////////////////////////////////////////
                return v;
    }

}



