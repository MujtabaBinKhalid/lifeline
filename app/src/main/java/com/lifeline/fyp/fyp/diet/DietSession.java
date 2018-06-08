package com.lifeline.fyp.fyp.diet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.CheckingCalories;
import com.lifeline.fyp.fyp.models.FetchingCalorie;
import com.lifeline.fyp.fyp.models.FoodArray;
import com.lifeline.fyp.fyp.models.InsertingDietSession;
import com.lifeline.fyp.fyp.models.SendingPlan;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.DetailedCalorieResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FoodItemsResponse;
import com.lifeline.fyp.fyp.retrofit.responses.CaloriesResponse;
import com.lifeline.fyp.fyp.retrofit.responses.InsertSessionResponse;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietSession extends AppCompatActivity {
    private RecyclerView dprv; // dietplanrecyclerview  // recycler view of the selected food iyem
    private RecyclerView.Adapter dpa; // dietplanadapter adaoter of that.
    private Api api, apibase; // setting responses
    Retrofit retrofit, retrofitbase;
    ListView listview; // displaying all the list of the food items.
    Dialog dialog;
    Call<FoodItemsResponse> call; // response
    public ArrayList<FoodArray> allfooditems; // array of the food items fetched
    public ArrayList<String> onlyname; // array of the only food items
    public ArrayList<Model> selectedfood;
    public ArrayList<FetchingCalorie> selectedfooddetail; // info of the selected food item
    public EditText searchbar; // serach bar
    private TextView selected;
    private FloatingActionButton btn; // next btn
    AlertDialog.Builder builder; // prompt .
    EditText grams, amountedit; // edit text of the prompt
    String[] seperator; // seperator
    int postion; // posotion of the selected food item
    private boolean check1, check2; // checking the state of which text field has been filled.
    private String calorieTaken;
    ArrayAdapter<String> adapter;
    private TextView name; // sdn = selected diet name.
    private Button fats;
    private Button proteins;
    private Button carbohydrates;
    private Button calories;
    String unittext;
    private Integer dietPlanId;
    private String sc;
    private String msc;
    AlertDialog alert;
RelativeLayout layout;
ProgressDialog progressDialog;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            adapter = new ArrayAdapter<String>( DietSession.this, R.layout.fooditemname, R.id.itemname, onlyname );
            listview.setAdapter( adapter );
            progressDialog.hide();
            layout.setVisibility( View.VISIBLE );



        }
    };

    Handler recyclerhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            dpa = new AllFoodIemsAdapter( DietSession.this, selectedfooddetail );
            dprv.setAdapter( dpa );
            dpa.notifyDataSetChanged();


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diet_session );

        layout =(RelativeLayout) findViewById( R.id.layout ) ;
        progressDialog = new ProgressDialog( this );
        progressDialog.setTitle( "Fetching Data" );
        progressDialog.setMessage( "Wait.. " );
        progressDialog.show();
        progressDialog.setCancelable( false );
        searchbar = (EditText) findViewById( R.id.search );
        selectedfood = new ArrayList<>();

        btn = (FloatingActionButton) findViewById( R.id.sessioncompleted );
        // btn setting.

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText( DietSession.this, "Session ended..", Toast.LENGTH_SHORT ).show();
                endingSession();

            }
        } );

        selected = (TextView) findViewById( R.id.selecteditem );


        // fetching the dietplanid..

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dietPlanId = extras.getInt( "id" );

        Log.d( "dietPlanId", String.valueOf( dietPlanId ) );

        dprv = (RecyclerView) findViewById( R.id.selectedd );
        LinearLayoutManager dpm = new LinearLayoutManager( DietSession.this, LinearLayoutManager.VERTICAL, false );
        dprv.setLayoutManager( dpm );


        // for fetching the foodiems,
        retrofit = new Retrofit.Builder()
                .baseUrl( Api.FoodItems )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

//                  // for the rest of the work,=.
        retrofitbase = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        apibase = retrofitbase.create( Api.class );


        // Locate the EditText in listview_main.xml


        // lisrview.
        listview = (ListView) findViewById( R.id.foodlistview );
        selectedfooddetail = new ArrayList<>();


        FetchingItmes();
        onItemClick();
        implementingSearch();


    }

    // fetching the items from the database.
    public void FetchingItmes() {

        allfooditems = new ArrayList<>();
        onlyname = new ArrayList<>();


        call = api.listoffood();
        call.enqueue( new Callback<FoodItemsResponse>() {


            @Override
            public void onResponse(Call<FoodItemsResponse> call, final Response<FoodItemsResponse> response) {
                Log.d( "ftrt", "hello" );
                try {
                    Runnable runnablelist1 = new Runnable() {
                        @Override
                        public void run() {

                            try {
                                List<FoodArray> food = response.body().getData();
                                for (int i = 0; i < food.size(); i++) {

                                    allfooditems.add( new FoodArray( food.get( i ).getName(),
                                            food.get( i ).getMeasure(), food.get( i ).getWeight(),
                                            food.get( i ).getCalories(), food.get( i ).getFats(),
                                            food.get( i ).getCarbohydrates(), food.get( i ).getProteins() ) );

                                    onlyname.add( food.get( i ).getName() );


                                }
                                Log.d( "sise1", String.valueOf( allfooditems.size() ) );
                                Log.d( "sise1", String.valueOf( onlyname.size() ) );

                            } catch (NullPointerException e) {
                                Log.d( "failing", String.valueOf( response ) );
                            }
                            /////////////////////////


                            handler.sendEmptyMessage( 0 );
                            //////////////////////////////////////
                        }
                    };

                    Thread runnable1 = new Thread( runnablelist1 );
                    runnable1.start();

                } catch (Exception e) {
                    Toast.makeText( DietSession.this, "Network-error", Toast.LENGTH_LONG ).show();
                }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                try {
//                    Runnable runnablelist2 = new Runnable() {
//                        @Override
//                        public void run() {
//
//                            try {
//                                List<FoodArray> food = response.body().getData();
//                                for (int i = 320; i < 640; i++) {
//
//                                    allfooditems.add( new FoodArray( food.get( i ).getName(),
//                                            food.get( i ).getMeasure(), food.get( i ).getWeight(),
//                                            food.get( i ).getCalories(), food.get( i ).getFats(),
//                                            food.get( i ).getCarbohydrates(), food.get( i ).getProteins() ) );
//
//                                    onlyname.add( food.get( i ).getName() );
//
//
//                                }
//                                Log.d( "sise", String.valueOf( allfooditems.size() ) );
//                                Log.d( "sise", String.valueOf( onlyname.size() ) );
//
//                            } catch (NullPointerException e) {
//                                Log.d( "failing", String.valueOf( response ) );
//                            }
//                            /////////////////////////
//
//
//                            handler.sendEmptyMessage( 0 );
//                            //////////////////////////////////////
//                        }
//                    };
//
//                    Thread runnable2 = new Thread( runnablelist2 );
//                    runnable2.start();
//
//                } catch (Exception e) {
//                    Toast.makeText( DietSession.this, "Network-error", Toast.LENGTH_LONG ).show();
//                }
////////////////////////////////////////////////////////////////////////////////////////////////
//                try {
//                    Runnable runnablelist3 = new Runnable() {
//                        @Override
//                        public void run() {
//
//                            try {
//                                List<FoodArray> food = response.body().getData();
//                                for (int i = 640; i < 960; i++) {
//
//                                    allfooditems.add( new FoodArray( food.get( i ).getName(),
//                                            food.get( i ).getMeasure(), food.get( i ).getWeight(),
//                                            food.get( i ).getCalories(), food.get( i ).getFats(),
//                                            food.get( i ).getCarbohydrates(), food.get( i ).getProteins() ) );
//
//                                    onlyname.add( food.get( i ).getName() );
//
//
//                                }
//                                Log.d( "sise3", String.valueOf( allfooditems.size() ) );
//                                Log.d( "sise3", String.valueOf( onlyname.size() ) );
//
//                            } catch (NullPointerException e) {
//                                Log.d( "failing", String.valueOf( response ) );
//                            }
//                            /////////////////////////
//
//
//                            handler.sendEmptyMessage( 0 );
//                            //////////////////////////////////////
//                        }
//                    };
//
//                    Thread runnable3 = new Thread( runnablelist3 );
//                    runnable3.start();
//
//                } catch (Exception e) {
//                    Toast.makeText( DietSession.this, "Network-error", Toast.LENGTH_LONG ).show();
//                }
//
//
//

/////////////////////////////////////////////////////////////////////////////////


//String name, String measure, Double weight, Double calories, Double fats, Double carbohydrates, Double proteins)


            }

            @Override
            public void onFailure(Call<FoodItemsResponse> call, Throwable t) {

                Toast.makeText( DietSession.this, "Failed", Toast.LENGTH_SHORT ).show();

            } // failure ending
        } ); // call response ending


    }

    // onlclick event on the listview.
    public void onItemClick() {
        // onclick event.

        listview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                postion = onlyname.indexOf( adapter.getItem( i ) );

                Log.d( "qwq1", onlyname.get( postion ) );
                Log.d( "posqwq1", String.valueOf( postion ) );
                Log.d( "qwq1", allfooditems.get( postion ).getName() );


                Log.d( "before1", allfooditems.get( postion ).getName() );
                Log.d( "before1", allfooditems.get( postion ).getMeasure() );

                seperator = allfooditems.get( postion ).getMeasure().split( " " );

                Log.d( "sep", seperator[0] );
                Log.d( "seo", seperator[1] );


                // fetching details of the pbject.
                CheckingCalories cc = new CheckingCalories( allfooditems.get( postion ).getName(),
                        allfooditems.get( postion ).getMeasure() );
                unittext = seperator[1];
                //Details( seperator[1], postion );

                fetchingDetail( cc, postion, unittext );


            }
        } );

    }

    // fetching details
    private void fetchingDetail(final CheckingCalories cc, final int position, final String unit) {

        Call<DetailedCalorieResponse> call2 = api.calories( cc );
        call2.enqueue( new Callback<DetailedCalorieResponse>() {
            @Override
            public void onResponse(Call<DetailedCalorieResponse> call, final Response<DetailedCalorieResponse> response) {

                try {

                    Runnable itemgram = new Runnable() {
                        @Override
                        public void run() {

                            Log.d( "measure", String.valueOf( response ) );


                        }
                    };
                    FetchingCalorie fc = new FetchingCalorie( response.body().getData().getName(),
                            response.body().getData().getFat(), response.body().getData().getCarbohydrates(),
                            response.body().getData().getProteins() );

                    Log.d( "measurebb", String.valueOf( fc.getName() ) );
                    Log.d( "measurebb", String.valueOf( fc.getFat() ) );
                    Log.d( "measurebb", String.valueOf( fc.getCarbohydrates() ) );
                    Log.d( "measurebb", String.valueOf( fc.getProteins() ) );

                    Thread runnablegram = new Thread( itemgram );
                    runnablegram.start();

                    Details( unit, position, fc );


                } catch (NullPointerException e) {
                    Log.d( "its here", String.valueOf( response ) );


                }


            }

            @Override
            public void onFailure(Call<DetailedCalorieResponse> call, Throwable t) {

            }
        } );


    }

    // serach bar implementation.
    public void implementingSearch() {


        try {
            Runnable searchinglist = new Runnable() {
                @Override
                public void run() {
                    searchbar.addTextChangedListener( new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            try {
                                DietSession.this.adapter.getFilter().filter( charSequence );
                            } catch (NullPointerException e) {

                            }
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    } );

                }
            };


            Thread seachingthread = new Thread( searchinglist );
            seachingthread.start();


        } catch (NullPointerException e) {
            Toast.makeText( DietSession.this, "Loading... Wait", Toast.LENGTH_SHORT ).show();
        }

    }

    // prompt will appear so that user can enter the detail of what they want.
    public void Details(String unit, final int postionvalue, final FetchingCalorie fcc) {

        Log.d( "its here", String.valueOf( "dialogggg" ) );
        builder = new AlertDialog.Builder( DietSession.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = inflater.inflate( R.layout.dialog_prompt, null );
        builder.setView( customView );
        final TextView settingtext = (TextView) customView.findViewById( R.id.textamount );
        settingtext.setText( unit );
        // reference the textview of custom_popup_dialog
        grams = (EditText) customView.findViewById( R.id.gram );
        amountedit = (EditText) customView.findViewById( R.id.amount );

        // intialization of the fieds.
        name = (TextView) customView.findViewById( R.id.itemname );
        fats = (Button) customView.findViewById( R.id.fatsbtn );
        proteins = (Button) customView.findViewById( R.id.proteinsbtn );
        carbohydrates = (Button) customView.findViewById( R.id.carbobtn );

        // setting the values.
        name.setText( fcc.getName() );
        fats.setText( fcc.getFat() );
        proteins.setText( fcc.getProteins() );
        carbohydrates.setText( fcc.getCarbohydrates() );
         dialog = new Dialog( DietSession.this );


        // validation of the first textfield
        validation( grams, amountedit );


        builder.setTitle( "Calculating Calories" );
        builder.setCancelable( true );

        Button calculate = (Button) customView.findViewById( R.id.ok );
        calculate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setting the buttons and getting their text.


                if (TextUtils.isEmpty( grams.getText() ) && (TextUtils.isEmpty( amountedit.getText() ))) {
                    Toast.makeText( DietSession.this, "Fill in he fields to continue", Toast.LENGTH_SHORT ).show();

                }
                else if (check1) {
                    // get calories from gram.


                    CheckingCalories cc = new CheckingCalories( allfooditems.get( postionvalue ).getName(),
                            allfooditems.get( postionvalue ).getMeasure(), grams.getText().toString() );

                  //  dialog.dismiss();

                    selected.setVisibility( View.VISIBLE );
                    btn.setVisibility( View.VISIBLE );
                    getCalbyGram( cc, fcc.getName() );
                    Toast.makeText( DietSession.this, "Calories Calculated", Toast.LENGTH_SHORT ).show();


                }

                else if (check2) {

                    CheckingCalories cc = new CheckingCalories( allfooditems.get( postionvalue ).getName(),
                            allfooditems.get( postionvalue ).getMeasure(), amountedit.getText().toString() );




                    selected.setVisibility( View.VISIBLE );
                    btn.setVisibility( View.VISIBLE );
                    getCalbyMeasure( cc, fcc.getName() );
                  //  dialog.dismiss();
                    Toast.makeText( DietSession.this, "Calories Calculated", Toast.LENGTH_SHORT ).show();

                }

            }
        } );


        builder.setView( customView );
        dialog = builder.create();
        // dialog.create();
        dialog.show();
    }

    // performing validationns on the edit text of the prompt.
    public void validation(final EditText grams, final EditText measure) {


/// second text field texting .
        grams.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String pattern = "^[0-9.]{1,5}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    grams.setTextColor( Color.RED );
                    measure.setFocusableInTouchMode( true );
//                            measure.setVisibility( View.VISIBLE );
                    check1 = false;
                    check2 = false;

                } else {
                    grams.setTextColor( Color.BLACK );
                    measure.setFocusable( false );

                    //measure.setVisibility( View.INVISIBLE );
                    check1 = true;
                    check2 = false;
                }

            }
        } );

        // second text field
        measure.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pattern = "^[0-9.]{1,5}$";
                Pattern regEx = Pattern.compile( pattern );
                Matcher matches = regEx.matcher( editable );

                if (!matches.matches()) {
                    measure.setTextColor( Color.RED );
                    grams.setFocusableInTouchMode( true );
                    //grams.setVisibility( View.VISIBLE );
                    check2 = false;
                    check1 = false;

                } else {
                    measure.setTextColor( Color.BLACK );
                    grams.setFocusable( false );
                    // grams.setVisibility( View.INVISIBLE );
                    check2 = true;
                    check1 = false;
                }

            }
        } );
    }

    // measuring the calories by the grams.
    public void getCalbyGram(final CheckingCalories cc, final String name) {

        Call<CaloriesResponse> call2 = api.fetchingcaloriesbygram( cc );
        call2.enqueue( new Callback<CaloriesResponse>() {
            @Override
            public void onResponse(Call<CaloriesResponse> call, final Response<CaloriesResponse> response) {

                try {

                    Runnable itemgram = new Runnable() {
                        @Override
                        public void run() {

                            Log.d( "measure", String.valueOf( response ) );
                            Log.d( "measuregram", String.valueOf( response.body().getData().getCalorie() ) );
                            calorieTaken = response.body().getData().getCalorie();
                            String substring = calorieTaken.substring( 0, calorieTaken.length() / 2 );

                            FetchingCalorie fc = new FetchingCalorie( name, substring );

                            selectedfooddetail.add( fc );
dialog.dismiss();
                            recyclerhandler.sendEmptyMessage( 0 );


                        }
                    };


                    Thread runnablegram = new Thread( itemgram );
                    runnablegram.start();
                    //selectedData( cc, substring );


                } catch (NullPointerException e) {
                    Log.d( "its here", String.valueOf( response ) );


                }


            }

            @Override
            public void onFailure(Call<CaloriesResponse> call, Throwable t) {

            }
        } );


    }

    // measuring the calroies by the unit of the food item
    public void getCalbyMeasure(final CheckingCalories cc, final String name) {


        Call<CaloriesResponse> callcc = api.fetchingcaloriesbymeasure( cc );
        callcc.enqueue( new Callback<CaloriesResponse>() {
            @Override
            public void onResponse(Call<CaloriesResponse> call, final Response<CaloriesResponse> response) {

                try {

                    Runnable itemmeasure = new Runnable() {
                        @Override
                        public void run() {

                            Log.d( "measue", String.valueOf( response ) );
                            Log.d( "measuemeasuure", String.valueOf( response.body().getData().getCalorie() ) );


                            FetchingCalorie fc = new FetchingCalorie( name, response.body().getData().getCalorie() );

                            selectedfooddetail.add( fc );
                                        dialog.dismiss();
                            recyclerhandler.sendEmptyMessage( 0 );
                        }
                    };


                    calorieTaken = response.body().getData().getCalorie();
                    String substring = calorieTaken.substring( 0, calorieTaken.length() / 2 );

                    Thread runnablemeasure = new Thread( itemmeasure );
                    runnablemeasure.start();
                    // selectedData( cc, calorieTaken  );


                } catch (NullPointerException e) {
                    Log.d( "its here", String.valueOf( response ) );


                }


            }

            @Override
            public void onFailure(Call<CaloriesResponse> call, Throwable t) {

            }
        } );


    }

    // sending data to the recycler to being dispalyyed in the selected food cateory textvoew
    public void selectedData(CheckingCalories cc, final String calorieTaken) {


        Call<DetailedCalorieResponse> call2 = api.calories( cc );
        call2.enqueue( new Callback<DetailedCalorieResponse>() {
            @Override
            public void onResponse(Call<DetailedCalorieResponse> call, final Response<DetailedCalorieResponse> response) {

                try {

                    Runnable alldetail = new Runnable() {
                        @Override
                        public void run() {

                            Log.d( "measuedetailed", String.valueOf( response ) );
                            Log.d( "measuedetailed", String.valueOf( response.body().getData().getFat() ) );
                            Log.d( "measuedetailed", String.valueOf( response.body().getData().getName() ) );
                            Log.d( "measuedetailed", String.valueOf( calorieTaken ) );

                            //String name, String calories, String fat, String carbohydrates, String proteins
//                            FetchingCalorie fc = new FetchingCalorie( response.body().getData().getName(),
//                                     response.body().getData().getFat(), response.body().getData().getCarbohydrates(),
//                                    response.body().getData().getProteins() );

                            //  selectedfooddetail.add( fc );

                            recyclerhandler.sendEmptyMessage( 0 );


                        }
                    };


                    Thread rundetail = new Thread( alldetail );
                    rundetail.start();
                    // Details();


                } catch (NullPointerException e) {
                    Log.d( "its here", String.valueOf( response ) );

                }
            }

            @Override
            public void onFailure(Call<DetailedCalorieResponse> call, Throwable t) {

            }
        } );
    }

    // ending session.
    private void endingSession() {

        double sumCalories = 0;
        for (int i = 0; i < selectedfooddetail.size(); i++) {
            sumCalories += Double.parseDouble( selectedfooddetail.get( i ).getCalories() );
        }

        String foodeaten = "";
        String newfoodeaten = "";

        for (int j = 0; j < selectedfooddetail.size(); j++) {
            foodeaten += selectedfooddetail.get( j ).getName() + ";";

            newfoodeaten = foodeaten.substring( 0, foodeaten.length() - 1 );
            // foodeaten = selectedfooddetail.get( j ).getName()+";"+foodeaten;

        }

        Log.d( "calories", String.valueOf( dietPlanId ) );
        Log.d( "calories", String.valueOf( sumCalories ) );
        Log.d( "calories", newfoodeaten );

        InsertingDietSession ids = new InsertingDietSession( dietPlanId, sumCalories, newfoodeaten );


        InsertingSession( ids );
    }

    public void InsertingSession(final InsertingDietSession ids) {
        Log.d( "resssssss", String.valueOf( "hrh" ) );

        final SendingPlan sp = new SendingPlan( "noactive" );
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                Call<InsertSessionResponse> callinserting = apibase.insertingSession( ids );
                callinserting.enqueue( new Callback<InsertSessionResponse>() {
                    @Override
                    public void onResponse(Call<InsertSessionResponse> call, Response<InsertSessionResponse> response) {
                        Log.d( "qwqwq", String.valueOf( response ) );
                        Log.d( "resssssss", String.valueOf( response.body() ) );
                        sc = response.code() + "";
                        Log.d( "qwqwq", String.valueOf( sc ) );


                        if (sc.equals( "200" )) {
                            msc = "";
                            msc = response.body().getMessage();

                            Log.d( "Quettta", String.valueOf( response.message() ) );
                            Log.d( "QuettaGladiattiors", String.valueOf( response.body().getMessage() ) );
                            Log.d( "QuettaGiattsMsg", String.valueOf( msc ) );

                            if (msc.equals( "Ok" )) {
                                Log.d( "tewadi serer", "tawade swei," );
                                Intent intent = new Intent( DietSession.this, DietMain.class );
                                finish();
                                startActivity( intent );
                            } else if (!msc.equals( "Ok" )) {
                                Log.d( "tewadi serer", "Lahore swei," );


                                AlertDialog.Builder builder = new AlertDialog.Builder( DietSession.this );
                                builder.setMessage( "Plan Ended because you weren't following it rightly!" )
                                        .setCancelable( false )
                                        .setPositiveButton( "Exit", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                Call<SendingPlan> callinserting = apibase.updateDietStatus( dietPlanId, sp );
                                                callinserting.enqueue( new Callback<SendingPlan>() {
                                                    @Override
                                                    public void onResponse(Call<SendingPlan> call, Response<SendingPlan> response) {
                                                        Log.d( "resssssss", String.valueOf( response ) );
                                                        Log.d( "resssssss", String.valueOf( response.body() ) );
                                                        Intent intent = new Intent( DietSession.this, DietMain.class );
                                                        finish();
                                                        startActivity( intent );


                                                    }

                                                    @Override
                                                    public void onFailure(Call<SendingPlan> call, Throwable t) {
                                                        Log.d( "errormessageregistrat", t.getMessage() );

                                                    }
                                                } );


                                            }
                                        } );
                                AlertDialog alert = builder.create();
                                alert.show();

                            }


                        } else {
                            Log.d( "aaaaas", String.valueOf( "bc" ) );


                            Call<SendingPlan> callinserting =apibase.updateDietStatus( dietPlanId, sp);
                            callinserting.enqueue(new Callback<SendingPlan>() {
                                @Override
                                public void onResponse(Call<SendingPlan> call, Response<SendingPlan> response) {
                                    Log.d( "resssssss", String.valueOf( response ) );
                                    Log.d( "resssssss", String.valueOf( response.body() ) );

                            // Plan Just Ended. New session.
                            AlertDialog.Builder builder = new AlertDialog.Builder( DietSession.this );
                            builder.setMessage( "Plan Ended because you weren't following it rightly!" )
                                    .setCancelable( false )
                                    .setPositiveButton( "Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                                    Intent intent = new Intent( DietSession.this,DietMain.class );
                                                    finish();
                                                    startActivity(intent);

                                        }
                                    } );
                            AlertDialog alert = builder.create();
                            alert.show();


                                }

                                @Override
                                public void onFailure(Call<SendingPlan> call, Throwable t) {
                                    Log.d("errormessageregistrat", t.getMessage() );

                                }
                            });
                        }


                    }

                    @Override
                    public void onFailure(Call<InsertSessionResponse> call, Throwable t) {

                    }
                } );


            }
        };

        Thread runnable2 = new Thread( r2 );
        runnable2.start();


    }
}

