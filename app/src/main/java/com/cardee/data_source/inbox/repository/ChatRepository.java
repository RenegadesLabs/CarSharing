package com.cardee.data_source.inbox.repository;

import android.util.Log;

import com.cardee.data_source.inbox.local.chat.ChatItemLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.inbox.remote.chat.ChatItemRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ChatRepository implements ChatContract {

    private static final String TAG = ChatRepository.class.getSimpleName();
    private static ChatRepository INSTANCE;

    private final LocalData.ChatSingleSource mLocalSource;
    private final RemoteData.ChatSingleSource mRemoteSource;

    private int chatId;

    public static ChatRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRepository();
        }
        return INSTANCE;
    }

    private ChatRepository() {
        mLocalSource = new ChatItemLocalSource();
        mRemoteSource = new ChatItemRemoteSource();
    }

    @Override
    public void sendChatIdentifier(Integer chatId, String attachment) {
        this.chatId = chatId;
    }

    @Override
    public Single<ChatInfo> getChatInfo() {
        return mLocalSource.getChatInfo(chatId);
    }

    @Override
    public Flowable<List<ChatMessage>> getLocalMessages() {
        return mLocalSource.getMessages(chatId);
    }

    @Override
    public Completable getRemoteMessages() {
        return Completable.create(emitter ->
                mRemoteSource.getMessages(chatId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(messageList -> {
                            mLocalSource.persistMessages(messageList, chatId);
                            if (isLastMessageDidNotRead(messageList)) {
                                markAsRead(getLastMessageId(messageList), emitter);
                            }
                        }, emitter::onError));
    }

    @Override
    public void removeChatUnreadStatus(int chatId) {
        mLocalSource.updateChatUnreadCount(chatId);
    }

    private void markAsRead(int lastMessageId, CompletableEmitter emitter) {
        mRemoteSource.markAsRead(lastMessageId)
                .subscribe(() -> {
                    mLocalSource.markAsRead(chatId);
                    emitter.onComplete();
                }, emitter::onError);
    }

    private boolean isLastMessageDidNotRead(List<ChatMessage> messageList) {
        return !messageList.get(messageList.size() - 1).getIsRead();
    }

    private int getLastMessageId(List<ChatMessage> messageList) {
        return messageList.get(messageList.size() - 1).getMessageId();
    }

    @Override
    public Single<List<ChatMessage>> getNewChat() {
        return null;
    }

    @Override
    public Single<Integer> sendMessage(String message) {
        return Single.create(emitter -> mRemoteSource.sendMessage(message, chatId)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(newMessage -> emitter.onSuccess(newMessage.getMessageId()))
                .subscribe(newMessage -> {
                    newMessage.setChatId(chatId);
                    newMessage.setMessage(message);
                    mLocalSource.addNewMessage(newMessage);
                }, throwable -> {
                    emitter.onError(throwable);
                    Log.d(TAG, "Server connection lost");
                }));


    }
}
