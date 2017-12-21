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
import io.reactivex.disposables.Disposable;

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
        Observable<List<InboxChat>> localSource = mChatLocalSource.getLocalChats(attachment);
        Observable<List<InboxChat>> remoteSource = mChatRemoteSource
                .getRemoteChats(attachment)
                .filter(remoteChats -> remoteChats != null && !remoteChats.isEmpty());

        return Observable.create(emitter -> {
            localSource.subscribe(localChats -> {
                mCacheLocalChats = localChats;
                emitter.onNext(localChats);
            });
            Disposable subscribe = remoteSource
                    .subscribe(remoteChats -> {
                        if (mCacheLocalChats.isEmpty()) {
                            mChatLocalSource.saveChats(remoteChats);
                        } else {
                            mChatLocalSource.updateChats(remoteChats);
                        }
                    });
            emitter.setCancellable(() -> {
                mCacheLocalChats = null;
                subscribe.dispose();
            });
        });
    }

    @Override
    public Observable<List<InboxChat>> getRemoteChats(String attachment) {
        return mChatRemoteSource
                .getRemoteChats(attachment)
                .filter(remoteChats -> remoteChats != null && !remoteChats.isEmpty());
    }

    @Override
    public Completable addChat(Chat chat) {
        return mChatLocalSource.addChat(chat);
    }

    @Override
    public Completable updateChat(Chat chat) {
        return Completable.create((CompletableEmitter emitter) ->
                mChatLocalSource.getChat(chat)
                        .subscribe((Chat persistChat) -> {
                            persistChat.setLastMessageText(chat.getLastMessageText());
                            persistChat.setLastMessageTime(chat.getLastMessageTime());
                            persistChat.setUnreadMessageCount(chat.getUnreadMessageCount());
                            addChat(persistChat)
                                    .subscribe(
                                            emitter::onComplete,
                                            emitter::onError);
                        }, throwable -> {
//                            mChatRemoteSource.getChat(chat);
                        }));
    }
}
