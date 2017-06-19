package com.swampass.nauticalapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by That Sexy Taylor on 6/19/2017.
 */

class ConnectionsPagerAdapter extends FragmentPagerAdapter{


    public ConnectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PokeFragment pokefragment = new PokeFragment();
                return pokefragment;


            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 2:
                OmegleFragment omegleFragment = new OmegleFragment();
                return omegleFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int postition){

      switch (postition){
          case 0:
              return "POKES";
          case 1:
              return "Chats";
          case 2:
              return "Omegle";

          default:
              return null;
      }

    }
}
