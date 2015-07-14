package com.example.jmiron.musicswap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;
import com.example.jmiron.musicswap.data.MessageContainer;

import java.util.ArrayList;

/**
 * Created by jmiron on 7/9/2015.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<MessageContainer> mMessages;

    public MessageAdapter(Context context, ArrayList<MessageContainer> infoData) {
        mInflater = LayoutInflater.from(context);
        mMessages = infoData;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_layout, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MessageContainer message = mMessages.get(position);
        viewHolder.setName(message.name);
        viewHolder.setDetails(message.details);
        viewHolder.setArt(message.imageId);
    }

    @Override
    public int getItemCount()
    {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView infoName;
        private TextView details;
        private ImageView infoArt;

        public ViewHolder(View itemView)
        {
            super(itemView);
            infoName = (TextView) itemView.findViewById(R.id.infoName);
            details = (TextView) itemView.findViewById(R.id.infoDetail);
            infoArt = (ImageView) itemView.findViewById(R.id.infoArt);
        }

        public void setName(String newName){ infoName.setText(newName); }
        public void setDetails(String newDetails){ details.setText(newDetails); }
        public void setArt(int imageResource){ infoArt.setImageResource(imageResource); }
    }
}
