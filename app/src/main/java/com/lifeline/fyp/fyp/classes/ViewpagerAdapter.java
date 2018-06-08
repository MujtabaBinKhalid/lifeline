package com.lifeline.fyp.fyp.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 2/4/2018.
 */

public class ViewpagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabtitles= new ArrayList<>();

    public void addFragments(Fragment addedfragments,String addedtabtitles){

        //
        this.fragments.add(addedfragments);
        this.tabtitles.add(addedtabtitles);
    }
    // constructor
    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // getting current fragment
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // getting total fragments
    @Override
    public int getCount() {
        return fragments.size();
    }

    // getting tab titles
    @Override
    public CharSequence getPageTitle(int position){
        return tabtitles.get(position);
    }
}
