package com.touchmenotapps.athena.main.views;

import android.content.Context;
import android.support.v4.widget.Space;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.main.dao.MessageDao;
import com.touchmenotapps.athena.main.dao.enums.ChatSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 22-11-2017.
 */

public class ChatListViewHolder extends RecyclerView.ViewHolder implements LinkPreviewCallback {

    @BindView(R.id.chat_message)
    AppCompatTextView chatMessage;
    @BindView(R.id.chat_message_system)
    Space messageSystem;
    @BindView(R.id.chat_message_user)
    Space messageUser;
    @BindView(R.id.url_preview_container)
    LinearLayout urlPreview;
    @BindView(R.id.url_preview_image)
    ImageView urlPreviewImage;
    @BindView(R.id.url_preview_title)
    AppCompatTextView urlPreviewTitle;
    @BindView(R.id.url_preview_details)
    AppCompatTextView urlPreviewDesc;
    @BindView(R.id.chat_url)
    AppCompatTextView chatURL;

    private TextCrawler textCrawler;
    private ChatSelectListener chatSelectListener;
    private MessageDao messageDao;
    private Context context;

    public ChatListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        textCrawler = new TextCrawler();
        this.context = itemView.getContext();
    }

    public void setSelectListener(ChatSelectListener chatSelectListener) {
        this.chatSelectListener = chatSelectListener;
    }

    public void setData(MessageDao messageDao) {
        this.messageDao = messageDao;
        if(messageDao.isUserInput()) {
            messageUser.setVisibility(View.VISIBLE);
        } else {
            messageSystem.setVisibility(View.VISIBLE);
        }
        chatMessage.setText(messageDao.getMessage());
        if(messageDao.getUrl() != null) {
            chatURL.setText(messageDao.getUrl());
            textCrawler.makePreview(this, messageDao.getUrl());
        }
    }

    @Override
    public void onPre() {
        chatURL.setVisibility(View.VISIBLE);
        urlPreview.setVisibility(View.GONE);
    }

    @Override
    public void onPos(SourceContent sourceContent, boolean b) {
        chatURL.setVisibility(View.GONE);
        urlPreview.setVisibility(View.VISIBLE);
        Glide.with(context).load(sourceContent.getImages().get(0)).into(urlPreviewImage);
        urlPreviewTitle.setText(sourceContent.getTitle());
        urlPreviewDesc.setText(sourceContent.getDescription());
    }
}