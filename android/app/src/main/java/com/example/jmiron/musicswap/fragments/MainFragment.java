package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.adapters.MessageAdapter;
import com.example.jmiron.musicswap.adapters.ProfileArtistAdapter;
import com.example.jmiron.musicswap.data.MessageContainer;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;
import com.example.jmiron.musicswap.dialogs.NewUserDialogFragment;
import com.example.jmiron.musicswap.dialogs.NoConnectionDialogFragment;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment implements ViewPagerFragmentInterface {

    private ArrayList<MessageContainer> mMessageArray = new ArrayList<>();
    private MessageAdapter mMessageAdapter;
    private RecyclerView mMessageView;

    private Emitter.Listener matchFound = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MessageContainer newMatch = new MessageContainer();
                    JSONObject received = (JSONObject) args[0];
                    try {
                        newMatch.name = received.getString("username");
                        newMatch.details = received.getString("artist");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    newMatch.imageId = R.drawable.albumartph40; //TODO: Set it based on args[1], the artist
                    newMatch.type = 1;
                    addInfo(newMatch);
                }
            });
        }
    };

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
        ServerHandler.mSocket.on("match_found", matchFound);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_main,container, false);
        /* restoring the info view */
        if(savedInstanceState != null){
            /* copy old info array data to the new emptied info array */
            ArrayList<MessageContainer> prevData = savedInstanceState.getParcelableArrayList("info");
            mMessageArray.clear();
            for (MessageContainer data : prevData){
                addInfo(data);
            }
        }

        mMessageView = (RecyclerView) v.findViewById(R.id.mainList);
        mMessageView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageView.setAdapter(mMessageAdapter); // assign the adapter to the infoview

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        if(PreferencesHandler.getFirst(getActivity()))
        {
            addInfo(new MessageContainer("Welcome", "THIS IS A MESSAGE", R.drawable.squirrel, 0));

            NewUserDialogFragment newUsernameDialog = new NewUserDialogFragment();
            newUsernameDialog.show(getActivity().getSupportFragmentManager(), "New Username");

            PreferencesHandler.setFirstFalse(getActivity());
        }

        Button swapBtn = (Button) view.findViewById(R.id.btnSuggestArtist);
        swapBtn.setOnClickListener(onSwapClick());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMessageAdapter = new MessageAdapter(getActivity(), mMessageArray);

        ArrayList<MessageContainer> prevMessages = PreferencesHandler.getMessages(getActivity());
        if(prevMessages != null)
        {
            for (MessageContainer message : prevMessages){
                addInfo(message);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("info", mMessageArray);
        PreferencesHandler.storeMessages(getActivity(), mMessageArray);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        PreferencesHandler.storeMessages(getActivity(), mMessageArray);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void onDestroy(){
        super.onDestroy();
        ServerHandler.mSocket.off("match_found", matchFound);
    }

    private void addInfo(MessageContainer newInfo)
    {
        mMessageArray.add(0, newInfo);
        mMessageAdapter.notifyItemInserted(0);
    }

    private Button.OnClickListener onSwapClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ServerHandler.isConnected()) {
                    NoConnectionDialogFragment noConnDialog = new NoConnectionDialogFragment();
                    noConnDialog.show(getActivity().getSupportFragmentManager(), "No Connection");
                }

                final String username = PreferencesHandler.getUsername(getActivity());
                ArrayList<ProfileArtistContainer> artists = PreferencesHandler.getArtists(getActivity());
                ArrayList<String> artistsName = new ArrayList<>();
                for(ProfileArtistContainer artist : artists){
                    artistsName.add(artist.artist);
                }
                JSONArray toSend = new JSONArray(artistsName);
                ServerHandler.findMatch(username, toSend);
            }
        };
        return ret;
    }

    @Override
    public void fragmentSelected() { }
    public void fragmentScrolled(){ }
}
