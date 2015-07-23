package com.example.jmiron.musicswap.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;

/**
 * Created by jmiron on 7/23/2015.
 */
public class NewUserDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_new_user, null);
        final EditText username = (EditText) v.findViewById(R.id.new_username);

        builder.setView(v)
                .setPositiveButton(R.string.add_artist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesHandler.saveUserData(getActivity(), username.getText().toString().trim());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
