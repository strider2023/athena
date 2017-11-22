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

    private ChatSelectListener chatSelectListener;
    private List<MessageDao> messageDaos = new ArrayList<>();

    public ChatListAdapter(ChatSelectListener chatSelectListener) {
        this.chatSelectListener = chatSelectListener;
    }

    public void setData(List<MessageDao> messageDaos) {
        this.messageDaos = messageDaos;
        notifyDataSetChanged();
    }

    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chat,
                parent, false);
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
