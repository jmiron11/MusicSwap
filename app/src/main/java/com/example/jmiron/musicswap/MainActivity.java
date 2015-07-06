package com.example.jmiron.musicswap;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends Activity implements NewUserDialogFragment.NewUserDialogListener {

    public static Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectToServer();

        if(!mSocket.connected())
        {
            Log.e("NOCONN", "Error connecting dayummm");
        }

        if(savedInstanceState == null)
        {
            MainFragment mf = MainFragment.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content_frame, mf)
                    .commit();
        }
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
