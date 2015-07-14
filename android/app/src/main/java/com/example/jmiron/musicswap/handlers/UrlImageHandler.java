package com.example.jmiron.musicswap.handlers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by jmiron on 7/14/2015.
 */
public class UrlImageHandler {

    public static Bitmap getUrlBitmap(String url){
        try
        {
            return new DownloadImageTask().execute(url).get();
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String imgUrl = urls[0];
            Bitmap imgBitmap = null;
            try {
                InputStream in = new java.net.URL(imgUrl).openStream();
                imgBitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return imgBitmap;
        }
    }

}
