package com.example.jmiron.musicswap.adapters;

import android.content.Context;
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
public class MessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<MessageContainer> mInfo;

    public MessageAdapter(Context context, ArrayList<MessageContainer> infoData) {
        mInflater = LayoutInflater.from(context);
        mInfo = infoData;
    }

    @Override
    public int getCount() {
        return mInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) { // Only inflating if necessary is great for performance
            convertView = mInflater.inflate(R.layout.info_layout, parent, false);
            holder = new ViewHolder();
            holder.infoName = (TextView) convertView.findViewById(R.id.infoName);
            holder.details = (TextView) convertView.findViewById(R.id.infoDetail);
            holder.infoArt = (ImageView) convertView.findViewById(R.id.infoArt);
            convertView.setTag(holder);
        }
        else
        {
            holder  = (ViewHolder)convertView.getTag();
        }

        MessageContainer infoView = mInfo.get(position);
        holder.infoName.setText(infoView.name);
        holder.details.setText(infoView.details);
        holder.infoArt.setImageResource(infoView.imageId);

        return convertView;
    }

    private class ViewHolder {
        public TextView infoName;
        public TextView details;
        public ImageView infoArt;
    }
}
