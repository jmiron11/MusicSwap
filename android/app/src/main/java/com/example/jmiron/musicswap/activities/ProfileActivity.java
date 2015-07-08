package com.example.jmiron.musicswap.activities;

import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jmiron.musicswap.fragments.ProfileFragment;
import com.example.jmiron.musicswap.R;


public class ProfileActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if(savedInstanceState == null)
        {
            startProfileFragment();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_builder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//
//    @Override
//    public void onNewUserContinue(DialogFragment dialog){
//        TextView username = (TextView) this.findViewById(R.id.username);
//        SharedPreferences profile = this.getSharedPreferences("UserInfo", 0);
//        username.setText(profile.getString("username", "No Profile"));
//    }

    private void startProfileFragment(){
        ProfileFragment pbf = ProfileFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.profile_content_frame, pbf)
                .commit();
    }

}
