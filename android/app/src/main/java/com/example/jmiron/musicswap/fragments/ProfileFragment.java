package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;
import com.example.jmiron.musicswap.activities.NewProfileActivity;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;



public class ProfileFragment extends Fragment {
    private TextView username, artist1, artist2, artist3;
    private ImageView albumArt1, albumArt2, albumArt3;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!ServerHandler.isConnected())
            ServerHandler.connectToServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        /* initialize class gui variables */
        username = (TextView) v.findViewById(R.id.username);
        artist1 = (TextView) v.findViewById(R.id.editBand1);
        artist2 = (TextView) v.findViewById(R.id.editBand2);
        artist3 = (TextView) v.findViewById(R.id.editBand3);
        albumArt1 = (ImageView) v.findViewById(R.id.albumArt1);
        albumArt2 = (ImageView) v.findViewById(R.id.albumArt2);
        albumArt3 = (ImageView) v.findViewById(R.id.albumArt3);

        /* assign album art placeholders */
        albumArt1.setImageResource(R.drawable.albumartph40);
        albumArt2.setImageResource(R.drawable.albumartph40);
        albumArt3.setImageResource(R.drawable.albumartph40);

        loadData(); // load profile data from phone

        /* if there is typed data that hasn't been saved */
        if(savedInstanceState != null){
            username.setText(savedInstanceState.getCharSequence("username"));
            artist1.setText(savedInstanceState.getCharSequence("artist1"));
            artist2.setText(savedInstanceState.getCharSequence("artist2"));
            artist3.setText(savedInstanceState.getCharSequence("artist3"));
        }

        /* set button OnClickListeners */
        Button changeUsernameBtn = (Button) v.findViewById(R.id.btnChangeUsername);
        changeUsernameBtn.setOnClickListener(onChangeUserClick());

        Button resetProfileBtn = (Button) v.findViewById(R.id.profileReset);
        resetProfileBtn.setOnClickListener(onResetProfileClick());

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("username", username.getText());
        outState.putCharSequence("artist1", artist1.getText());
        outState.putCharSequence("artist2", artist2.getText());
        outState.putCharSequence("artist3", artist3.getText());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        username.setText(PreferencesHandler.getUsername(getActivity()));
        artist1.setText(PreferencesHandler.getArtist1(getActivity()));
        artist2.setText(PreferencesHandler.getArtist2(getActivity()));
        artist3.setText(PreferencesHandler.getArtist3(getActivity()));
    }

    private Button.OnClickListener onChangeUserClick() {
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    //TODO: Implement change username
                }
            }
        };
        return ret;
    }

    private Button.OnClickListener onResetProfileClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewProfileActivity.class);
                startActivity(intent);
            }
        };
        return ret;
    }
}
