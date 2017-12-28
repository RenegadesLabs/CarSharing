package com.cardee.domain.inbox.usecase.chat;

import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;

public class GetChatInfo implements UseCase<GetChatInfo.RequestValues, GetChatInfo.ResponseValues> {

    private final ChatRepository mRepository;

    public GetChatInfo() {
        mRepository = ChatRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mRepository.sendChatIdentifier(values.getServerId(), values.getDatabaseId(), values.getAttachment());
        mRepository.getChatInfo()
                .subscribe(chatInfo -> callback.onSuccess(new ResponseValues(chatInfo)));
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final int databaseId;
        private final int serverId;
        private final String attachment;

        public RequestValues(int databaseId, int serverId, String attachment) {
            this.databaseId = databaseId;
            this.serverId = serverId;
            this.attachment = attachment;
        }

        int getDatabaseId() {
            return databaseId;
        }

        int getServerId() {
            return serverId;
        }

        public String getAttachment() {
            return attachment;
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
