package com.example.jmiron.musicswap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {
    private SharedPreferences.Editor profileEditor;
    private SharedPreferences profile;

    private TextView username;
    private TextView artist1;
    private TextView artist2;
    private TextView artist3;
    private ImageView albumArt1;
    private ImageView albumArt2;
    private ImageView albumArt3;

    private Socket mSocket = MainActivity.mSocket;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
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
        profile = getActivity().getSharedPreferences("UserInfo", 0);
        profileEditor = profile.edit();
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

        Button saveButton = (Button) v.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(onSaveClick());

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String tempUsername = profile.getString("username", "No Profile");
                final String tempBand1 = profile.getString("artist1", "Artist 1");
                final String tempBand2 = profile.getString("artist2", "Artist 2");
                final String tempBand3 = profile.getString("artist3", "Artist 3");

                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        username.setText(tempUsername);
                        artist1.setText(tempBand1);
                        artist2.setText(tempBand2);
                        artist3.setText(tempBand3);
                    }
                });
            }
        }).start();
    }

    private void saveData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                profileEditor.putString("username", username.getText().toString().trim());
                MainActivity.username = username.getText().toString().trim();
                profileEditor.putString("artist1", artist1.getText().toString().trim());
                profileEditor.putString("artist2", artist2.getText().toString().trim());
                profileEditor.putString("artist3", artist3.getText().toString().trim());
                profileEditor.commit();
            }
        }).start();
    }

    private Button.OnClickListener onChangeUserClick() {
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    NewUserDialogFragment newUserDialog = new NewUserDialogFragment();
                    newUserDialog.show(getActivity().getSupportFragmentManager(), "New User");
                    username.setText(profile.getString("username", "No Username"));
                }
            }
        };
        return ret;
    }

    private Button.OnClickListener onSaveClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveData();
                        JSONObject new_profile = new JSONObject();
                        try {
                            new_profile.put("username", username.getText());
                            new_profile.put("artist1", artist1.getText());
                            new_profile.put("artist2", artist2.getText());
                            new_profile.put("artist3", artist3.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!mSocket.connected()) {
                            MainActivity.connectToServer();
                        }
                        mSocket.emit("new_profile", new_profile);
                    }
                }).start();
            }
        };
        return ret;
    }
}
