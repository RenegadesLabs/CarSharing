package com.cardee.data_source.inbox.repository;

import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import io.reactivex.Single;

public interface ChatContract {

    Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId);
}
