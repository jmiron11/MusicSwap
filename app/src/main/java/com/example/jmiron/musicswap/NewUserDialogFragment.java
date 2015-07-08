package com.example.jmiron.musicswap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by jmiron on 7/4/2015.
 */
public class NewUserDialogFragment extends DialogFragment {

    public interface NewUserDialogListener{
        void onNewUserContinue(DialogFragment Dialog);
    }

    private NewUserDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText usernameInput = new EditText(getActivity());

        builder.setMessage(R.string.new_user)
                .setView(usernameInput)
                .setNeutralButton(R.string.cont, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences profile = getActivity().getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor profileEditor = profile.edit();

                        profileEditor.putString("username", usernameInput.getText().toString().trim());
                        Log.e("DUNNO", usernameInput.getText().toString().trim());
                        profileEditor.putBoolean("first", false);
                        profileEditor.apply();
                        mListener.onNewUserContinue(NewUserDialogFragment.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mListener = (NewUserDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NewUserDialogListener");
        }
    }

}
