package com.cardee.inbox.chat.list.adapter;

import android.support.v7.util.DiffUtil;

import com.cardee.data_source.inbox.local.chat.entity.Chat;

import java.util.List;

public class ChatDiffCallback extends DiffUtil.Callback {

    private List<Chat> oldChatList;
    private List<Chat> newChatList;

    public ChatDiffCallback(List<Chat> oldChatList, List<Chat> newChatList) {
        this.oldChatList = oldChatList;
        this.newChatList = newChatList;
    }

    @Override
    public int getOldListSize() {
        return oldChatList.size();
    }

    @Override
    public int getNewListSize() {
        return newChatList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldChatList.get(oldItemPosition).getChatId() == newChatList.get(newItemPosition).getChatId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldChatList.get(oldItemPosition).equals(newChatList.get(newItemPosition));
    }
}
