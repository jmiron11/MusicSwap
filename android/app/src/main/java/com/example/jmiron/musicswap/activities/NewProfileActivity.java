package com.example.jmiron.musicswap.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.NewProfilePagerAdapter;
import com.example.jmiron.musicswap.interfaces.NewProfileFragmentInterface;

/**
 * Created by jmiron on 7/8/2015.
 */
public class NewProfileActivity extends AppCompatActivity {
    ViewPager mViewPager;
    NewProfilePagerAdapter mProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        getSupportActionBar().hide();

        mProfileAdapter = new NewProfilePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.new_profile_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                NewProfileFragmentInterface fragment = (NewProfileFragmentInterface) mProfileAdapter.instantiateItem(mViewPager, position);
                if (fragment != null) {
                    fragment.fragmentScrolled();
                }
            }

            @Override
            public void onPageSelected(int position) {
                NewProfileFragmentInterface fragment = (NewProfileFragmentInterface) mProfileAdapter.instantiateItem(mViewPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mViewPager.setAdapter(mProfileAdapter);
    }
}