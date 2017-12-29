package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.chat.ChatItemLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.chat.ChatItemRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChatRepository implements ChatContract {

    private static ChatRepository INSTANCE;

    private final LocalData.ChatSingleSource mLocalSource;
    private final RemoteData.ChatSingleSource mRemoteSource;

    private int chatId;
    private String attachment;

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
        this.attachment = attachment;
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
    public void addNewMessage(ChatMessage chatMessage) {
        mLocalSource.addNewMessage(chatMessage);
    }

    @Override
    public Completable sendMessage(String message) {
        return mRemoteSource.sendMessage(message);
    }

}
