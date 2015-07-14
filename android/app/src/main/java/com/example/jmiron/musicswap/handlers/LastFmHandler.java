package com.example.jmiron.musicswap.handlers;

import android.os.AsyncTask;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.ImageSize;

/**
 * Created by jmiron on 7/14/2015.
 */
public class LastFmHandler {
    public static String key = "9413bced5c4158f822c692ed529e50c4";

    public static void init(){
        Caller.getInstance().setCache(null);
        Caller.getInstance().setUserAgent("tst");
    }

    public static String getArtistArtUrl(String artistName){
        try
        {
            return new getArtistUrlTask().execute(artistName).get();
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static class getArtistUrlTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String artistName = params[0];
            Artist artist = Artist.getInfo(artistName, key);
            return artist.getImageURL(ImageSize.valueOf("LARGE"));
        }
    }
}
