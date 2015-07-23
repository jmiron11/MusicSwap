package com.example.jmiron.musicswap.handlers;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by jmiron on 7/9/2015.
 */
public class ServerHandler {

    public static String serverAddress = "http://musicswap-jmironapps.rhcloud.com/8000";
    public static Socket mSocket;

    public static void connectToServer(){
        try {
            mSocket = IO.socket(serverAddress); // initialize the io.socket websocket
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        if(mSocket.connected())
            mSocket.emit("new_connect");
    }

    public static void closeConnection()
    {
        try {
            mSocket.close();
            mSocket.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isConnected(){ return mSocket.connected(); }

    public static void saveProfile(String username, JSONArray artists) {
        if (mSocket.connected())
        {
            JSONObject new_profile = new JSONObject();
            try {
                new_profile.put("username", username);
                new_profile.put("artists", artists);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("update_profile", new_profile);
        }
        else
        {
            Log.e("NO_CONN", "socket was not connected on new_profile emit");
        }
    }

    public static void findMatch(String username, JSONArray artists)
    {
        if(mSocket.connected())
        {
            mSocket.emit("find_match", artists);
        }
        else
        {
            Log.e("NO_CONN", "socket was not connected on find_match emit");
        }
    }

    public static void updateMatches(String username)
    {
        if(mSocket.connected())
        {
            ServerHandler.mSocket.emit("request_matches", username);
        }
        else
        {
            Log.e("NO_CONN", "socket was not connected on request_matches emit");
        }
    }





}
