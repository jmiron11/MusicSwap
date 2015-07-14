package com.example.jmiron.musicswap.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmiron on 7/10/2015.
 */
public class MatchContainer implements Parcelable{
    public String matchedName;
    public String artist;
    public int imageId;

    public MatchContainer() { }

    public MatchContainer(String newName, String newArtist)
    {
        matchedName = newName;
        artist = newArtist;
    }

    /* parcelable part */
    public MatchContainer(Parcel in)
    {
        matchedName = in.readString();
        artist = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matchedName);
        dest.writeString(artist);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MessageContainer> CREATOR = new Parcelable.Creator<MessageContainer>() {
        @Override
        public MessageContainer createFromParcel(Parcel in) {
            return new MessageContainer(in);
        }

        @Override
        public MessageContainer[] newArray(int size) {
            return new MessageContainer[size];
        }
    };
}
