package com.example.jmiron.musicswap.handlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by jmiron on 7/23/2015.
 */
public class BitmapStorageHandler {
    public static File getSavePath(){
        File path = Environment.getDataDirectory();
        return path;
    }

    public static Bitmap loadFromFile(String filename, Context context){
        try {
            File f = context.getFileStreamPath(filename);
            if (!f.exists() || f == null) { return null; }
            FileInputStream in = context.openFileInput(filename);
            Bitmap tmp = BitmapFactory.decodeStream(in);
            return tmp;
        } catch (Exception e){
            return null;
        }
    }

    public static void saveToFile(String filename,Bitmap bmp, Context context) {
        try {
            FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch(Exception e) {}
    }



}
