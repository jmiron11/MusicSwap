package com.example.jmiron.musicswap.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.fragments.ProfileFragment;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
