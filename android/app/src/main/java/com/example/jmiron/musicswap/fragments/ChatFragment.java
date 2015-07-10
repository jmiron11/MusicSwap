package com.example.jmiron.musicswap.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jmiron.musicswap.adapters.ChatMessageAdapter;
import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.activities.MainActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SavedTextView = "Default1";

    private EditText mInputText;
    private ListView mChatText;
    private ArrayList<String> mMessageArray = new ArrayList<String>();
    private ChatMessageAdapter mMessageAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment() {
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
        View v = inflater.inflate(
                R.layout.fragment_chat,container, false);

        /* restore the previous chat data */
        EditText msgToSend = (EditText) v.findViewById(R.id.chatMessage);
        if(savedInstanceState != null){
            msgToSend.setText(savedInstanceState.getCharSequence(
                    "MessageText"));
            /* copy old text data to the new emptied message array */
            ArrayList<String> prevData = savedInstanceState.getStringArrayList("ListData");
            for (String chatMsg : prevData){
                mMessageArray.add(chatMsg);
            }
        }

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        /* attach class gui variables */
        mInputText = (EditText) view.findViewById(R.id.chatMessage);
        mChatText = (ListView) view.findViewById(R.id.listView);
        mChatText.setAdapter(mMessageAdapter); // assign the adapter to the

        Button sendBtn = (Button) view.findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMessageAdapter = new ChatMessageAdapter(getActivity(), mMessageArray);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View v=getView();
        EditText message = (EditText) v.findViewById(R.id.chatMessage);
        outState.putCharSequence("MessageText", message.getText());
        outState.putStringArrayList("ListData", mMessageArray);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //TODO: Add username to leaving chat
    }


    public void sendMessage()
    {
        String toSend = mInputText.getText().toString().trim();
        if(TextUtils.isEmpty(toSend)){
            mInputText.requestFocus();
            return;
        }

        //TODO: Add username along with message
        mInputText.setText("");
        addMessage(toSend);
    }

    public void addMessage(String message)
    {
        mMessageArray.add(message);
    }

}
