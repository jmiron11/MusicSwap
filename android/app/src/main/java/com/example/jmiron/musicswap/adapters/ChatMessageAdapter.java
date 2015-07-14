package com.example.jmiron.musicswap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jmiron.musicswap.R;

import java.util.ArrayList;

/**
 * Created by jmiron on 7/2/2015.
 */
public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<String> mChatValue;

    public ChatMessageAdapter(Context context, ArrayList<String> chatValue) {
        mInflater = LayoutInflater.from(context);
        mChatValue = chatValue;
    }

    @Override
    public int getCount() {
        return mChatValue.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) { // Only inflating if necessary is great for performance
            convertView = mInflater.inflate(R.layout.msg_layout, parent, false);
            holder = new ViewHolder();
            holder.latestMessage = (TextView) convertView.findViewById(R.id.textViewItem);
            convertView.setTag(holder);
        }
        else
        {
            holder  = (ViewHolder)convertView.getTag();
        }

        String message = mChatValue.get(position);
        holder.latestMessage.setText(message);

        return convertView;
    }

    private class ViewHolder {
        public TextView latestMessage;
    }
}
