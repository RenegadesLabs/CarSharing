package com.cardee.domain.inbox.usecase.chat;

import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

import io.reactivex.functions.Consumer;

public class GetChatInfo implements UseCase<GetChatInfo.RequestValues, GetChatInfo.ResponseValues> {

    private final ChatRepository mRepository;

    public GetChatInfo() {
        mRepository = new ChatRepository();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.getChatInfo(values.getDatabaseId(), values.getServerId())
                .subscribe(chatInfo -> callback.onSuccess(new ResponseValues(chatInfo)));
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final int databaseId;
        private final int serverId;

        public RequestValues(int databaseId, int serverId) {
            this.databaseId = databaseId;
            this.serverId = serverId;
        }

        public int getDatabaseId() {
            return databaseId;
        }

        public int getServerId() {
            return serverId;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final ChatInfo chatInfo;

        public ResponseValues(ChatInfo chatInfo) {
            this.chatInfo = chatInfo;
        }

        public ChatInfo getChatInfo() {
            return chatInfo;
        }
    }
}
