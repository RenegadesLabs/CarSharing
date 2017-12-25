package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface InboxRepositoryContract {

    Flowable<List<Chat>> getLocalChats(String attachment);

    Observable<Chat> subscribe(String attachment);

    Single<List<Chat>> getRemoteChats(String attachment);

    void addChat(Chat chat);

    Completable updateChat(Chat chat);
}
