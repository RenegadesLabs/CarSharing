package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ChatContract {

    Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId);

    Flowable<List<ChatMessage>> getMessages();

    void addNewMessage(ChatMessage chatMessage);

}
