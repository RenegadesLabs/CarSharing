package com.cardee.data_source.inbox.service.controller;

import android.util.Log;

import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.google.firebase.messaging.RemoteMessage;

public class FcmRepositoryController implements RepositoryController {

    static final String INBOX_CHAT = "CHAT";
    static final String INBOX_ALERT = "BOOKING";
    static final String INBOX_GENERAL = "GENERAL";

    private static final String TAG = RepositoryController.class.getSimpleName();

    private final InboxRepository mInboxRepository;
    private final ChatRepository mChatRepository;
    private final NotificationRepository mNotificationRepository;
    private final FcmMessageMapper mMessageMapper;

    public FcmRepositoryController() {
        mInboxRepository = InboxRepository.getInstance();
        mChatRepository = ChatRepository.getInstance();
        mNotificationRepository = NotificationRepository.getInstance();
        mMessageMapper = new FcmMessageMapper();
    }

    @Override
    public void updateInbox(RemoteMessage remoteMessage, ControllerCallback controllerCallback) {
        if (remoteMessage.getData().get(Key.TAG) == null) return;

        switch (remoteMessage.getData().get(Key.TAG)) {
            case INBOX_CHAT:
                updateChat(remoteMessage, controllerCallback);
                break;
            case INBOX_ALERT:
            case INBOX_GENERAL:
                updateBooking(remoteMessage, controllerCallback);
                break;
        }
    }

    private void updateChat(RemoteMessage remoteMessage, ControllerCallback controllerCallback) {
        ChatNotification chatNotification = (ChatNotification) mMessageMapper.map(remoteMessage.getData());
        chatNotification.setCurrentSession(mNotificationRepository.isCurrentMessagingSession(chatNotification.getChatId()));
        chatNotification.setCurrentSessionNeedToNotify(mNotificationRepository.isCurrentSessionNeedToNotify(chatNotification.getAttachment()));

        mInboxRepository.updateChat(chatNotification)
                .subscribe(() -> mChatRepository.addNewMessage(chatNotification),
                        throwable -> Log.e(TAG, "Chat update ERROR : " + throwable.getMessage()));
        mNotificationRepository.setRelevantChatUnreadCount(chatNotification.getUnreadChatCount());

        if (!chatNotification.isCurrentSession()) {
            controllerCallback.notifyUser(chatNotification);
        }
    }

    private void updateBooking(RemoteMessage remoteMessage, ControllerCallback controllerCallback) {
        AlertNotification alertNotification = (AlertNotification) mMessageMapper.map(remoteMessage.getData());
        alertNotification.setCurrentSessionNeedToNotify(mNotificationRepository.isCurrentSessionNeedToNotify(alertNotification.getAttachment()));

        mInboxRepository.addAlert(alertNotification);
        mNotificationRepository.setRelevantAlertUnreadCount(alertNotification.getUnreadCount());
        controllerCallback.notifyUser(alertNotification);
    }
}
