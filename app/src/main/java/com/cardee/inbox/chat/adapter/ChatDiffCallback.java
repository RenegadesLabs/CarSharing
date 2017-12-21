package com.cardee.inbox.chat.adapter;

import android.support.v7.util.DiffUtil;

import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

public class ChatDiffCallback extends DiffUtil.Callback {

    private List<InboxChat> oldChatList;
    private List<InboxChat> newChatList;

    public ChatDiffCallback(List<InboxChat> oldChatList, List<InboxChat> newChatList) {
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
