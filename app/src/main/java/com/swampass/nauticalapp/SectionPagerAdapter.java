package com.swampass.nauticalapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Leaky Pete on 6/17/2017.
 */

class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               InfoFragment infoFrag = new InfoFragment();
               return  infoFrag;
           case 1:
               return new EditInfoFragment();
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
