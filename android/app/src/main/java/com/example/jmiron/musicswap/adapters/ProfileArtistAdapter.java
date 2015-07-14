package com.example.jmiron.musicswap.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;

import java.util.ArrayList;

/**
 * Created by jmiron on 7/14/2015.
 */
public class ProfileArtistAdapter extends RecyclerView.Adapter<ProfileArtistAdapter.ViewHolder> {

    private ArrayList<ProfileArtistContainer> mArtists;

    public ProfileArtistAdapter(Context context, ArrayList<ProfileArtistContainer> artistData) {
        mArtists = artistData;
    }

    //TODO: Set this adapter to grid view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_layout, parent, false);
        return new ProfileArtistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProfileArtistContainer artistProfile = mArtists.get(position);
        holder.setArtistName(artistProfile.artist);

        if(artistProfile.artName != null)
            holder.setDefaultArt(); // TODO: Set to proper art
        else holder.setDefaultArt();
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //TODO: Insert in artist profile information

        private TextView artistName;
        private ImageView artistArt;

        public ViewHolder(View itemView) {
            super(itemView);
            artistName = (TextView) itemView.findViewById(R.id.profile_artist_name);
            artistArt = (ImageView) itemView.findViewById(R.id.profile_artist_art);
        }

        public void setArtistName(String newName) { artistName.setText(newName); }
        public void setArtistArt(Bitmap ArtistArt) { artistArt.setImageBitmap(ArtistArt); }
        public void setDefaultArt(){ artistArt.setImageResource(R.drawable.albumartph80); }

    }
}
