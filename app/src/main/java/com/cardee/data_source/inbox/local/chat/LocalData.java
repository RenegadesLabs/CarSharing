package com.cardee.data_source.inbox.local.chat;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalData {

    interface ChatListSource extends LocalData {

        Flowable<List<Chat>> getLocalChats(String attachment);

        void saveChats(List<Chat> inboxChats);

        void addChat(Chat chat);

        Completable updateChat(Chat chat);

        Single<Chat> getChat(Chat chat);
    }

    interface ChatSingleSource extends LocalData {

        Single<ChatInfo> getChatInfo(int chatId);

        Flowable<List<ChatMessage>> getMessages(int chatId);

        void markAsRead(int chatId);

        void updateChatUnreadCount(int chatId);

        void updateChatLastMessage(NewMessage newMessage);

        void persistMessages(List<ChatMessage> messageList, int chatId);

        void addNewMessage(NewMessage newMessage);
    }

    interface AlertListSource extends LocalData {

    }

    interface AlertSingleSource extends LocalData {

    }
}
