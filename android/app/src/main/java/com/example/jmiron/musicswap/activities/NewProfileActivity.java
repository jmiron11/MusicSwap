package com.example.jmiron.musicswap.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.NewProfilePagerAdapter;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;

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
                ViewPagerFragmentInterface fragment = (ViewPagerFragmentInterface) mProfileAdapter.instantiateItem(mViewPager, position);
                if (fragment != null) {
                    fragment.fragmentScrolled();
                }
            }

            @Override
            public void onPageSelected(int position) {
                ViewPagerFragmentInterface fragment = (ViewPagerFragmentInterface) mProfileAdapter.instantiateItem(mViewPager, position);
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