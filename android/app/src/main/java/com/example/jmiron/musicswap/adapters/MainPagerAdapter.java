package com.example.jmiron.musicswap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jmiron.musicswap.fragments.MainFragment;
import com.example.jmiron.musicswap.fragments.MatchFragment;
import com.example.jmiron.musicswap.fragments.ProfileFragment;

/**
 * Created by jmiron on 7/7/2015.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new MatchFragment();
            case 1:
                return new MainFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
