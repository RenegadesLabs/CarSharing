package com.cardee.data_source.inbox.remote.chat;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.request.NewChatMessage;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RemoteData {

    interface ChatListSource extends RemoteData {

        Single<List<Chat>> getRemoteChats(String attachment);

        Single<Chat> getSingleChat(int serverId, String attachment);
    }

    interface ChatSingleSource extends RemoteData {

        Single<List<ChatMessage>> getMessages(int chatId);

        Completable sendMessage(String newMessage);

        Completable markAsRead(int messageId);
    }

    interface AlertListSource extends RemoteData {

    }

    interface AlertSingleSource extends RemoteData {

    }
}
