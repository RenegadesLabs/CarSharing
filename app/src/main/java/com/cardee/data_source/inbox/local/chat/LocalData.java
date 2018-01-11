package com.cardee.data_source.inbox.local.chat;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalData {

    interface ChatListSource extends LocalData {

        Flowable<List<Chat>> getLocalChats(String attachment);

        void saveChats(List<Chat> inboxChats);

        Completable addChat(Chat chat);

        Completable updateChat(ChatNotification chat);

        Single<Chat> getChat(Chat chat);
    }

    interface ChatSingleSource extends LocalData {

        Single<ChatInfo> getChatInfo(int chatId);

        Flowable<List<ChatMessage>> getMessages(int chatId);

        void markAsRead(int chatId);

        void updateChatUnreadCount(int chatId);

        void updateChatLastMessage(NewMessage newMessage);

        void persistMessages(List<ChatMessage> messageList, int chatId);

        void addOutputMessage(NewMessage newMessage);

        void addInputMessage(ChatNotification newMessage);
    }

    interface AlertListSource extends LocalData {

        Flowable<List<Alert>> getLocalAlerts(String attachment);

        void saveAlerts(List<Alert> inboxAlerts);

        void addAlert(AlertNotification alert);
    }
}
