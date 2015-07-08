package com.example.jmiron.musicswap;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends FragmentActivity implements NewUserDialogFragment.NewUserDialogListener {

    public static Socket mSocket;
    public static String username;

    ViewPager mViewPager;
    MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
//        mViewPager = (ViewPager)findViewById(R.id.mainPager);
//        mViewPager.setAdapter(mAdapter);

        connectToServer();

        if(!mSocket.connected())
        {
            Log.e("NOCONN", "Error connecting dayummm");
        }

        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);


    }

    public static void connectToServer(){
        try {
            mSocket = IO.socket("http://10.0.2.2:8080");
        } catch (URISyntaxException e) {
            Log.e("IDK", "Error creating socket");
        }
        mSocket.connect();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try {
            mSocket.close();
            mSocket.disconnect();
        } catch (Exception e){
            Log.e("IDK","Error closing socket");
        }
    }

    @Override
    public void onNewUserContinue(DialogFragment dialog){
        //update the main screen
    }
}
