package com.example.jmiron.musicswap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by jmiron on 7/4/2015.
 */
public class NewUserDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText usernameInput = new EditText(getActivity());

        builder.setMessage(R.string.new_user)
                .setView(usernameInput)
                .setNeutralButton(R.string.cont, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences profile = getActivity().getPreferences(0);
                        SharedPreferences.Editor profileEditor = profile.edit();

                        profileEditor.putString("username", usernameInput.getText().toString().trim());
                        profileEditor.putBoolean("first", false);
                        profileEditor.apply();
                    }
                });

        return builder.create();
    }
}
