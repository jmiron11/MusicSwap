package com.example.jmiron.musicswap.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;
import com.example.jmiron.musicswap.adapters.MainPagerAdapter;
import com.example.jmiron.musicswap.interfaces.NewProfileFragmentInterface;

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
    // TODO: Rename and change types and number of parameters
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
        loadData();
    }

    private Button.OnClickListener onSaveClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                saveData();
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
                        if (!MainActivity.mSocket.connected()) {
                            MainActivity.connectToServer();
                        }
                        MainActivity.mSocket.emit("new_profile", new_profile);
                    }
                }).start();
                getActivity().finish();
            }
        };

        return ret;
    }

    private void loadData(){
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
        profileEditor.putString("username", username.getText().toString().trim());
        MainActivity.username = username.getText().toString().trim();
        profileEditor.putString("artist1", artist1.getText().toString().trim());
        profileEditor.putString("artist2", artist2.getText().toString().trim());
        profileEditor.putString("artist3", artist3.getText().toString().trim());
        profileEditor.putBoolean("prevProfile", true);
        profileEditor.commit();
    }

    @Override
    public void fragmentBecameVisible() {
        loadData();
    }

    @Override
    public void fragmentScrolled() { }





}
