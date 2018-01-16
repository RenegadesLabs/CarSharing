package com.cardee.data_source.inbox.service.controller;

import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.Notification;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.cardee.domain.util.Mapper;

import java.util.Map;

import static com.cardee.data_source.inbox.service.controller.FcmRepositoryController.INBOX_ALERT;
import static com.cardee.data_source.inbox.service.controller.FcmRepositoryController.INBOX_CHAT;
import static com.cardee.data_source.inbox.service.controller.FcmRepositoryController.INBOX_GENERAL;

public class FcmMessageMapper implements Mapper<Map<String, String>, Notification> {

    @Override
    public Notification map(Map<String, String> messageData) {
        switch (messageData.get(Key.TAG)) {
            case INBOX_CHAT:
                ChatNotification chatNotify = new ChatNotification();
                chatNotify.setChatId(Integer.valueOf(messageData.get(Key.Chat.CHAT_ID)));
                chatNotify.setChatAttachment(messageData.get(Key.PROFILE_TYPE).toLowerCase());
                chatNotify.setUnreadMessageCount(Integer.valueOf(messageData.get(Key.Chat.NEW_MESSAGES)));
                chatNotify.setUnreadChatCount(Integer.parseInt(messageData.get(Key.CHAT_COUNT)));
                chatNotify.setMessageId(Integer.parseInt(messageData.get(Key.Chat.MESSAGE_ID)));
                chatNotify.setMessageText(messageData.get(Key.Chat.MESSAGE));
                chatNotify.setSenderName(messageData.get(Key.Chat.SENDER));
                chatNotify.setMessageTime(messageData.get(Key.DATE_CREATED));
                return chatNotify;
            case INBOX_ALERT:
            case INBOX_GENERAL:
                AlertNotification alertNotify = new AlertNotification();
                alertNotify.setAlertId(Integer.valueOf(messageData.get(Key.Alert.USER_ALERT_ID)));
                alertNotify.setObjectId(Integer.valueOf(messageData.get(Key.Alert.OBJECT_ID)));
                alertNotify.setAlertAttachment(messageData.get(Key.PROFILE_TYPE).toLowerCase());
//                alertNotify.setAlertTitle(messageData.get(Key.Alert.MESSAGE_TITLE));
                alertNotify.setAlertType(messageData.get(Key.Alert.TYPE_NOTIFICATION));
                alertNotify.setUnreadAlertCount(Integer.valueOf(messageData.get(Key.ALERT_COUNT)));
                alertNotify.setAlertMessage(messageData.get(Key.Alert.MESSAGE_BODY));
                alertNotify.setAlertStatus(RemoteData.NotificationType.NEW);
                alertNotify.setDateCreated(messageData.get(Key.DATE_CREATED));
                return alertNotify;
        }
        return null;
    }
}
