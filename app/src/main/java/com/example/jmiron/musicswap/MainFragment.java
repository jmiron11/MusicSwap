package com.example.jmiron.musicswap;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SavedTextView = "Default1";
    private static final String SavedButtonText = "Default2";

    private EditText mInputText;
    private ListView mChatText;
    private ArrayList<String> mMessageArray = new ArrayList<String>();
    private ChatMessageAdapter mMessageAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(SavedTextView, param1);
        args.putString(SavedButtonText, param2);
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(
                R.layout.fragment_main,container, false);

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
//        View v=getView();
//        EditText message = (EditText) v.findViewById(R.id.chatMessage);
//        outState.putCharSequence("MessageText", message.getText());
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
    }

    public void addMessage(String message)
    {
        mMessageArray.add(message);
    }

}
