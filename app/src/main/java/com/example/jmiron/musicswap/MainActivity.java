package com.example.jmiron.musicswap;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if(savedInstanceState == null)
        {
            MainFragment mf = MainFragment.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content_frame, mf)
                    .commit();
        }
    }
}
