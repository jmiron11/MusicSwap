package com.example.jmiron.musicswap;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private Button chatBtn;
    private Button suggestBtn;
    private Button editProfileBtn;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_main,container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences profile = getActivity().getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor profileEditor = profile.edit();

        boolean firstStart = profile.getBoolean("first", true);

        if(firstStart){
            if(getActivity() != null){
                NewUserDialogFragment newUserDialog = new NewUserDialogFragment();
                newUserDialog.show(getFragmentManager(), "New User");
            }
        }

        chatBtn = (Button) view.findViewById(R.id.btnFindChat);
        suggestBtn = (Button) view.findViewById(R.id.btnSuggestArtist);
        editProfileBtn = (Button) view.findViewById(R.id.btnEdit);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null)
                {
                    if(MainActivity.mSocket.connected())
                    {
                        //TODO: Add username to joining chat
                        MainActivity.mSocket.emit("chat_user_join");
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        NoConnectionDialogFragment noConnDialog = new NoConnectionDialogFragment();
                        noConnDialog.show(getFragmentManager(), "No Connection");
                    }
                }
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(getActivity() != null)
                {
                    Intent intent = new Intent(getActivity(), ProfileBuilderActivity.class);
                    startActivity(intent);
                }
            }
        });

        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
