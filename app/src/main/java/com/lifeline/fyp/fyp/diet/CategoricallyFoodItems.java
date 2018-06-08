package com.lifeline.fyp.fyp.diet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.Signup.SignUp4;
import com.lifeline.fyp.fyp.Signup.SignUp5;
import com.lifeline.fyp.fyp.classes.MainActivity;
import com.lifeline.fyp.fyp.classes.StartingActivity;
import com.lifeline.fyp.fyp.lifestyle.LifeStyle;
import com.lifeline.fyp.fyp.lifestyle.suggestions.ClothSuggestions;
import com.lifeline.fyp.fyp.models.BMICalculator;
import com.lifeline.fyp.fyp.models.FoodCategory;
import com.lifeline.fyp.fyp.models.FoodItems;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.AllCategoryFoodItems;
import com.lifeline.fyp.fyp.retrofit.responses.BMIObject;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;
import com.tooltip.Tooltip;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoricallyFoodItems extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,NavigationView.OnNavigationItemSelectedListener {

    private Api api;
    Retrofit retrofit;
    private DrawerLayout dlayout; // drawerlayout
    private String clickedid;

    private NavigationView mNavigationview;
    private SearchView search; // layout in my view.
    private ListAdapter listAdapter; // adapter object .
    private ExpandableListView myList; // expandible list view .
    private ArrayList<FoodCategory> footcategorylistview = new ArrayList<FoodCategory>(); // main group array list parents.
    public ArrayList<FoodItems> newArray;
    private Toolbar bar;
    TextView useremail,username;
    CircleImageView userdp;
    RelativeLayout layout;
    ProgressDialog progressDialog;
    private ActionBarDrawerToggle drawerToggle; //toggle button


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            listAdapter = new ListAdapter(CategoricallyFoodItems.this, footcategorylistview ); // passing the context of the activity and the main array list.
            //attach the adapter to the list
            myList.setAdapter( listAdapter ); // connecting to the adapter.
            progressDialog.hide();
            layout.setVisibility( View.VISIBLE );


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_categorically_food_items );

        // inflating the navigation header.
        mNavigationview = (NavigationView) findViewById( R.id.navmenu );
        View header = mNavigationview.getHeaderView( 0 );
progressDialog = new ProgressDialog( this );
progressDialog.setMessage( "Loading Food items" );
progressDialog.setCancelable( false );
progressDialog.show();
        layout = (RelativeLayout) findViewById( R.id.layout );
        useremail = (TextView) header.findViewById( R.id.currentuser );
        username = (TextView) header.findViewById( R.id.activeusername );
        userdp = (CircleImageView) header.findViewById( R.id.userimage );
        // setting firebase.

        useremail.setText( FirebaseAuth.getInstance().getCurrentUser().getEmail() );

        // setting up the nav bar.
        ImageButton diet = (ImageButton)findViewById( R.id.apnadeit2 );
        diet.setEnabled(false);
        diet.setClickable(false);

        // lifestyle

        ImageButton  lifestyle = (ImageButton) findViewById( R.id.dietlifestyle2 );

        lifestyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(CategoricallyFoodItems.this, LifeStyle.class);
                startActivity( intent );
            }
        });

        ImageButton  profile = (ImageButton) findViewById( R.id.profile );

        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(CategoricallyFoodItems.this, UserProfile.class);
                startActivity( intent );
            }
        });

        ImageButton home = (ImageButton) findViewById( R.id.sweethome2 );

        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getActivity().finish();
                Intent intent = new Intent(CategoricallyFoodItems.this, MainActivity.class);
                startActivity( intent );
            }
        });



        // including the search service
        // seacrch functionality.
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        search = (SearchView) findViewById( R.id.search );
        search.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        search.setIconifiedByDefault( false );
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);


        // fetching the value of the clicked item.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clickedid = extras.getString( "information" ); // information contains boolean+gender+age+lifestylecategory.
        }

        Log.d( "iddd", String.valueOf( clickedid ) );


        // creating the reference.
        //get reference to the ExpandableListView
        myList = (ExpandableListView) findViewById( R.id.expandableList );

        // connection.
        retrofit = new Retrofit.Builder().baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );
        FoodCategory fc = new FoodCategory( clickedid );

        requrestingServer( fc );
        userInformation(FirebaseAuth.getInstance().getCurrentUser().getEmail());



        // Setting Drawable layut.
        // setting Drawlable layout
        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( CategoricallyFoodItems.this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        navigationMenu.setNavigationItemSelectedListener( this );


    }

    private void userInformation(String email){

        Call<MemberObject> memberObjectCall = api.fetchingUser( email );

        memberObjectCall.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {

               try{
                username.setText( response.body().getData().getFirstName()+" "+
                response.body().getData().getLastName());

                   Glide.with( CategoricallyFoodItems.this ).load(
                           response.body().getData().getProfilePicture()
                   ).into( userdp );
               }
               catch (Exception e){

               }

            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }
        } );
    }

    private void requrestingServer(  FoodCategory foodcategory) {


        Call<AllCategoryFoodItems> call = api.fooditems( foodcategory );
        call.enqueue( new Callback<AllCategoryFoodItems>() {
            @Override
            public void onResponse(Call<AllCategoryFoodItems> call, final Response<AllCategoryFoodItems> response) {

                try{
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<FoodItems> users = response.body().getData();

                                String[] subcateogry = new String[users.size()];
//                //Sub categpory
                                ////////////////////////////////////////////////////////////// fetching the subcategory  in the array.
                                for (int i = 0; i < users.size(); i++) {
                                    subcateogry[i] = users.get( i ).getSubCategory();
                                }


                                //  passing the data from the array into array list.
                                int i = 0;
                                ArrayList<String> subcategorylist = new ArrayList<>();
                                for (String pic : subcateogry) {
                                    subcategorylist.add( pic );
                                    i++;
                                }


                                Set<String> setString = new HashSet<String>( subcategorylist );
                                subcategorylist.clear();


                                // MAKING THE UNIQUE VALUES FROM THE SUB CATEGORY.
                                ArrayList<String> uniquecategorylist = new ArrayList<>();

                                uniquecategorylist.addAll( setString );


                                ////////////////////////////////// Altherating through the loop to fetch values.
                                for (int k = 0; k < uniquecategorylist.size(); k++) {

                                    /// new array to put all the data of the array.
                                    newArray = new ArrayList<FoodItems>();

//(String name, String amount, String fat, String protein, String carbohydrates, String calorie)
                                    Log.d( " index value", String.valueOf( uniquecategorylist.get( k ) ) );

                                    for (int j = 0; j < subcateogry.length; j++) {
                                        Log.d( " index value J", String.valueOf( subcateogry[j] ) );

                                        if (uniquecategorylist.get( k ).equals( subcateogry[j] )) {

                                            // adding object.
                                            newArray.add(new FoodItems( users.get( j ).getName()
                                                    ,users.get( j ).getAmount(),users.get( j ).getFat()
                                                    ,users.get( j ).getProtein(),users.get( j ).getCarbohydrates(),users.get( j ).getCalorie()));


                                        } // end of if.
                                    }  // end of iner for.


                                    Log.d( " Finsihed", "It is here,"+newArray.size() );




                                    //  exp = newArray;

                                    Log.d( " EXP array size", String.valueOf( newArray.size() ) );

                                    FoodCategory cf = new FoodCategory( uniquecategorylist.get( k ),newArray); // first index of the major array ,, first item.
                                    footcategorylistview.add( cf ); // adding the major list item to the major array.





                                } // end of outer if.
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                                ///////////////////////////////////////////////////////////////////////////////




                                ////////////////////////////////////////////////////////////////////////////////
                                /////////////////////////////////////////////////////////////////////////////


//


                                Log.d( "ssd", String.valueOf( listAdapter.getGroupCount() ) );
                                //  expandAll();
                            }
                            catch (NullPointerException e) {
                            }

                            handler.sendEmptyMessage( 0 );
                        }
                    };

                    Thread runnable = new Thread( r );
                    runnable.start();


                }

                catch (Exception e ){
                    Toast.makeText( CategoricallyFoodItems.this,"Network-error",Toast.LENGTH_LONG ).show();
                }



            }

            @Override
            public void onFailure(Call<AllCategoryFoodItems> call, Throwable t) {
                Log.d( "errormessageregistrat", t.getMessage() );

            }
        } );


    }





    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount(); // fetching the size of the main list.
        for (int i = 0; i < count; i++) {
            myList.expandGroup( i );
        }
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    // live search. (on text change)
    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    // result of the search. (after text change)
    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }


    // setting the navigation menu in the layout.

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

            case R.id.bmical: {
                Log.d( "DLLALQWLQLDLLD",")))_____DDDDS" );
                Intent intent = new Intent( CategoricallyFoodItems.this, com.lifeline.fyp.fyp.diet.BMICalculator.class );
                startActivity( intent );
                break;



            }
            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity( new Intent( this, StartingActivity.class ) );

                break;
            }

        }
        return false;

    }

}


