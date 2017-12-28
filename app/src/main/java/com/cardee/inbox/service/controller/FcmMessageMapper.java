package com.cardee.inbox.service.controller;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.domain.util.Mapper;

import java.util.Map;


public class FcmMessageMapper implements Mapper<Map<String, String>, Chat> {

    static final String INBOX_CHAT = "CHAT";
    static final String INBOX_ALERT = "BOOKING";

    private int unreadCountMessage;

    @Override
    public Chat map(Map<String, String> messageData) {
        Chat chat = new Chat();

        chat.setChatServerId(Integer.valueOf(messageData.get(Key.Chat.CHAT_ID)));
        chat.setUnreadMessageCount(Integer.valueOf(messageData.get(Key.Chat.NEW_MESSAGES)));
        chat.setLastMessageText(messageData.get(Key.Chat.MESSAGE));
        chat.setRecipientName(messageData.get(Key.Chat.SENDER));
        chat.setLastMessageTime(messageData.get(Key.Chat.DATE_CREATED));
        chat.setChatAttachment(messageData.get(Key.Chat.PROFILE_TYPE).toLowerCase());

        unreadCountMessage = Integer.parseInt(messageData.get(Key.CHAT_COUNT));
        return chat;
    }

    int getUnreadCountMessage() {
        return unreadCountMessage;
    }

    public static class Key {

        public static final String TAG = "tag";
        public static final String BADGE = "badge";
        public static final String CHAT_COUNT = "chat_cnt";
        public static final String ALERT_COUNT = "alert_cnt";

        public static class Chat {
            public static final String SENDER = "sender";
            public static final String MESSAGE = "message";
            public static final String MESSAGE_ID = "message_id";

            static final String CHAT_ID = "chat_id";
            static final String DATE_CREATED = "date_created";
            static final String NEW_MESSAGES = "new_messages";
            static final String PROFILE_TYPE = "profile_type";
        }

        public static class Booking {
            static final String OBJECT_ID = "object_id";
            static final String MESSAGE_TITLE = "message_title";
            static final String MESSAGE_BODY = "message_body";
        }
    }
}
