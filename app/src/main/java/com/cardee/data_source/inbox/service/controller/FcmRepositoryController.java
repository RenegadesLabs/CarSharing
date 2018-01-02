package com.cardee.data_source.inbox.service.controller;

import android.util.Log;

import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.data_source.inbox.service.model.ChatNotification;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.Completable;

import static com.cardee.data_source.inbox.service.controller.FcmMessageMapper.INBOX_ALERT;
import static com.cardee.data_source.inbox.service.controller.FcmMessageMapper.INBOX_CHAT;

public class FcmRepositoryController implements RepositoryController {

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
    public void updateInbox(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().get(FcmMessageMapper.Key.TAG) != null) {
            switch (remoteMessage.getData().get(FcmMessageMapper.Key.TAG)) {
                case INBOX_CHAT:
                    updateChat(remoteMessage);
                    break;
                case INBOX_ALERT:
                    updateBooking();
                    break;
            }
        }
    }

    private void updateChat(RemoteMessage remoteMessage) {
        ChatNotification chatNotification = mMessageMapper.map(remoteMessage.getData());
        mInboxRepository.updateChat(chatNotification)
                .subscribe(() -> mChatRepository.addNewMessage(chatNotification),
                        throwable -> Log.e(TAG, "Chat update ERROR : " + throwable.getMessage()));

        mNotificationRepository.setRelevantAlertUnreadCount(chatNotification.getUnreadChatCount());
    }

    private void updateBooking() {

    }
}
