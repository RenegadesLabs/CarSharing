package com.cardee.inbox.service.controller;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.util.Mapper;

import java.util.Map;


public class FcmMessageMapper implements Mapper<Map<String, String>, Chat> {

    @Override
    public Chat map(Map<String, String> messageData) {
        Chat chat = new Chat();
        chat.setChatId(Integer.valueOf(messageData.get(Key.Chat.CHAT_ID)));
        chat.setUnreadMessageCount(Integer.valueOf(messageData.get(Key.Chat.NEW_MESSAGES)));
        chat.setLastMessageText(messageData.get(Key.Chat.MESSAGE));
        chat.setLastMessageTime(messageData.get(Key.Chat.DATE_CREATED));
        chat.setChatAttachment(messageData.get(Key.Chat.PROFILE_TYPE).toLowerCase());
        return chat;
    }

    public static class Key {

        public static class Chat {
            public static final String SENDER = "sender";
            public static final String TAG = "tag";
            public static final String MESSAGE = "message";
            public static final String MESSAGE_ID = "message_id";

            static final String CHAT_ID = "chat_id";
            static final String DATE_CREATED = "date_created";
            static final String NEW_MESSAGES = "new_messages";
            static final String PROFILE_TYPE = "profile_type";
        }

        public static class Booking {
            static final String OBJECT_ID = "object_id";
        }
    }
}
