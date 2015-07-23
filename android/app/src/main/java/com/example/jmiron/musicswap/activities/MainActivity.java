package com.example.jmiron.musicswap.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.MainPagerAdapter;
import com.example.jmiron.musicswap.handlers.LastFmHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;

public class MainActivity extends AppCompatActivity {

    public static String username;

    ViewPager mViewPager;
    MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerHandler.connectToServer();
        LastFmHandler.init();

        /* Set up horizontal paging (swipe) */

        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int pageNumber) {
                ViewPagerFragmentInterface fragment = (ViewPagerFragmentInterface) mAdapter.instantiateItem(mViewPager, pageNumber);
                if (fragment != null) {
                    fragment.fragmentSelected();
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ViewPagerFragmentInterface fragment = (ViewPagerFragmentInterface) mAdapter.instantiateItem(mViewPager, position);
                if (fragment != null) {
                    fragment.fragmentScrolled();
                }
            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        ServerHandler.closeConnection();
    }

    private boolean prevProfileExists() {
        SharedPreferences profile = this.getSharedPreferences("UserInfo", 0);
        return profile.getBoolean("prevProfile", false);
    }

}
