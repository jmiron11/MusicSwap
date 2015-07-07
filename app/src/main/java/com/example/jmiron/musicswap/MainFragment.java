package com.example.jmiron.musicswap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    ViewPager mViewPager;

    SharedPreferences profile;
    SharedPreferences.Editor profileEditor;

    private TextView username;
    private TextView band1;
    private TextView band2;
    private TextView band3;

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

        /* initialize class varialbes */
        username = (TextView) view.findViewById(R.id.UpperUsername);
        band1 = (TextView) view.findViewById(R.id.mainBand1);
        band2 = (TextView) view.findViewById(R.id.mainBand2);
        band3 = (TextView) view.findViewById(R.id.mainBand3);

        profile = getActivity().getSharedPreferences("UserInfo", 0);
        profileEditor = profile.edit();

        boolean firstStart = profile.getBoolean("first", true);

        if(firstStart){
            if(getActivity() != null){
                NewUserDialogFragment newUserDialog = new NewUserDialogFragment();
                newUserDialog.show(getFragmentManager(), "New User");
            }
        }
        else
        {
            loadData();
        }

        Button chatBtn = (Button) view.findViewById(R.id.btnFindChat);
        Button suggestBtn = (Button) view.findViewById(R.id.btnSuggestArtist);
        Button editProfileBtn = (Button) view.findViewById(R.id.btnEdit);

        chatBtn.setOnClickListener(new View.OnClickListener() {
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
                        noConnDialog.show(getFragmentManager(), "No Connection");
                    }
                }
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(getActivity() != null)
                {
                    Intent intent = new Intent(getActivity(), ProfileBuilderActivity.class);
                    startActivity(intent);
                }
            }
        });

        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /* Set album art */
        ImageView albumArt1 = (ImageView) view.findViewById(R.id.albumArt1);
        ImageView albumArt2 = (ImageView) view.findViewById(R.id.albumArt2);
        ImageView albumArt3 = (ImageView) view.findViewById(R.id.albumArt3);
        albumArt1.setImageResource(R.drawable.albumartph40);
        albumArt2.setImageResource(R.drawable.albumartph40);
        albumArt3.setImageResource(R.drawable.albumartph40);

        /* Set clickable linear layouts */
        LinearLayout band1row = (LinearLayout) view.findViewById(R.id.band1row);
        LinearLayout band2row = (LinearLayout) view.findViewById(R.id.band2row);
        LinearLayout band3row = (LinearLayout) view.findViewById(R.id.band3row);
        band1row.setOnClickListener(onArtistClick(band1));
        band2row.setOnClickListener(onArtistClick(band2));
        band3row.setOnClickListener(onArtistClick(band3));

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
                final String tempUsername = profile.getString("username", "No Profile");
                final String tempBand1 = profile.getString("band1", "Band 1");
                final String tempBand2 = profile.getString("band2", "Band 2");
                final String tempBand3 = profile.getString("band3", "Band 3");

                getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            username.setText(tempUsername);
                            band1.setText(tempBand1);
                            band2.setText(tempBand2);
                            band3.setText(tempBand3);
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
}
