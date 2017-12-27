package com.cardee.data_source.inbox.remote.chat;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.ChatApi;
import com.cardee.data_source.inbox.remote.api.model.entity.ChatRemoteMessage;
import com.cardee.data_source.inbox.remote.api.request.NewChatMessage;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChatItemRemoteSource implements RemoteData.ChatSingleSource {

    private final ChatApi mChatApi;
    private final ToChatMessageMapper mMapper;

    public ChatItemRemoteSource() {
        mChatApi = CardeeApp.retrofit.create(ChatApi.class);
        mMapper = new ToChatMessageMapper();
    }

    @Override
    public Single<List<ChatMessage>> getMessages(String attachment, int chatId) {
        return mChatApi.getMessages(attachment, chatId)
                .map(chatMessagesResponse -> mMapper.map(chatMessagesResponse.getMessages()));
    }

    @Override
    public Completable sendMessage(String newMessage) {
        return Completable.create(emitter
                -> mChatApi.sendMessage(new NewChatMessage(newMessage))
                .subscribeOn(Schedulers.io())
                .filter(messageResponse -> messageResponse != null && messageResponse.isSuccessful())
                .subscribe(messageResponse -> emitter.onComplete(), emitter::onError));
    }

    @Override
    public Completable markAsRead() {
        return null;
    }

    private class ToChatMessageMapper implements Mapper<ChatRemoteMessage[], List<ChatMessage>> {

        @Override
        public List<ChatMessage> map(ChatRemoteMessage[] response) {
            List<ChatMessage> chatMessageList = new ArrayList<>(15);
            for (ChatRemoteMessage chatRemote : response) {
                //TODO: implement mapper from RemoteChat to ChatMessage
            }
            return chatMessageList;
        }
    }
}
