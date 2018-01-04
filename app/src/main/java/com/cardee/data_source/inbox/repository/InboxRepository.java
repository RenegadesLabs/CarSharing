package com.cardee.data_source.inbox.repository;

import android.util.Log;

import com.cardee.data_source.inbox.local.alert.AlertListLocalSource;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.ChatListLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.remote.alert.AlertRemoteDataSource;
import com.cardee.data_source.inbox.remote.chat.ChatListRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.inbox.service.model.ChatNotification;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InboxRepository implements InboxContract {

    private static final String TAG = InboxRepository.class.getSimpleName();
    private static InboxRepository INSTANCE;

    private final LocalData.ChatListSource mChatLocalSource;
    private final LocalData.AlertListSource mAlertLocalSource;
    private final RemoteData.ChatListSource mChatRemoteSource;
    private final RemoteData.AlertListSource mAlertRemoteSource;

    public static InboxRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InboxRepository();
        }
        return INSTANCE;
    }

    private InboxRepository() {
        mChatLocalSource = new ChatListLocalSource();
        mChatRemoteSource = new ChatListRemoteSource();
        mAlertLocalSource = new AlertListLocalSource();
        mAlertRemoteSource = new AlertRemoteDataSource();
    }

    @Override
    public Flowable<List<Alert>> getLocalAlerts(String attachment) {
        return mAlertLocalSource.getLocalAlerts(attachment);
    }

    @Override
    public Flowable<List<Chat>> getLocalChats(String attachment) {
        return mChatLocalSource.getLocalChats(attachment);
    }

    @Override
    public Single<List<Alert>> getRemoteAlerts(String attachment) {
        return mAlertRemoteSource
                .getRemoteAlerts(attachment)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Chat>> getRemoteChats(String attachment) {
        return mChatRemoteSource
                .getRemoteChats(attachment)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void fetchChatData(List<Chat> remoteChats) {
        mChatLocalSource.saveChats(remoteChats);
    }

    @Override
    public void fetchAlertData(List<Alert> remoteAlerts) {
        mAlertLocalSource.saveAlerts(remoteAlerts);
    }

    @Override
    public Completable updateChat(ChatNotification chatNotification) {
        return Completable.create((CompletableEmitter emitter) ->
                mChatLocalSource.updateChat(chatNotification)
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.e(TAG, "Chat updated");
                            emitter.onComplete();
                        }, throwable -> getNewChat(chatNotification, emitter)));
    }

    private void getNewChat(ChatNotification chatNotification, CompletableEmitter emitter) {
        mChatRemoteSource.getSingleChat(chatNotification.getChatId(), chatNotification.getChatAttachment())
                .subscribe(chat -> mChatLocalSource.addChat(chat)
                                .subscribe(emitter::onComplete, emitter::onError),
                        responseError -> {
                            emitter.onError(responseError);
                            Log.d(TAG, "Error while obtaining chat information");
                        });
    }
}
