package com.cardee.data_source.inbox.local;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalDataSource {

    Flowable<List<InboxChat>> getLocalChats(String attachment);

    Completable addChat(Chat chat);

    Single<Chat> getChat(Chat chat);

    void saveDataToDb(List<InboxChat> inboxChats);

}
