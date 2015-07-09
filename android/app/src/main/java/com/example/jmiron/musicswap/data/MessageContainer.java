package com.example.jmiron.musicswap.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmiron on 7/9/2015.
 */
public class MessageContainer implements Parcelable {
    public String name;
    public String details;
    public int imageId;
    public int type;

    public MessageContainer(String newName, String newDetails, int newImageId, int newType)
    {
        name = newName;
        details = newDetails;
        imageId = newImageId;
        type = newType;
    }

    /* parcelable part */
    public MessageContainer(Parcel in)
    {
        name = in.readString();
        details = in.readString();
        imageId = in.readInt();
        type = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(details);
        dest.writeInt(imageId);
        dest.writeInt(type);
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
