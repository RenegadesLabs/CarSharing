package com.cardee.data_source.inbox.local;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataSource {

    Flowable<List<Chat>> getLocalChats(String attachment);

    Flowable<Chat> subscribeToDb(String attachment);

    Single<Chat> getChat(Chat chat);

    void fetchUpdates(List<Chat> oldChatList, List<Chat> newChatList);

    void saveChats(List<Chat> inboxChats);

    void updateChats(List<Chat> chats);

    void addChat(Chat chat);

}
