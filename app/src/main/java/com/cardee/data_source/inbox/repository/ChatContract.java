package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ChatContract {

    void sendChatIdentifier(Integer serverId, Integer databaseId, String attachment);

    Single<ChatInfo> getChatInfo();

    Flowable<List<ChatMessage>> getLocalMessages();

    Single<List<ChatMessage>> getRemoteMessages();

    Single<List<ChatMessage>> getNewChat();

    void addNewMessage(ChatMessage chatMessage);

    Completable sendMessage(String message);

    Completable markAsRead(int messageId);

}
