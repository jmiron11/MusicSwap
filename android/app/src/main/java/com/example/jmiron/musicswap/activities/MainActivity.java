package com.example.jmiron.musicswap.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.example.jmiron.musicswap.adapters.MainPagerAdapter;
import com.example.jmiron.musicswap.adapters.NewProfilePagerAdapter;
import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    public static Socket mSocket;
    public static String username;

    ViewPager mViewPager;
    MainPagerAdapter mAdapter;
    NewProfilePagerAdapter mProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerHandler.connectToServer();

        /* Set up horizontal paging (swipe) */

        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);

        if(!prevProfileExists()){
            Intent intent = new Intent(this, NewProfileActivity.class);
            startActivity(intent);
        }
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
