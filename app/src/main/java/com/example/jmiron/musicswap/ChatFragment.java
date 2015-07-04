package com.example.jmiron.musicswap;

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

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
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

    private Socket mSocket;
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    addMessage(message);
                }
            });
        }
    };

    private Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addMessage("A user has joined the room!");
                }
            });
        }
    };


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

        try {
            mSocket = IO.socket("http://10.0.2.2:8080");
        } catch (URISyntaxException e) {

        }
        mSocket.connect();

        mSocket.on("new_message", onNewMessage);
        mSocket.on("new_user", onNewUser);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(
                R.layout.fragment_chat,container, false);

        EditText message = (EditText) v.findViewById(R.id.chatMessage);

//        if(savedInstanceState != null){
//            message.setText(savedInstanceState.getCharSequence(
//                    "MessageText"));
//        }

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mInputText = (EditText) view.findViewById(R.id.chatMessage);
        mChatText = (ListView) view.findViewById(R.id.listView);
        mChatText.setAdapter(mMessageAdapter);

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.emit("user_left");
        mSocket.disconnect();
        mSocket.off("new_message", onNewMessage);
    }


    public void sendMessage()
    {
        String toSend = mInputText.getText().toString().trim();
        if(TextUtils.isEmpty(toSend)){
            mInputText.requestFocus();
            return;
        }

        mInputText.setText("");
        addMessage(toSend);
        mSocket.emit("new_message", toSend);
    }

    public void addMessage(String message)
    {
        mMessageArray.add(message);
    }

}
