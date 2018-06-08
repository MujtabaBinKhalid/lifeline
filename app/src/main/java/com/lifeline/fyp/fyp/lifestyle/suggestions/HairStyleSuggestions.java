package com.lifeline.fyp.fyp.lifestyle.suggestions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.diet.DietMain;
import com.lifeline.fyp.fyp.fitness.FitnessSession;
import com.lifeline.fyp.fyp.lifestyle.FaceShapeQuestion;
import com.lifeline.fyp.fyp.lifestyle.FashionCategoryQuestion;
import com.lifeline.fyp.fyp.lifestyle.GenderQuestion;
import com.lifeline.fyp.fyp.lifestyle.HairTypeQuestion;
import com.lifeline.fyp.fyp.lifestyle.HeightQuestion;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.lifestyle.ShadesTone;
import com.lifeline.fyp.fyp.models.HairStyle;
import com.lifeline.fyp.fyp.models.Member;
import com.lifeline.fyp.fyp.models.StyleAccessories;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.StyleAccessoriesArray;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.List;

import qdx.bezierviewpager_compile.BezierRoundView;
import qdx.bezierviewpager_compile.vPage.BezierViewPager;
import qdx.bezierviewpager_compile.vPage.CardPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HairStyleSuggestions extends AppCompatActivity implements CardStack.CardEventListener , NavigationView.OnNavigationItemSelectedListener{

    private String pinfo;
    private Api api;
    private String [] seperator; // used to seperator the string (pinfo)
    private Member updatedmember;
    ArrayList<String>pictures;
    CardStack cardstack;
    ProgressDialog progressDialog;

    HairSwipeAdapter swipe_card_adapter;
    Integer id;
    RelativeLayout layout;
    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private NavigationView suggestionsNavigationview;
    private TextView useremail,username;
    private FirebaseUser user; //  firebase user
    private FirebaseAuth auth; // firebase auth
    private String email,info,moresugg;
    private Menu menuNav;
    MenuItem current;
    ImageButton imageButton;
    private String facetype,hairtype,weight,gender,height,age,skincolor,buttonText; // email of the signed in user.
    Call<MemberObject> call;
    private String sinfo,finalinfo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hair_style_suggestions );

        progressDialog = new ProgressDialog( this );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( "Wait.." );
        progressDialog.setTitle( "Processing Data" );
       progressDialog.show();

// disabling.
        ImageButton lifestyle = (ImageButton) findViewById(R.id.lifestyle1);
        lifestyle.setEnabled(false);
        lifestyle.setClickable(false);

        layout = (RelativeLayout) findViewById( R.id.layout );




        // fitnese btn of the nav bar.
        ImageButton fitness1 = (ImageButton) findViewById( R.id.fitness1 );
        fitness1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( HairStyleSuggestions.this, FitnessSession.class );
              intent.putExtra( "flow","noexericse;" );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );


        ImageButton home1 = (ImageButton) findViewById( R.id.home1 );
        home1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( HairStyleSuggestions.this, MainActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );

        // diet button.
        ImageButton profile1 = (ImageButton) findViewById( R.id.profile );
        profile1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( HairStyleSuggestions.this, UserProfile.class );
                intent.putExtra( "flow","noexericse;" );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );

            }
        } );

        ImageButton diet1 = (ImageButton) findViewById( R.id.nutrition1 );
        diet1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent( HairStyleSuggestions.this, DietMain.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right );
            }
        } );




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinfo = extras.getString("information");
        }

        seperator = pinfo.split(";");


        Log.d( "dfdfdfsdaf late","sfsdfser fgfgsd" );
        Log.d( "dfdfdfsdaf late", seperator[2] );
        Log.d( "dfdfdfsdaf late", seperator[5] );
        Log.d( "dfdfdfsdaf late", seperator[6] );

        // setting nav bar and gettting the firebase instance.

        // inflating the navigation header.
        suggestionsNavigationview  = (NavigationView)findViewById(R.id.navmenusuggestions);
        View header = suggestionsNavigationview.getHeaderView(0);

        useremail = (TextView) header.findViewById(R.id.currentuser);
        username = (TextView) header.findViewById(R.id.activeusername);


        // getting user instance
        auth = FirebaseAuth.getInstance();

        // fetching email.
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // setting email.
        useremail.setText(email);


        // CardAdapter

        final CardPagerAdapter cardPagerAdapter = new CardPagerAdapter( this );
        final BezierViewPager bvp = (BezierViewPager) findViewById( R.id.bezierviewpager );
        final BezierRoundView brv = (BezierRoundView) findViewById( R.id.bezierroundivew );

        // setting Drawlable layout
        dlayout = (DrawerLayout) findViewById(R.id.drawerlayoutsuggestions);
        drawerToggle = new ActionBarDrawerToggle(HairStyleSuggestions.this, dlayout, R.string.open, R.string.close);
        dlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //initializing card stack and its adapter.


//        cardstack = (CardStack) findViewById(R.id.container);
//        cardstack.setContentResource(R.layout.swipe_adapter2);
//        cardstack.setStackMargin(18);
//        cardstack.setListener(this);

        // selecting which adapter t use



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Base_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        // used to fetch all the info stored..

        // sep[6] = faceshape; sep[2] = gender sep[5] == hair
        HairStyle suggestions = new HairStyle( seperator[6],seperator[2],seperator[5]);

        Log.d( "baba",seperator[2] );
        Log.d( "baba",seperator[5] );
        Log.d( "baba",seperator[6] );

//        Log.d("1st argu sep5", seperator[5]);
//
//        Log.d(" 2nd argu sep2", seperator[2]);
//                            Log.d("3rd argu ksep6", seperator[6]);

        Call<StyleAccessoriesArray> call = api.hairssugestion(suggestions);
        call.enqueue(new Callback<StyleAccessoriesArray>() {
            @Override
            public void onResponse(Call<StyleAccessoriesArray> call, Response<StyleAccessoriesArray> response) {

                Log.d("ronaldo", String.valueOf(response));
                Log.d("ronaldo", String.valueOf(response.body()));
//
                try{
                    Log.d("ronaldo", String.valueOf(response.body().getMessage()));

//              TextView hairtip = (TextView) findViewById( R.id.tiphair ) ;
//              hairtip.setText( response.body().getMessage() );

                    // hair tip
                    String stringhairtip = response.body().getMessage();
                    List<StyleAccessories> users = response.body().getData();
                    String[] Stringpictures = new String [users.size()];

                    for (int i=0; i< users.size();i++) {
                        Stringpictures[i] = users.get( i ).getPicture();
                        Log.d("itni khushi pictures", String.valueOf(Stringpictures[i]));
                    }

                    int i =0;

                    pictures = new ArrayList<>();
                    for (String pic: Stringpictures){
                        pictures.add(pic);
                        Log.d("itni khushi arraylist", String.valueOf(pic));
                        i++;
                    }

                    cardPagerAdapter.addImgUrlList( pictures );
                    bvp.setAdapter( cardPagerAdapter );
                    brv.attach2ViewPage( bvp );


                    Log.d("INSDIE AL", String.valueOf(pictures.size()));

                    progressDialog.hide();
                    layout.setVisibility( View.VISIBLE );

//
////              swipe_card_adapter = new HairSwipeAdapter(getApplicationContext(),0,pictures);
////              cardstack.setAdapter(swipe_card_adapter);
//
                }

               catch (NullPointerException e){
                }

            }

            @Override
            public void onFailure(Call<StyleAccessoriesArray> call, Throwable t) {
                Log.d("errormessageregistrat", t.getMessage() );

            }
        });

        // disabling of the menu item and setting of the onclick nav buttons.

//        NavigationView navigationMenu = (NavigationView)findViewById(R.id.navmenusuggestions);
        suggestionsNavigationview.setNavigationItemSelectedListener(this);


        // disabling the buttons. // nav butons.
        menuNav=suggestionsNavigationview.getMenu();

        if(seperator[1].equals("hairStyle")){
            current = menuNav.findItem(R.id.hairnav);
            Log.d("checking", "hair");
            current.setEnabled(false);
        }

        else if(seperator[1].equals("sunglasses")){
            current = menuNav.findItem(R.id.sglassesnav);
            Log.d("checking", "hasfdvvfsfsfsfsfsdsir");
            current.setEnabled(false);
        }

        else if(seperator[1].equals("clothes")){
            current = menuNav.findItem(R.id.clothnav);
            Log.d("checking", "havdfdfdfdir");
            current.setEnabled(false);
        }


        else if(seperator[1].equals("footwear")){
            current = menuNav.findItem(R.id.clothnav);
            Log.d("checking", "hafooir");
            current.setEnabled(false);
        }

    }

    @Override
    public boolean swipeEnd(int i, float v) {
        return (v>300)?true:false;
    }

    @Override
    public boolean swipeStart(int i, float v) {
        return false;
    }

    @Override
    public boolean swipeContinue(int i, float v, float v1) {
        return false;
    }

    @Override
    public void discarded(int i, int i1) {

    }

    @Override
    public void topCardTapped() {

    }


    // selsection action result.
    public boolean onOptionsItemSelected(MenuItem menu) {

        if (drawerToggle.onOptionsItemSelected(menu)) {
            return true;
        }
        return super.onOptionsItemSelected(menu);
    }

    // intializing the menu items of the navigation bar.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.snavbar: {
                moresugg = "true"+";"+seperator[1];
                Intent intent = new Intent(HairStyleSuggestions.this, GenderQuestion.class);
                Toast.makeText(HairStyleSuggestions.this, sinfo, Toast.LENGTH_SHORT).show();
                intent.putExtra("information",moresugg);
                startActivity(intent);
                // finish();


                break;
            }

            case R.id.hairnav: {
                Toast.makeText(this, "Home icon clicked", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(HairStyleSuggestions.this, HairTypeQuestion.class);
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!hairtype.equals("null")){
                            Intent intent = new Intent(HairStyleSuggestions.this, HairStyleSuggestions.class);
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
                        Toast.makeText(HairStyleSuggestions.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            }

            case R.id.sglassesnav: {
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
                            Intent intent = new Intent(HairStyleSuggestions.this, FaceShapeQuestion.class);
                            Toast.makeText(HairStyleSuggestions.this, sinfo, Toast.LENGTH_SHORT).show();
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!facetype.equals("null")){
                            Intent intent = new Intent(HairStyleSuggestions.this, ShadesTone.class);
                            finalinfo = id+";"+buttonText+";"+gender+";"+gender+";"+facetype;
                            Toast.makeText(HairStyleSuggestions.this, sinfo, Toast.LENGTH_SHORT).show();
                            intent.putExtra("information",finalinfo);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(HairStyleSuggestions.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }

            case R.id.clothnav: {
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


                        if(height.equals("null")){
                            Intent intent = new Intent(HairStyleSuggestions.this, HeightQuestion.class);
                            intent.putExtra("information",sinfo);
                            startActivity(intent);
                            finish();


                        }

                        else if (!height.equals("null")){
                            Intent intent = new Intent(HairStyleSuggestions.this, FashionCategoryQuestion.class);
                            finalinfo = id+";"+buttonText+";"+gender+";"+weight+";"+age+";"+height+";"+skincolor+";" +height+";"+weight;
                            intent.putExtra("information",finalinfo);
                            startActivity(intent);
                            finish();
                        }



                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(HairStyleSuggestions.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }

            case R.id.fwearnav: {
                Toast.makeText(this, "Reports icon clicked", Toast.LENGTH_SHORT).show();
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

                        Intent intent = new Intent(HairStyleSuggestions.this, FashionCategoryQuestion.class);
                        Toast.makeText(HairStyleSuggestions.this, sinfo, Toast.LENGTH_SHORT).show();
                        intent.putExtra("information",sinfo);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<MemberObject> call, Throwable t) {

                        Log.d("ciit", t.getMessage());
                        Toast.makeText(HairStyleSuggestions.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }

            case R.id.exitapp: {
                auth.signOut();
                finish();
                startActivity(new Intent(this,StartingActivity.class));

                break;
            }


        }
        return false;
    }



}
