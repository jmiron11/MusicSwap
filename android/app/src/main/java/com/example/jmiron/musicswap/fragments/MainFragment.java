package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.jmiron.musicswap.adapters.MessageAdapter;
import com.example.jmiron.musicswap.data.MessageContainer;
import com.example.jmiron.musicswap.dialogs.NoConnectionDialogFragment;
import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.handlers.PreferencesHandler;
import com.example.jmiron.musicswap.handlers.ServerHandler;
import com.example.jmiron.musicswap.interfaces.ViewPagerFragmentInterface;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment {

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
            addInfo(new MessageContainer("Welcome", "THIS IS A MESSAGE", R.drawable.albumartph40, 0));
            PreferencesHandler.setFirstFalse(getActivity());
        }

        /* assign button on click listeners */
        Button chatBtn = (Button) view.findViewById(R.id.btnFindChat);
        chatBtn.setOnClickListener(onChatClick());

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

    private Button.OnClickListener onChatClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ServerHandler.isConnected()) {
                    NoConnectionDialogFragment noConnDialog = new NoConnectionDialogFragment();
                    noConnDialog.show(getActivity().getSupportFragmentManager(), "No Connection");
                }
//                if (getActivity() != null) {
//                    if (MainActivity.mSocket.connected()) {
//                        //TODO: Add username to joining chat
//                        MainActivity.mSocket.emit("chat_user_join");
//                        Intent intent = new Intent(getActivity(), ChatActivity.class);
//                        startActivity(intent);
//                    } else {
//                        NoConnectionDialogFragment noConnDialog = new NoConnectionDialogFragment();
//                        noConnDialog.show(getActivity().getSupportFragmentManager(), "No Connection");
//                    }
//                }
            }
        };
        return ret;
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
                final String artist1 = PreferencesHandler.getArtist1(getActivity());
                final String artist2 = PreferencesHandler.getArtist2(getActivity());
                final String artist3 = PreferencesHandler.getArtist3(getActivity());
                ServerHandler.findMatch(username, artist1, artist2, artist3);
            }
        };
        return ret;
    }
}
