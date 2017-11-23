package com.touchmenotapps.athena.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.main.dao.MessageDao;
import com.touchmenotapps.athena.main.dao.enums.ChatSelectListener;
import com.touchmenotapps.athena.main.views.ChatListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i7 on 22-11-2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    //Tag for tracking self message
    private int SELF = 786;

    private ChatSelectListener chatSelectListener;
    private List<MessageDao> messageDaos = new ArrayList<>();

    public ChatListAdapter(ChatSelectListener chatSelectListener) {
        this.chatSelectListener = chatSelectListener;
    }

    public void setData(MessageDao message) {
        this.messageDaos.add(message);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.messageDaos.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //getting message object of current position
        MessageDao message = messageDaos.get(position);
        //If its owner  id is  equals to the logged in user id
        if (message.isUserInput()) {
            //Returning self
            return SELF;
        }
        //else returning position
        return position;
    }

    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF) {
            //Inflating the layout self
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_chat_user, parent, false);
        } else {
            //else inflating the layout others
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_chat_athena, parent, false);
        }
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListViewHolder holder, int position) {
        holder.setSelectListener(chatSelectListener);
        holder.setData(messageDaos.get(position));
    }

    @Override
    public int getItemCount() {
        return messageDaos.size();
    }
}
