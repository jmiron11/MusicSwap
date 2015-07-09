package com.example.jmiron.musicswap.handlers;

import android.util.Log;

import com.example.jmiron.musicswap.activities.MainActivity;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by jmiron on 7/9/2015.
 */
public class ServerHandler {

    public static String serverAddress = "http://10.0.2.2:8080";
    public static Socket mSocket;

    public static void connectToServer(){
        try {
            mSocket = IO.socket(serverAddress); // initialize the io.socket websocket
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
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

    public static void saveProfile(String username, String artist1, String artist2, String artist3)
    {
        JSONObject new_profile = new JSONObject();
        try {
            new_profile.put("username", username);
            new_profile.put("artist1", artist1);
            new_profile.put("artist2", artist2);
            new_profile.put("artist3", artist3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("new_profile", new_profile);
    }

    public static void findMatch(String username, String artist1, String artist2, String artist3)
    {
        if(mSocket.connected())
            {
                JSONObject new_profile = new JSONObject();
                try {
                    new_profile.put("username", username);
                    new_profile.put("artist1", artist1);
                    new_profile.put("artist2", artist2);
                    new_profile.put("artist3", artist3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("find_match", new_profile);
            }
    }





}
