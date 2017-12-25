package com.cardee.data_source.inbox.remote.chat;

import com.cardee.data_source.inbox.local.chat.entity.Chat;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {

    Single<List<Chat>> getRemoteChats(String attachment);

    Single<Chat> getChat(Chat chat);

}
