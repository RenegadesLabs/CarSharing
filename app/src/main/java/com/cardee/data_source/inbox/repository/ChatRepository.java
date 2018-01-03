package com.cardee.data_source.inbox.repository;

import android.util.Log;

import com.cardee.data_source.inbox.local.chat.ChatItemLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.inbox.remote.chat.ChatItemRemoteSource;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChatRepository implements ChatContract {

    private static final String TAG = ChatRepository.class.getSimpleName();
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
                            emitter.onComplete();
                            mLocalSource.persistMessages(messageList, chatId);
                            if (isLastInboxMessageDidNotRead(messageList)) {
                                markAsRead(getLastMessageId(messageList));
                            }
                        }, emitter::onError));
    }

    @Override
    public void removeChatUnreadStatus(int chatId) {
        mLocalSource.updateChatUnreadCount(chatId);
    }

    private void markAsRead(int lastMessageId) {
        mRemoteSource.markAsRead(lastMessageId)
                .subscribe(() -> mLocalSource.markAsRead(chatId),
                        throwable -> Log.d(TAG, "Connection lost"));
    }

    private boolean isLastInboxMessageDidNotRead(List<ChatMessage> messageList) {
        return getLastMessage(messageList).getInbox() && !getLastMessage(messageList).getIsRead();
    }

    private int getLastMessageId(List<ChatMessage> messageList) {
        return getLastMessage(messageList).getMessageId();
    }

    private ChatMessage getLastMessage(List<ChatMessage> chatMessages) {
        return chatMessages.get(chatMessages.size() - 1);
    }

    @Override
    public Single<List<ChatMessage>> getNewChat() {
        return null;
    }

    @Override
    public Single<Integer> sendMessage(String messageText) {
        return Single.create(emitter -> mRemoteSource.sendMessage(messageText, chatId)
                .subscribeOn(Schedulers.io())
                .subscribe(newMessage -> {
                    emitter.onSuccess(newMessage.getMessageId());
                    fetchMessageData(messageText, newMessage);
                    mLocalSource.addOutputMessage(newMessage);
                    mLocalSource.updateChatLastMessage(newMessage);
                }, throwable -> {
                    emitter.onError(throwable);
                    Log.d(TAG, "Server connection lost");
                }));
    }

    private void fetchMessageData(String messageText, NewMessage newMessage) {
        newMessage.setChatId(chatId);
        newMessage.setAttachment(attachment);
        newMessage.setMessage(messageText);
    }

    @Override
    public void addNewMessage(ChatNotification chatNotification) {
        mLocalSource.addInputMessage(chatNotification);
        if (chatNotification.isCurrentSession()) {
            markAsReadIncomingMessage(chatNotification.getMessageId());
        }
    }

    private void markAsReadIncomingMessage(int messageId) {
        mRemoteSource.markAsRead(messageId).subscribe(
                () -> Log.d(TAG, "Message " + messageId + " marked"),
                throwable -> Log.d(TAG, "Connection lost"));
    }
}
