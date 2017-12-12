package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ChatDataSource {

    Single<List<InboxChat>> getRemoteChats();

    Maybe<List<InboxChat>> getLocalChats();

    void saveDataToDb(List<Chat> inboxChats);

}
