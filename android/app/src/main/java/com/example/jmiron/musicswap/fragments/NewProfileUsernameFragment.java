package com.example.jmiron.musicswap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProfileUsernameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProfileUsernameFragment extends Fragment implements ViewPagerFragmentInterface {

    private EditText usernameField;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewProfileUsernameFragment.
     */
    public static NewProfileUsernameFragment newInstance() {
        NewProfileUsernameFragment fragment = new NewProfileUsernameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewProfileUsernameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_profile_username, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        usernameField = (EditText) view.findViewById(R.id.usernameField);

        ImageView someLogo = (ImageView) view.findViewById(R.id.logoImage);
        someLogo.setImageResource(R.drawable.squirrel);

        usernameField.setText(PreferencesHandler.getUsername(getActivity()));
    }

    @Override
    public void fragmentBecameVisible() {   }

    @Override
    public void fragmentScrolled(){
        PreferencesHandler.saveUserData(getActivity(), usernameField.getText().toString().trim());
    }

}
