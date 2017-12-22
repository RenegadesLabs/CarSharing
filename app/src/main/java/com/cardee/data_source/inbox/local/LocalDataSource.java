package com.cardee.data_source.inbox.local;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataSource {

    Single<List<InboxChat>> getLocalChats(String attachment);

    Observable<InboxChat> subscribeToDb(String attachment);

    Single<Chat> getChat(Chat chat);

    void fetchUpdates(List<InboxChat> oldChatList, List<InboxChat> newChatList);

    void saveChats(List<InboxChat> inboxChats);

    void updateChats(List<InboxChat> chats);

    void addChat(Chat chat);

}
