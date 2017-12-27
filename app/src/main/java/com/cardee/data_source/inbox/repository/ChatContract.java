package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ChatContract {

    void sendChatIdentifier(Integer serverId, Integer databaseId);

    Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId);

    Flowable<List<ChatMessage>> getMessages(String attachment);

    void addNewMessage(ChatMessage chatMessage);

    Completable sendMessage(String message);

    Completable markAsRead(int messageId);

}
