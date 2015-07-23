package com.example.jmiron.musicswap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.data.MatchContainer;

import java.util.ArrayList;

/**
 * Created by jmiron on 7/10/2015.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private ArrayList<MatchContainer> mMatches;


    public MatchAdapter(Context context, ArrayList<MatchContainer> matchData) {
        mMatches = matchData;
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_layout, parent, false);
        return new MatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchAdapter.ViewHolder viewHolder, int position) {
        MatchContainer match = mMatches.get(position);
        viewHolder.setArtist(match.artist);
        viewHolder.setUsername(match.matchedName);
        viewHolder.setArtistArt(match.imageId);
    }

    @Override
    public int getItemCount() {
        return mMatches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView artist;
        private TextView username;
        private ImageView artistArt;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.match_username);
            artist = (TextView) itemView.findViewById(R.id.match_artist);
            artistArt = (ImageView) itemView.findViewById(R.id.match_art);
        }

        public void setArtist(String newArtist){ artist.setText(newArtist); }
        public void setUsername(String newName){ username.setText(newName); }
        public void setArtistArt(int imageResource){ artistArt.setImageResource(imageResource); }
    }
}
