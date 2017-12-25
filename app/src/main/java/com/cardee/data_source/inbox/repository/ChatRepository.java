package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.ChatItemDataSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class ChatRepository implements ChatRepositoryContract {

    private final LocalData.ChatSingleSource mLocalData;

    public ChatRepository() {
        mLocalData = new ChatItemDataSource();
    }

    @Override
    public Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId) {
        return mLocalData.getChatInfo(databaseId, serverId);
    }
}
