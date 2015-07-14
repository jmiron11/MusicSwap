package com.example.jmiron.musicswap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProfileArtistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProfileArtistsFragment extends Fragment implements ViewPagerFragmentInterface {

    private ImageView artistArt1, artistArt2, artistArt3;
    private EditText artistName1, artistName2, artistName3;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewProfileArtistsFragment.
     */
    public static NewProfileArtistsFragment newInstance() {
        NewProfileArtistsFragment fragment = new NewProfileArtistsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewProfileArtistsFragment() {
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
        return inflater.inflate(R.layout.fragment_new_profile_artists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        artistName1 = (EditText) view.findViewById(R.id.artist1Field);
        artistName2 = (EditText) view.findViewById(R.id.artist2Field);
        artistName3 = (EditText) view.findViewById(R.id.artist3Field);

        //TODO: Variable Length Artists
        ArrayList<ProfileArtistContainer> artists = PreferencesHandler.getArtists(getActivity());
        if(artists != null)
        {
            Iterator<ProfileArtistContainer> it = artists.iterator();
            if(it.hasNext())
                artistName1.setText(it.next().artist);
            if(it.hasNext())
                artistName2.setText(it.next().artist);
            if(it.hasNext())
                artistName3.setText(it.next().artist);
        }
    }

    @Override
    public void fragmentBecameVisible() {
    }

    @Override
    public void fragmentScrolled(){
        String artist1 =  artistName1.getText().toString().trim();
        String artist2 =  artistName2.getText().toString().trim();
        String artist3 =  artistName3.getText().toString().trim();
        PreferencesHandler.saveUserData(getActivity(), artist1, artist2, artist3);
    }
}

