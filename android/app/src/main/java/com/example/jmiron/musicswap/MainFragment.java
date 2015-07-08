package com.example.jmiron.musicswap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private SharedPreferences profile;
    private SharedPreferences.Editor profileEditor;

    private ImageView song1Art;
    private ImageView song2Art;
    private ImageView song3Art;
    private ImageView artistArt;
    private TextView prevArtist;
    private TextView song1;
    private TextView song2;
    private TextView song3;

    private static String prevArtistName;
    public void changePrevArtist(String newPrev ){ prevArtistName = newPrev; }

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

        /* initialize class variables */
        song1 = (TextView) view.findViewById(R.id.topSong1);
        song2 = (TextView) view.findViewById(R.id.topSong2);
        song3 = (TextView) view.findViewById(R.id.topSong3);
        song1Art = (ImageView) view.findViewById(R.id.songArt1);
        song2Art = (ImageView) view.findViewById(R.id.songArt2);
        song3Art = (ImageView) view.findViewById(R.id.songArt3);
        artistArt = (ImageView) view.findViewById(R.id.lastAlbumArt);
        prevArtist = (TextView) view.findViewById(R.id.lastArtistName);

        /* initialize user preferences from local data */
        profile = getActivity().getSharedPreferences("UserInfo", 0);
        profileEditor = profile.edit();

        checkForPrevProfile();

        /* assign button on click listeners */
        Button chatBtn = (Button) view.findViewById(R.id.btnFindChat);
        chatBtn.setOnClickListener(onChatClick());

        Button suggestBtn = (Button) view.findViewById(R.id.btnSuggestArtist);
        suggestBtn.setOnClickListener(onSwapClick());


        /* Set album art */
        song1Art.setImageResource(R.drawable.albumartph40);
        song2Art.setImageResource(R.drawable.albumartph40);
        song3Art.setImageResource(R.drawable.albumartph40);
        artistArt.setImageResource(R.drawable.albumartph40);

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

    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String tempUsername = profile.getString("PrevArtist", "No Profile");
                MainActivity.username = tempUsername;
                final String prevsong1 = profile.getString("prevtopSong1", "Song 1");
                final String prevsong2 = profile.getString("prevtopSong2", "Song 2");
                final String prevsong3 = profile.getString("prevtopSong3", "Song 3");

                getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            prevArtist.setText(tempUsername);
                            song1.setText(prevsong1);
                            song2.setText(prevsong2);
                            song3.setText(prevsong3);
                        }
                    });
                }
        }).start();
    }

    private LinearLayout.OnClickListener onArtistClick(final TextView band){
        Button.OnClickListener ret = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getActivity() != null)
                {
//                    Intent intent = new Intent(getActivity(), ArtistProfileActivity.class);
//                    Bundle b = new Bundle();
//                    b.putString(band.getText().toString().trim(), "artist");
//                    intent.putExtras(b);
//                    startActivity(intent);
                }
            }
        };

        return ret;
    }

    private void checkForPrevProfile(){
        boolean firstStart = profile.getBoolean("first", true);

        if(firstStart){
            if(getActivity() != null){
                NewUserDialogFragment newUserDialog = new NewUserDialogFragment();
                newUserDialog.show(getActivity().getSupportFragmentManager(), "New User");
            }
        }
        else
        {
            loadData();
        }
    }

    private Button.OnClickListener onChatClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    if (MainActivity.mSocket.connected()) {
                        //TODO: Add username to joining chat
                        MainActivity.mSocket.emit("chat_user_join");
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        startActivity(intent);
                    } else {
                        NoConnectionDialogFragment noConnDialog = new NoConnectionDialogFragment();
                        noConnDialog.show(getActivity().getSupportFragmentManager(), "No Connection");
                    }
                }
            }
        };
        return ret;
    }

    private Button.OnClickListener onSwapClick(){
        Button.OnClickListener ret = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        return ret;
    }

}
