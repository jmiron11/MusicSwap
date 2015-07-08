package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreviousMatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreviousMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousMatchFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String username;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviousMatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviousMatchFragment newInstance(String param1, String param2) {
        PreviousMatchFragment fragment = new PreviousMatchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PreviousMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = MainActivity.username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_match, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
