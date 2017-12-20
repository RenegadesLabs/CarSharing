package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface InboxRepositoryContract {

    Observable<List<InboxChat>> getChats(String attachment);

    Completable addChat(Chat chat);

    Completable updateChat(Chat chat);
}
