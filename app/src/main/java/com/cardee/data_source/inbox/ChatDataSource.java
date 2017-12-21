package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Observable;

public interface ChatDataSource {

    Observable<List<InboxChat>> getRemoteChats(String attachment);

    Observable<List<InboxChat>> getLocalChats(String attachment);

    void saveDataToDb(List<Chat> inboxChats);

}
