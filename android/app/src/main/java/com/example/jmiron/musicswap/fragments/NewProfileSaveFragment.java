package com.example.jmiron.musicswap.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.example.jmiron.musicswap.interfaces.NewProfileFragmentInterface;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProfileSaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProfileSaveFragment extends Fragment implements NewProfileFragmentInterface{

    private SharedPreferences profile;
    private SharedPreferences.Editor profileEditor;
    TextView username, artist1, artist2, artist3;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewProfileSaveFragment.
     */
    public static NewProfileSaveFragment newInstance(String param1, String param2) {
        NewProfileSaveFragment fragment = new NewProfileSaveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewProfileSaveFragment() {
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
        return inflater.inflate(R.layout.fragment_new_profile_save, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        /* load the profile! */

        /* assign class gui variables */
        profile = getActivity().getSharedPreferences("UserInfo", 0);
        profileEditor = profile.edit();
        username = (TextView) view.findViewById(R.id.new_profile_username);
        artist1 = (TextView) view.findViewById(R.id.new_profile_band1);
        artist2 = (TextView) view.findViewById(R.id.new_profile_band2);
        artist3 = (TextView) view.findViewById(R.id.new_profile_band3);

        Button save = (Button) view.findViewById(R.id.new_profile_btnSave);
        save.setOnClickListener(onSaveClick());

        loadData();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private Button.OnClickListener onSaveClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(!ServerHandler.isConnected())
                    ServerHandler.connectToServer();

                String saveUsername = username.getText().toString().trim();
                String saveArtist1 = artist1.getText().toString().trim();
                String saveArtist2 = artist2.getText().toString().trim();
                String saveArtist3 = artist3.getText().toString().trim();
                PreferencesHandler.saveUserData(getActivity(), saveUsername, saveArtist1, saveArtist2, saveArtist3);
                ServerHandler.saveProfile(saveUsername, saveArtist1, saveArtist2, saveArtist3);
                getActivity().finish();
            }
        };

        return ret;
    }

    private void loadData(){
        username.setText(PreferencesHandler.getUsername(getActivity()));
        artist1.setText(PreferencesHandler.getArtist1(getActivity()));
        artist2.setText(PreferencesHandler.getArtist2(getActivity()));
        artist3.setText(PreferencesHandler.getArtist3(getActivity()));
    }

    @Override
    public void fragmentBecameVisible() {
        loadData();
    }

    @Override
    public void fragmentScrolled() { }





}
