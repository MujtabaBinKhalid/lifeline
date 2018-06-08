package com.lifeline.fyp.fyp.lifestyle;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.ViewpagerAdapter;

/**
 * Created by Mujtaba_khalid on 11/25/2017.
 */

public class LifeStyle extends AppCompatActivity {

    Toolbar mtoolbar;
    TabLayout tablayout;
    ViewPager viewpager;
    ViewpagerAdapter viewpagerAdapter;
    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkInternetConenction()) {

            setContentView( R.layout.no_iternet );
          ImageButton  button = (ImageButton) findViewById( R.id.internetbtn );

            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity( getIntent() );
                }

            } );
        }

        else if(checkInternetConenction()) {
            setContentView(R.layout.activity_life_style);

            getSupportActionBar().setElevation(0);


            Bundle extras = getIntent().getExtras();
            if (extras != null) {
//                email = extras.getString("information"); // information contains boolean+gender+age.

            }
email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


            tablayout = (TabLayout) findViewById(R.id.tablayout);
            viewpager = (ViewPager) findViewById(R.id.viewpagersuggestions);

            viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
            viewpagerAdapter.addFragments(new Suggestions(),"Suggestions");
            viewpagerAdapter.addFragments(new Skin_Tips(),"Skin Tips");
            viewpager.setAdapter(viewpagerAdapter);
            tablayout.setupWithViewPager(viewpager);
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public String getEmail(){
                return email;
    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService( getBaseContext().CONNECTIVITY_SERVICE );

        // Check for network connections
        if (connec.getNetworkInfo( 0 ).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo( 0 ).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo( 1 ).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

}
