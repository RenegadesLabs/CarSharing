package com.cardee.data_source.inbox.remote;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface RemoteDataSource {

    Single<List<Chat>> getRemoteChats(String attachment);

    Single<Chat> getChat(Chat chat);

}
