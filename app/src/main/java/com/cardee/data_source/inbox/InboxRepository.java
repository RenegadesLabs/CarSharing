package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.ChatLocalDataSource;
import com.cardee.data_source.inbox.local.LocalDataSource;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.data_source.inbox.remote.ChatRemoteDataSource;
import com.cardee.data_source.inbox.remote.RemoteDataSource;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InboxRepository implements InboxRepositoryContract {

    private static final String TAG = InboxRepository.class.getSimpleName();

    private static InboxRepository INSTANCE;
    private final LocalDataSource mChatLocalSource;
    private final RemoteDataSource mChatRemoteSource;

    private List<Chat> mCacheLocalChats;

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
    public Flowable<List<Chat>> getLocalChats(String attachment) {
        return mChatLocalSource
                .getLocalChats(attachment)
                .doOnNext(localChats -> {
                    mCacheLocalChats = localChats;
                    Collections.sort(localChats);
                });
    }

    @Override
    public Single<List<Chat>> getRemoteChats(String attachment) {
        return mChatRemoteSource
                .getRemoteChats(attachment)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(remoteChats -> {
                    if (mCacheLocalChats.isEmpty()) {
                        mChatLocalSource.saveChats(remoteChats);
                    } else {
                        mChatLocalSource.fetchUpdates(mCacheLocalChats, remoteChats);
                    }
                });
    }

    @Override
    public void addChat(Chat chat) {
        mChatLocalSource.addChat(chat);
    }

    @Override
    public Completable updateChat(Chat chat) {
        return Completable.create((CompletableEmitter emitter) ->
                mChatLocalSource.getChat(chat)
                        .subscribeOn(Schedulers.io())
                        .subscribe((Chat persistChat) -> {
                            persistChat.setLastMessageText(chat.getLastMessageText());
                            persistChat.setLastMessageTime(chat.getLastMessageTime());
                            persistChat.setUnreadMessageCount(chat.getUnreadMessageCount());
                            addChat(persistChat);
                        }, throwable -> {
                            if (mCacheLocalChats != null) {
                                getRemoteChats(chat.getChatAttachment());
                            }
                        }));
    }

    @Override
    public Observable<Chat> subscribe(String attachment) {
        return null;
    }
}
