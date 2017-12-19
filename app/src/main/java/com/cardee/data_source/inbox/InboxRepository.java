package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.ChatLocalDataSource;
import com.cardee.data_source.inbox.remote.ChatRemoteDataSource;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class InboxRepository implements AlertDataSource, ChatDataSource {

    private static InboxRepository INSTANCE;
    private final ChatDataSource mChatLocalSource;
    private final ChatDataSource mChatRemoteSource;
//    private final AlertDataSource mAlertLocalSource;
//    private final AlertDataSource mAlertRemoteSource;

    public static InboxRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InboxRepository();
        }
        return INSTANCE;
    }

    private InboxRepository() {
        mChatLocalSource = ChatLocalDataSource.getInstance();
        mChatRemoteSource = ChatRemoteDataSource.getInstance();
    }

    @Override
    public Single<List<InboxChat>> getRemoteChats() {
        return null;
    }

    @Override
    public Maybe<List<InboxChat>> getLocalChats() {
        return null;
    }
}
