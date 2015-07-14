package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.ProfileArtistAdapter;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private ArrayList<ProfileArtistContainer> mArtistArray = new ArrayList<>();
    private ProfileArtistAdapter mArtistAdapter;
    private RecyclerView mArtistView;

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

        // Inflate the layout for this fragment
        mArtistView = (RecyclerView) v.findViewById(R.id.profileArtistList);
        mArtistView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArtistView.setAdapter(mArtistAdapter); // assign the adapter to the infoview

        /* if there is typed data that hasn't been saved */
        if(savedInstanceState != null){

        }


        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mArtistAdapter = new ProfileArtistAdapter(getActivity(), mArtistArray);

        ArrayList<ProfileArtistContainer> prevArtists = PreferencesHandler.getArtists(getActivity());
        if(prevArtists != null)
        {
            for (ProfileArtistContainer artist : prevArtists){
                addArtist(artist);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        PreferencesHandler.saveArtists(getActivity(), mArtistArray);
    }

    private void addArtist(ProfileArtistContainer newArtist)
    {
        mArtistArray.add(0, newArtist);
        mArtistAdapter.notifyItemInserted(0);
    }

    private Button.OnClickListener onAddArtistClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        return ret;
    }


}
