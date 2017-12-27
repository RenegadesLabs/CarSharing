package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.ChatItemLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.chat.ChatItemRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ChatRepository implements ChatContract {

    private final LocalData.ChatSingleSource mLocalSource;
    private final RemoteData.ChatSingleSource mRemoteSource;

    public ChatRepository() {
        mLocalSource = new ChatItemLocalSource();
        mRemoteSource = new ChatItemRemoteSource();
    }

    @Override
    public Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId) {
        return mLocalSource.getChatInfo(databaseId, serverId);
    }

    @Override
    public Flowable<List<ChatMessage>> getMessages() {
        return mLocalSource.getMessages();
    }

    @Override
    public void addNewMessage(ChatMessage chatMessage) {
        mLocalSource.addNewMessage(chatMessage);
    }
}
