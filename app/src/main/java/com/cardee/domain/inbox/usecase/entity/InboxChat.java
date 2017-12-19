package com.cardee.domain.inbox.usecase.entity;

public class InboxChat {

    private Integer databaseId;

    private Integer mChatId;

    private String chatAttachment;

    private Integer mUnreadMessageCount;

    private String mRecipientName;

    private String mRecipientPhotoUrl;

    private String mLastMessageText;

    private String mLastMessageTime;

    private String mCarPhotoUrl;


    public InboxChat() {
    }

    public Integer getChatId() {
        return mChatId;
    }

    public Integer getUnreadMessageCount() {
        return mUnreadMessageCount;
    }

    public String getRecipientName() {
        return mRecipientName;
    }

    public String getRecipientPhotoUrl() {
        return mRecipientPhotoUrl;
    }

    public String getLastMessageText() {
        return mLastMessageText;
    }

    public String getLastMessageTime() {
        return mLastMessageTime;
    }

    public String getCarPhotoUrl() {
        return mCarPhotoUrl;
    }

    public Integer getDatabaseId() {
        return databaseId;
    }

    public String getChatAttachment() {
        return chatAttachment;
    }

    public static class Builder {

        private InboxChat mInboxChat;

        public Builder() {
            mInboxChat = new InboxChat();
        }

        public Builder withDatabaseId(Integer databaseId) {
            mInboxChat.databaseId = databaseId;
            return this;
        }

        public Builder withChatId(Integer chatId) {
            mInboxChat.mChatId = chatId;
            return this;
        }

        public Builder withChatAttachment(String chatAttachment) {
            mInboxChat.chatAttachment = chatAttachment;
            return this;
        }

        public Builder withUnreadMessageCount(Integer count) {
            mInboxChat.mUnreadMessageCount = count;
            return this;
        }

        public Builder withName(String name) {
            mInboxChat.mRecipientName = name;
            return this;
        }

        public Builder withPhotoUrl(String url) {
            mInboxChat.mRecipientPhotoUrl = url;
            return this;
        }

        public Builder withLastMessage(String message) {
            mInboxChat.mLastMessageText = message;
            return this;
        }

        public Builder withLastMessageTime(String time) {
            mInboxChat.mLastMessageTime = time;
            return this;
        }

        public Builder withCarPhoto(String url) {
            mInboxChat.mCarPhotoUrl = url;
            return this;
        }

        public InboxChat build() {
            return mInboxChat;
        }
    }
}
