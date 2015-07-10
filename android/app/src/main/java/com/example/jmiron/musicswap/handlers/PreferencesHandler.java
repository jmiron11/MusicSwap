package com.example.jmiron.musicswap.handlers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.jmiron.musicswap.data.MessageContainer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jmiron on 7/9/2015.
 */
public class PreferencesHandler {
    public static String profileName = "UserInfo";
    public static void saveUserData(Activity callActivity, String username, String artist1, String artist2, String artist3)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putString("username", username);
        profileEditor.putString("artist1", artist1);
        profileEditor.putString("artist2", artist2);
        profileEditor.putString("artist3", artist3);
        profileEditor.putBoolean("prevProfile", true);
        profileEditor.commit();

    }

    public static void saveUserData(Activity callActivity, String username)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putString("username", username);
        profileEditor.commit();
    }

    public static void saveUserData(Activity callActivity, String artist1, String artist2, String artist3)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putString("artist1", artist1);
        profileEditor.putString("artist2", artist2);
        profileEditor.putString("artist3", artist3);
        profileEditor.commit();
    }

    public static void setFirstFalse(Activity callActivity)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putBoolean("first", false);
        profileEditor.commit();
    }

    public static void storeMessages(Activity callActivity, ArrayList<MessageContainer> myMessages)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        String mainInfoJSONString = new Gson().toJson(myMessages);
        profileEditor.putString("main_info", mainInfoJSONString);
        profileEditor.commit();
    }

    public static String getUsername(Activity callActivity){ return callActivity.getSharedPreferences(profileName, 0).getString("username", null); }
    public static String getArtist1(Activity callActivity) { return callActivity.getSharedPreferences(profileName, 0).getString("artist1", null); }
    public static String getArtist2(Activity callActivity) { return callActivity.getSharedPreferences(profileName, 0).getString("artist2", null); }
    public static String getArtist3(Activity callActivity) { return callActivity.getSharedPreferences(profileName, 0).getString("artist3", null); }
    public static boolean getFirst(Activity callActivity) { return callActivity.getSharedPreferences(profileName, 0).getBoolean("first", true); }
    public static ArrayList<MessageContainer> getMessages(Activity callActivity)
    {
        String mainInfoJSONString = callActivity.getSharedPreferences(profileName, 0).getString("main_info", null);
        if(mainInfoJSONString == null)
            return null;
        else
        {
            Type type = new TypeToken< ArrayList <MessageContainer> >() {}.getType();
            ArrayList<MessageContainer> storedData = new Gson().fromJson(mainInfoJSONString, type);
            return storedData;
        }
    }


}
