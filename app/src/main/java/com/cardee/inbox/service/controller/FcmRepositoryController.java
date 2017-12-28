package com.cardee.inbox.service.controller;

import android.util.Log;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.google.firebase.messaging.RemoteMessage;

import static com.cardee.inbox.service.controller.FcmMessageMapper.INBOX_ALERT;
import static com.cardee.inbox.service.controller.FcmMessageMapper.INBOX_CHAT;

public class FcmRepositoryController implements RepositoryController {

    private static final String TAG = RepositoryController.class.getSimpleName();

    private final InboxRepository mInboxRepository;
    private final NotificationRepository mNotificationRepository;
    private final FcmMessageMapper mMessageMapper;

    public FcmRepositoryController() {
        mInboxRepository = InboxRepository.getInstance();
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
        Chat newChatData = mMessageMapper.map(remoteMessage.getData());
        mInboxRepository.updateChat(newChatData)
                .subscribe(()
                        -> Log.e(TAG, "Chat updated"), throwable
                        -> Log.e(TAG, "Chat update ERROR : " + throwable.getMessage()));
        mNotificationRepository.updateChatNotificationCount(mMessageMapper.getUnreadCountMessage());
    }

    private void updateBooking() {

    }
}
