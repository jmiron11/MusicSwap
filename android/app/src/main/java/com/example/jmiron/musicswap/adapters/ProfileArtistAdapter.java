package com.example.jmiron.musicswap.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;
import com.example.jmiron.musicswap.handlers.BitmapStorageHandler;
import com.example.jmiron.musicswap.handlers.LastFmHandler;
import com.example.jmiron.musicswap.handlers.UrlImageHandler;

import java.util.ArrayList;

/**
 * Created by jmiron on 7/14/2015.
 */
public class ProfileArtistAdapter extends RecyclerView.Adapter<ProfileArtistAdapter.ViewHolder> {

    private ArrayList<ProfileArtistContainer> mArtists;
    Context mContext;

    public ProfileArtistAdapter(Context context, ArrayList<ProfileArtistContainer> artistData) {
        mArtists = artistData;
        mContext = context;
    }

    //TODO: Set this adapter to grid view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_artist_layout, parent, false);
        return new ProfileArtistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProfileArtistContainer artistProfile = mArtists.get(position);
        holder.setArtistName(artistProfile.artist);
        holder.setArtistArt(artistProfile.artist);
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
        public void setArtistArt(String artistName) {
            /* if it doesnt't exist */
            Bitmap art = BitmapStorageHandler.loadFromFile(artistName + ".png", mContext);
            if(art != null) {
                artistArt.setImageBitmap(art);
            }
            else
            {
                art = UrlImageHandler.getUrlBitmap(LastFmHandler.getArtistArtUrl(artistName));
                if(art == null)
                {
                    setDefaultArt();
                    return;
                }
                else
                {
                    BitmapStorageHandler.saveToFile(artistName + ".png", art, mContext);
                    artistArt.setImageBitmap(art);
                }
            }
        }

        public void setDefaultArt(){ artistArt.setImageResource(R.drawable.albumartph80); }
    }
}
