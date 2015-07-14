package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.MatchAdapter;
import com.example.jmiron.musicswap.adapters.MessageAdapter;
import com.example.jmiron.musicswap.data.MatchContainer;
import com.example.jmiron.musicswap.data.MessageContainer;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MatchFragment extends Fragment {

    private ArrayList<MatchContainer> mMatchArray = new ArrayList<>();
    private MatchAdapter mMatchAdapter;
    private RecyclerView mMatchView;
    private SwipeRefreshLayout swipeContainer;

    public static MatchFragment newInstance(String param1, String param2) {
        MatchFragment fragment = new MatchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServerHandler.mSocket.on("match_return", matchesReturned);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_match, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.match_refresh_container);

        //TODO: fix request matches
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String username = PreferencesHandler.getUsername(getActivity());
                ServerHandler.updateMatches(username);
            }
        });

        // Inflate the layout for this fragment
        mMatchView = (RecyclerView) v.findViewById(R.id.matchList);
        mMatchView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchView.setAdapter(mMatchAdapter); // assign the adapter to the infoview
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMatchAdapter = new MatchAdapter(getActivity(), mMatchArray);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ServerHandler.mSocket.off("match_return", matchesReturned);
    }

    private Emitter.Listener matchesReturned = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    mMatchArray.clear();
                    mMatchAdapter.notifyDataSetChanged();
                    JSONObject received = (JSONObject) args[0];
                    try {
                        JSONArray matchArray = received.getJSONArray("profiles");
                        for (int i = 0; i < matchArray.length(); ++i) {
                            JSONObject singleMatch = matchArray.getJSONObject(i);
                            MatchContainer matchToAdd = new MatchContainer();
                            matchToAdd.artist = singleMatch.getString("artist");
                            matchToAdd.matchedName = singleMatch.getString("username");
                            matchToAdd.imageId = R.drawable.albumartph40; //TODO: Set to actual album art
                            addMatch(matchToAdd);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    swipeContainer.setRefreshing(false);
                }
            });
        }
    };

    private void addMatch(MatchContainer newMatch)
    {
        mMatchArray.add(0, newMatch);
        mMatchAdapter.notifyItemInserted(0);
    }
}
