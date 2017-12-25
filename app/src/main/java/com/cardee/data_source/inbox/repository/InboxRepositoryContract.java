package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.entity.Chat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface InboxRepositoryContract {

    Flowable<List<Chat>> getLocalChats(String attachment);

    Single<List<Chat>> getRemoteChats(String attachment);

    void fetchOrSaveData(List<Chat> remoteChats);

    Completable updateChat(Chat chat);

    Observable<Chat> subscribe(String attachment);
}
