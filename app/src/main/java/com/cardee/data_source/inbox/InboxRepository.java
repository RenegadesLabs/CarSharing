package com.cardee.data_source.inbox;

import com.cardee.data_source.inbox.local.ChatLocalDataSource;
import com.cardee.data_source.inbox.local.LocalDataSource;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.data_source.inbox.remote.ChatRemoteDataSource;
import com.cardee.data_source.inbox.remote.RemoteDataSource;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InboxRepository implements InboxRepositoryContract {

    private static final String TAG = InboxRepository.class.getSimpleName();

    private static InboxRepository INSTANCE;
    private final LocalDataSource mChatLocalSource;
    private final RemoteDataSource mChatRemoteSource;

    private List<InboxChat> mCacheLocalChats;

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
        Single<List<InboxChat>> localSource = mChatLocalSource
                .getLocalChats(attachment)
                .doOnSuccess(inboxChats -> mCacheLocalChats = inboxChats);

        Observable<List<InboxChat>> remoteSource = mChatRemoteSource
                .getRemoteChats(attachment)
                .subscribeOn(Schedulers.computation())
                .doOnNext(inboxChats -> {
                    if (mCacheLocalChats.isEmpty()) {
                        mChatLocalSource.saveChats(inboxChats);
                    } else {
                        mChatLocalSource.fetchUpdates(mCacheLocalChats, inboxChats);
                    }
                });

        return Observable
                .concat(localSource.toObservable(), remoteSource)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<InboxChat> subscribe(String attachment) {
        return null;
    }

    @Override
    public Observable<List<InboxChat>> getRemoteChats(String attachment) {
        return null;
    }

    @Override
    public void addChat(Chat chat) {
        mChatLocalSource.addChat(chat);
    }

    @Override
    public Completable updateChat(Chat chat) {
        return Completable.create((CompletableEmitter emitter) ->
                mChatLocalSource.getChat(chat)
                        .subscribe((Chat persistChat) -> {
                            persistChat.setLastMessageText(chat.getLastMessageText());
                            persistChat.setLastMessageTime(chat.getLastMessageTime());
                            persistChat.setUnreadMessageCount(chat.getUnreadMessageCount());
                            addChat(persistChat);
                        }, throwable -> {
//                            mChatRemoteSource.getChat(chat);
                        }));
    }
}
