package com.cardee.inbox.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    private List<InboxChat> mInboxChats;

    ChatAdapter() {
        mInboxChats = new ArrayList<>();
    }

    void addItems(List<InboxChat> list) {
        mInboxChats.addAll(list);
        notifyDataSetChanged();
    }

    void updateItem(InboxChat newChat) {
        int position = mInboxChats.indexOf(newChat);
        if (isChatExist(position)) {
            mInboxChats.add(position, newChat);
            notifyItemChanged(position);
        } else {
            mInboxChats.add(0, newChat);
            notifyDataSetChanged();
        }
    }

    private boolean isChatExist(int position) {
        return position != -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mInboxChats.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        public UserViewHolder(View itemView) {
            super(itemView);
        }
    }

    class RecipientViewHolder extends RecyclerView.ViewHolder {

        public RecipientViewHolder(View itemView) {
            super(itemView);
        }
    }

}
