package com.cardee.data_source.inbox.service.controller;

import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.cardee.domain.util.Mapper;

import java.util.Map;

public class FcmMessageMapper implements Mapper<Map<String, String>, ChatNotification> {

    static final String INBOX_CHAT = "CHAT";
    static final String INBOX_ALERT = "BOOKING";

    @Override
    public ChatNotification map(Map<String, String> messageData) {
        ChatNotification chatNotification = new ChatNotification();

        chatNotification.setChatId(Integer.valueOf(messageData.get(Key.Chat.CHAT_ID)));
        chatNotification.setChatAttachment(messageData.get(Key.Chat.PROFILE_TYPE).toLowerCase());
        chatNotification.setUnreadMessageCount(Integer.valueOf(messageData.get(Key.Chat.NEW_MESSAGES)));
        chatNotification.setUnreadChatCount(Integer.parseInt(messageData.get(Key.CHAT_COUNT)));

        chatNotification.setMessageId(Integer.parseInt(messageData.get(Key.Chat.MESSAGE_ID)));
        chatNotification.setMessageText(messageData.get(Key.Chat.MESSAGE));
        chatNotification.setSenderName(messageData.get(Key.Chat.SENDER));
        chatNotification.setMessageTime(messageData.get(Key.Chat.DATE_CREATED));

        return chatNotification;
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
