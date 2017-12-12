package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.ChatLocalDataSource;
import com.cardee.data_source.inbox.remote.ChatRemoteDataSource;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<InboxChat>> getChats() {
        return Observable.create(emitter ->
                mChatLocalSource.getLocalChats().subscribe(inboxChats -> {
                    emitter.onNext(inboxChats);
                    emitter.onComplete();
                }, emitter::onError));

    }
}
