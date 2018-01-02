package com.cardee.data_source.inbox.local.chat;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.domain.util.Mapper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChatItemLocalSource implements LocalData.ChatSingleSource {

    private static final String TAG = ChatItemLocalSource.class.getSimpleName();
    private final LocalInboxDatabase mDataBase;

    public ChatItemLocalSource() {
        mDataBase = LocalInboxDatabase.getInstance(CardeeApp.context);
    }

    @Override
    public Single<ChatInfo> getChatInfo(int chatId) {
        ToChatInfoMapper chatInfoMapper = new ToChatInfoMapper();
        return mDataBase.getChatDao()
                .getChatInfo(chatId)
                .subscribeOn(Schedulers.computation())
                .flatMap(chat -> Single.just(chatInfoMapper.map(chat)));

    }

    @Override
    public Flowable<List<ChatMessage>> getMessages(int databaseId) {
        return mDataBase.getChatMassageDao()
                .getMessages(databaseId)
                .distinct();
    }

    @Override
    public void persistMessages(List<ChatMessage> messageList, int chatId) {
        Completable.fromRunnable(() -> {
            for (ChatMessage chatMessage : messageList) {
                chatMessage.setChatId(chatId);
            }
            mDataBase.getChatMassageDao().persistMessages(messageList);
        }).subscribe();
    }

    @Override
    public void markAsRead(int databaseId) {
        mDataBase.getChatMassageDao().updateReadStatus(String.valueOf(databaseId));
    }

    @Override
    public void updateChatUnreadCount(int chatId) {
        Completable.fromRunnable(()
                -> mDataBase.getChatDao().updateChatUnreadCount(chatId))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    @Override
    public void updateChatLastMessage(NewMessage newMessage) {
        Completable.fromRunnable(() -> mDataBase.getChatDao()
                .updateChatLastMessage(
                        newMessage.getMessage(),
                        newMessage.getDateCreated(),
                        newMessage.getChatId(),
                        newMessage.getAttachment()))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    @Override
    public void addOutputMessage(NewMessage newMessage) {
        Completable.fromRunnable(() -> mDataBase.getChatMassageDao()
                .addNewMessage(new ChatMessage.Builder()
                        .withChatId(newMessage.getChatId())
                        .withMessageId(newMessage.getMessageId())
                        .withMessage(newMessage.getMessage())
                        .withDateCreated(newMessage.getDateCreated())
                        .withIsInbox(false)
                        .withIsRead(true)
                        .build()))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    @Override
    public void addInputMessage(ChatNotification newMessage) {
        Completable.fromRunnable(() -> mDataBase.getChatMassageDao()
                .addNewMessage(new ChatMessage.Builder()
                        .withChatId(newMessage.getChatId())
                        .withMessageId(newMessage.getMessageId())
                        .withMessage(newMessage.getMessageText())
                        .withDateCreated(newMessage.getMessageTime())
                        .withIsInbox(true)
                        .withIsRead(true)
                        .build()))
                .doOnComplete(() -> Log.e(TAG, "Message list updated"))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    private static class ToChatInfoMapper implements Mapper<Chat, ChatInfo> {

        @Override
        public ChatInfo map(Chat localChat) {
            return new ChatInfo.Builder()
                    .withRecipientName(localChat.getRecipientName())
                    .withRecipientPhoto(localChat.getPhotoUrl())
                    .withCarTitle(localChat.getCarTitle())
                    .withLicenseNumber(localChat.getCarLicenseNumber())
                    .withCarPhoto(localChat.getCarPhotoUrl())
                    .withBookingBegin(localChat.getBookingTimeBegin())
                    .withBookingEnd(localChat.getBookingTimeEnd())
                    .build();
        }
    }
}
