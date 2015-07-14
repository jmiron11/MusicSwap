package com.example.jmiron.musicswap.data;

import android.graphics.Bitmap;

/**
 * Created by jmiron on 7/14/2015.
 */
public class ProfileArtistContainer {
    public String artist;
    public String artName;

    public ProfileArtistContainer(){ }

    public ProfileArtistContainer(String newName)
    {
        artist = newName;
        artName = null;
    }

}
