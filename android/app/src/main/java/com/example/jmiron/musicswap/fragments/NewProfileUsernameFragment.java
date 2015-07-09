package com.example.jmiron.musicswap.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;
import com.example.jmiron.musicswap.interfaces.NewProfileFragmentInterface;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProfileUsernameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProfileUsernameFragment extends Fragment implements NewProfileFragmentInterface {

    private EditText usernameField;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewProfileUsernameFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public void fragmentBecameVisible() {   }

    @Override
    public void fragmentScrolled(){
        SharedPreferences profile = getActivity().getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor profileEditor = profileEditor = profile.edit();

        profileEditor.putString("username", usernameField.getText().toString().trim());
        profileEditor.commit();
    }

}
