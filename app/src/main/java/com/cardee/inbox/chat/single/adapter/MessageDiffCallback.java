package com.cardee.inbox.chat.single.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;

import java.util.List;

public class MessageDiffCallback extends DiffUtil.Callback {

    private List<ChatMessage> oldList;
    private List<ChatMessage> newList;

    MessageDiffCallback(List<ChatMessage> oldList, List<ChatMessage> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getMessageId().intValue() == newList.get(newItemPosition).getMessageId().intValue();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
