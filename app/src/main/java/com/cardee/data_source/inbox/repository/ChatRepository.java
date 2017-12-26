package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.ChatItemDataSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import io.reactivex.Single;

public class ChatRepository implements ChatContract {

    private final LocalData.ChatSingleSource mLocalData;

    public ChatRepository() {
        mLocalData = new ChatItemDataSource();
    }

    @Override
    public Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId) {
        return mLocalData.getChatInfo(databaseId, serverId);
    }
}
