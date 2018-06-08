package com.lifeline.fyp.fyp.lifestyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.lifestyle.suggestions.HairStyleSuggestions;
import com.lifeline.fyp.fyp.lifestyle.suggestions.CombinedSuggestions;
import com.lifeline.fyp.fyp.models.SkinTip;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesObject;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailedSkinTip extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private NavigationView tipsNavigationview;
    private TextView useremail,username;
    private FirebaseUser user; //  firebase user
    private FirebaseAuth auth; // firebase auth
    String email,loginemail;
    Api api;
    private Integer clickedid;
    private String facetype,hairtype,weight,gender,height,age,skincolor,buttonText; // email of the signed in user.
    Call<MemberObject> call;
    private Integer id;
    private String sinfo,finalinfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detailed_skin_tip );
            // navigatiob.
        tipsNavigationview  = (NavigationView)findViewById(R.id.nvskintips);

// disabling.
      ImageButton lifestyle = (ImageButton) findViewById(R.id.lifestyledetailed);
        lifestyle.setEnabled(false);
        lifestyle.setClickable(false);


        // extra kam.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             clickedid = extras.getInt("clickedtip"); // information contains boolean+gender+age+lifestylecategory.

        }

         Log.d( "iddd", String.valueOf( clickedid ) );


        //setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        api =  retrofit.create(Api.class);


        SkinTip st = new SkinTip(clickedid);
        // getting the detailed id ;
        Call<StyleAccessoriesObject> call = api.detailedskintip(st);
        call.enqueue(new Callback<StyleAccessoriesObject>() {
            @Override
            public void onResponse(Call<StyleAccessoriesObject> call, Response<StyleAccessoriesObject> response) {

                try{
                    HtmlTextView textView = (HtmlTextView) findViewById(R.id.texttip);
                    textView.setHtml(response.body().getData().getCategory(),new HtmlHttpImageGetter(textView));

                }
                catch(NullPointerException e){
                    Toast.makeText( DetailedSkinTip.this,"Network-error",Toast.LENGTH_LONG ).show();
                }

               }

            @Override
            public void onFailure(Call<StyleAccessoriesObject> call, Throwable t) {
                Toast.makeText( DetailedSkinTip.this,"Network-error",Toast.LENGTH_LONG ).show();

            }
        });


        View header = tipsNavigationview.getHeaderView(0);

        // navigation mnu & drawer layout setting.
        useremail = (TextView) header.findViewById(R.id.currentuser);
        username = (TextView) header.findViewById(R.id.activeusername);


        // getting user instance
        auth = FirebaseAuth.getInstance();

        // fetching email.
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // setting email.
        useremail.setText(email);



        // setting Drawlable layout
        dlayout = (DrawerLayout) findViewById(R.id.drawerlayouttips);
        drawerToggle = new ActionBarDrawerToggle(DetailedSkinTip.this, dlayout, R.string.open, R.string.close);
        dlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        NavigationView navigationMenu = (NavigationView)findViewById(R.id.navmenusuggestions);
        tipsNavigationview.setNavigationItemSelectedListener(this);
    }

    // implementing the onclick events.
    // selsection action result.
    public boolean onOptionsItemSelected(MenuItem menu) {

        if (drawerToggle.onOptionsItemSelected( menu )) {
            return true;
        }
        return super.onOptionsItemSelected( menu );
    }

    // intializing the menu items of the navigation bar.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.hairnav2: {
                call = api.fetchingUser(email);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject> response){


                        Log.d("messi", String.valueOf(response));
                        Log.d("messi", String.valueOf(response.body()));

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
                        buttonText = "hairStyle"; // seperator[1];


                        sinfo = id+";"+buttonText+";"+gender+";"+weight+";"+facetype; // hairstyle + gender

                        Log.d("messi", String.valueOf(response.body().getData().getHairType()));
                        Log.d("messi", String.valueOf(response.body().getData().getFaceShape()));
                        Log.d("messi", String.valueOf(response.body().getData().getWeight()));

                        if(hairtype.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, HairTypeQuestion.class);
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!hairtype.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, HairStyleSuggestions.class);
                            finalinfo = id+";"+buttonText+";"+gender+";"+gender+";"+gender+";"+hairtype+";"+facetype;
                            intent.putExtra("information",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee","ddddddddddddddfdfdfd");
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(DetailedSkinTip.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }
            case R.id.sglassesnav2: {
                call = api.fetchingUser(email);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response){


                        Log.d("messi", String.valueOf(response));
                        Log.d("messi", String.valueOf(response.body()));

                        try{
                            facetype = response.body().getData().getFaceShape().toString();
                        }
                        catch(NullPointerException e){
                            facetype = "null";
                        }


                        gender = response.body().getData().getGender().toString();
                        id = response.body().getData().getMemberId();
                        buttonText ="sunglasses";

                        sinfo = id+";"+buttonText+";"+gender+";"+facetype;

                        if(facetype.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, FaceShapeQuestion.class);
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!facetype.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, CombinedSuggestions.class);
                            finalinfo = id+";"+buttonText+";"+gender+";"+gender+";"+facetype;
                            intent.putExtra("information",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee","gggggggggggggggggggggggggggggggggggggggggggggg");
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(DetailedSkinTip.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });



                break;
            }

            case R.id.clothnav2: {
                call = api.fetchingUser(email);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response){


                        Log.d("messi", String.valueOf(response));
                        Log.d("messi", String.valueOf(response.body()));

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
                        buttonText = "clothes";


                        sinfo = id+";"+buttonText+";"+gender+";"+weight+";"+age+";"+height+";"+skincolor; // id;clothes;gender;weight;age;height;skincolor

                        Log.d("messi", String.valueOf(response.body().getData().getHairType()));
                        Log.d("messi", String.valueOf(response.body().getData().getFaceShape()));
                        Log.d("messi", String.valueOf(response.body().getData().getWeight()));

                        if(height.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, HeightQuestion.class);
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!height.equals("null")){
                            Intent intent = new Intent(DetailedSkinTip.this, FashionCategoryQuestion.class);
                            finalinfo = id+";"+buttonText+";"+gender+";"+weight+";"+age+";"+height+";"+skincolor+";" +height+";"+weight;
                            intent.putExtra("information",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee",finalinfo);
                            Log.d("mesaageeeeeeeeeeeeeeeee","ddddddddddddddfdfdfd");
                            startActivity(intent);
                            finish();
                        }



                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(DetailedSkinTip.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

            case R.id.fwearnav2: {
                call = api.fetchingUser(email);
                call.enqueue(new Callback<MemberObject>() {
                    @Override
                    public void onResponse(Call<MemberObject> call, Response<MemberObject>response){

                        gender = response.body().getData().getGender().toString();
                        age = response.body().getData().getAge().toString();
                        id = response.body().getData().getMemberId();
                        buttonText = "footwear";

                        // storing info.
                        sinfo = id+";"+buttonText+";"+gender+";"+age; // id;clothes;gender;weight;age;height;skincolor

                        Intent intent = new Intent(DetailedSkinTip.this, FashionCategoryQuestion.class);
                        intent.putExtra("information",sinfo);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(DetailedSkinTip.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });



                break;
            }

            case R.id.exitapp2: {
                auth.signOut();
                finish();
                startActivity( new Intent( this, StartingActivity.class ) );

                break;
            }


        }
        return false;
    }


    public void home(View view){
        Intent intent = new Intent(DetailedSkinTip.this, MainActivity.class);
        startActivity( intent );
        overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

    }

    public void DietNav(View view){
        Intent intent = new Intent(DetailedSkinTip.this, DietMain.class);
        startActivity( intent );
        overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
    }
}
