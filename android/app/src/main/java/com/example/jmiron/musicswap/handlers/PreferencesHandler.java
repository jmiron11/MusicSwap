package com.example.jmiron.musicswap.handlers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.jmiron.musicswap.data.MessageContainer;
import com.example.jmiron.musicswap.data.ProfileArtistContainer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jmiron on 7/9/2015.
 */
public class PreferencesHandler {
    public static String profileName = "UserInfo";
    public static void saveUserData(Activity callActivity, String username, String artist1, String artist2, String artist3)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putString("username", username);
        profileEditor.putBoolean("prevProfile", true);

        ArrayList<ProfileArtistContainer> toStore = new ArrayList<>();
        toStore.add(new ProfileArtistContainer(artist1));
        toStore.add(new ProfileArtistContainer(artist2));
        toStore.add(new ProfileArtistContainer(artist3));
        String mainInfoJSONString = new Gson().toJson(toStore);
        profileEditor.putString("artists", mainInfoJSONString);
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
        ArrayList<ProfileArtistContainer> toStore = new ArrayList<>();
        toStore.add(new ProfileArtistContainer(artist1));
        toStore.add(new ProfileArtistContainer(artist2));
        toStore.add(new ProfileArtistContainer(artist3));
        String mainInfoJSONString = new Gson().toJson(toStore);
        profileEditor.putString("artists", mainInfoJSONString);
        profileEditor.commit();
    }

    public static void setFirstFalse(Activity callActivity)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        profileEditor.putBoolean("first", false);
        profileEditor.commit();
    }

    public static void saveArtists(Activity callActivity, ArrayList<ProfileArtistContainer> artists)
    {
        SharedPreferences.Editor profileEditor = callActivity.getSharedPreferences(profileName, 0).edit();
        String mainInfoJSONString = new Gson().toJson(artists);
        profileEditor.putString("artists", mainInfoJSONString);
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

    public static ArrayList<ProfileArtistContainer> getArtists(Activity callActivity)
    {
        String mainInfoJSONString = callActivity.getSharedPreferences(profileName, 0).getString("artists", null);
        if(mainInfoJSONString == null)
            return null;
        else
        {
            Type type = new TypeToken< ArrayList <ProfileArtistContainer> >() {}.getType();
            ArrayList<ProfileArtistContainer> storedData = new Gson().fromJson(mainInfoJSONString, type);
            return storedData;
        }
    }


}
