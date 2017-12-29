package com.cardee.data_source.inbox.repository;

import android.util.Log;

import com.cardee.data_source.inbox.local.chat.ChatListLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.remote.chat.ChatListRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InboxRepository implements InboxContract {

    private static final String TAG = InboxRepository.class.getSimpleName();
    private final LocalData.ChatListSource mChatLocalSource;
    private final RemoteData.ChatListSource mChatRemoteSource;

    private static InboxRepository INSTANCE;
    private List<Chat> mCacheLocalChats;

    public static InboxRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InboxRepository();
        }
        return INSTANCE;
    }

    private InboxRepository() {
        mChatLocalSource = new ChatListLocalSource();
        mChatRemoteSource = new ChatListRemoteSource();
    }

    @Override
    public Flowable<List<Chat>> getLocalChats(String attachment) {
        return mChatLocalSource
                .getLocalChats(attachment)
                .doOnNext(localChats -> {
                    mCacheLocalChats = localChats;
//                    Collections.sort(localChats);
                });
    }

    @Override
    public Single<List<Chat>> getRemoteChats(String attachment) {
        return mChatRemoteSource
                .getRemoteChats(attachment)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void fetchOrSaveData(List<Chat> remoteChats) {
        if (mCacheLocalChats == null || mCacheLocalChats.isEmpty()) {
            mChatLocalSource.saveChats(remoteChats);
        } else {
            mChatLocalSource.fetchUpdates(mCacheLocalChats, remoteChats);
        }
    }

    @Override
    public Completable updateChat(Chat chatFromFcm) {
        return Completable.create((CompletableEmitter emitter) ->
                mChatLocalSource.updateChat(chatFromFcm)
                        .subscribeOn(Schedulers.io())
                        .subscribe(emitter::onComplete, throwable -> getNewChat(chatFromFcm)));
    }

//    private void updateChatList(Chat chatFromFcm, Chat persistChat) {
//        persistChat.setLastMessageText(chatFromFcm.getLastMessageText());
//        persistChat.setLastMessageTime(chatFromFcm.getLastMessageTime());
//        persistChat.setRecipientName(chatFromFcm.getRecipientName());
//        persistChat.setUnreadMessageCount(chatFromFcm.getUnreadMessageCount());
//        mChatLocalSource.addChat(persistChat);
//    }

    private void getNewChat(Chat chatFromFcm) {
        mChatRemoteSource.getSingleChat(chatFromFcm.getChatId(), chatFromFcm.getChatAttachment())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        newChat -> mChatLocalSource.addChat(chatFromFcm),
                        responseError -> Log.d(TAG, "Error while obtaining chat information"));
    }
}
