package com.example.jmiron.musicswap.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jmiron.musicswap.R;

/**
 * Created by jmiron on 7/15/2015.
 */
public class AddArtistDialogFragment extends DialogFragment {

    public interface AddArtistDialogFragmentListener{
        public void onDialogMessage(String artistName);
    }

    private AddArtistDialogFragmentListener mListener;

    public void setListener(AddArtistDialogFragmentListener mListener)
    {
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_add_artist, null);
        final EditText artistName = (EditText) v.findViewById(R.id.new_artist_field);

        builder.setView(v)
                .setPositiveButton(R.string.add_artist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogMessage(artistName.getText().toString().trim());
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
