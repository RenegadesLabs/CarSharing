package com.cardee.data_source.inbox.local;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataSource {

    Observable<List<InboxChat>> getLocalChats(String attachment);

    Completable addChat(Chat chat);

    Single<Chat> getChat(Chat chat);

    void fetchUpdates(List<InboxChat> oldChatList, List<InboxChat> newChatList);

    void saveChats(List<InboxChat> inboxChats);
    Completable updateChats(List<InboxChat> chats);

}
