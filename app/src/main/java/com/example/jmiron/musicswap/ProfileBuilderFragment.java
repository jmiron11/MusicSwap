package com.example.jmiron.musicswap;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileBuilderFragment extends Fragment{
    SharedPreferences.Editor profileEditor;
    SharedPreferences profile;

    TextView username;
    EditText band1;
    EditText band2;
    EditText band3;

    private Socket mSocket = MainActivity.mSocket;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static ProfileBuilderFragment newInstance() {
        ProfileBuilderFragment fragment = new ProfileBuilderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileBuilderFragment() {
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
        View v = inflater.inflate(R.layout.fragment_profile_builder, container, false);

        username = (TextView) v.findViewById(R.id.username);
        band1 = (EditText) v.findViewById(R.id.editBand1);
        band2 = (EditText) v.findViewById(R.id.editBand2);
        band3 = (EditText) v.findViewById(R.id.editBand3);

        band1.setEnabled(false);
        band2.setEnabled(false);
        band3.setEnabled(false);

        loadData();

        if(savedInstanceState != null){
            username.setText(savedInstanceState.getCharSequence("username"));
            band1.setText(savedInstanceState.getCharSequence("band1"));
            band2.setText(savedInstanceState.getCharSequence("band2"));
            band3.setText(savedInstanceState.getCharSequence("band3"));
        }

        Button changeUsernameBtn = (Button) v.findViewById(R.id.btnChangeUsername);
        changeUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null)
                {
                    NewUserDialogFragment newUserDialog = new NewUserDialogFragment();
                    newUserDialog.show(getFragmentManager(), "New User");
                    username.setText(profile.getString("username", "No Username"));
                }
            }
        });

        Button saveButton = (Button) v.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                JSONObject new_profile = new JSONObject();
                try {
                    new_profile.put("username", username.getText());
                    new_profile.put("band1", band1.getText());
                    new_profile.put("band2", band2.getText());
                    new_profile.put("band3", band3.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("new_profile", new_profile);
            }
        });

        final Button edit1 = (Button) v.findViewById(R.id.edit1);
        final Button edit2 = (Button) v.findViewById(R.id.edit2);
        final Button edit3 = (Button) v.findViewById(R.id.edit3);

        edit1.setOnClickListener(onEditClick(edit1, band1));
        edit2.setOnClickListener(onEditClick(edit2, band2));
        edit3.setOnClickListener(onEditClick(edit3, band3));

        return v;
    }

    public Button.OnClickListener onEditClick(final Button btn, final EditText text){
        Button.OnClickListener ret = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn.setText("Save");
                text.setEnabled(true);
                btn.setOnClickListener(onSaveClick(btn, text));
            }
        };

        return ret;
    }

    public Button.OnClickListener onSaveClick(final Button btn, final EditText text){
        Button.OnClickListener ret = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btn.setText("Edit");
                text.setEnabled(false);
                btn.setOnClickListener(onEditClick(btn, text));
            }
        };

        return ret;
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
        outState.putCharSequence("band1", band1.getText());
        outState.putCharSequence("band2", band2.getText());
        outState.putCharSequence("band3", band3.getText());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        username.setText(profile.getString("username", "No Profile"));
        band1.setText(profile.getString("band1", "Band 1"));
        band2.setText(profile.getString("band2", "Band 2"));
        band3.setText(profile.getString("band3", "Band 3"));
    }

    private void saveData(){
        profileEditor.putString("username", username.getText().toString().trim());
        profileEditor.putString("band1", band1.getText().toString().trim());
        profileEditor.putString("band2", band2.getText().toString().trim());
        profileEditor.putString("band3", band3.getText().toString().trim());
        profileEditor.commit();
    }
}
