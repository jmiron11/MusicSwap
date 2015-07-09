package com.example.jmiron.musicswap.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.ChatActivity;
import com.example.jmiron.musicswap.activities.MainActivity;

/**
 * Created by jmiron on 7/3/2015.
 */
public class NoConnectionDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_connection)
                .setPositiveButton(R.string.try_connect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.connectToServer();
                        if(MainActivity.mSocket.connected()){
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        MainActivity.connectToServer();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
