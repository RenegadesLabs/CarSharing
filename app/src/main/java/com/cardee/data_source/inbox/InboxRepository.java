package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.ChatLocalDataSource;
import com.cardee.data_source.inbox.remote.ChatRemoteDataSource;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class InboxRepository implements InboxRepositoryContract {

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
    public Observable<List<InboxChat>> getChats(String attachment) {
        Observable<List<InboxChat>> localObservable = mChatLocalSource.getLocalChats(attachment);
        Observable<List<InboxChat>> remoteObservable = mChatRemoteSource.getRemoteChats(attachment);
        return Observable
                .merge(localObservable, remoteObservable);
    }
}
