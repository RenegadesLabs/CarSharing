package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.service.model.ChatNotification;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface InboxContract {

    Flowable<List<Chat>> getLocalChats(String attachment);

    Single<List<Chat>> getRemoteChats(String attachment);

    void fetchData(List<Chat> remoteChats);

    Completable updateChat(ChatNotification chat);
}
