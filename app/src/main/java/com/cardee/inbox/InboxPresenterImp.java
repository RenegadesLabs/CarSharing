package com.cardee.inbox;

import com.cardee.data_source.inbox.repository.NotificationRepository;

public class InboxPresenterImp implements InboxContract.Presenter {

    private InboxContract.View mInboxView;
    private final NotificationRepository mNotificationRepository;

    InboxPresenterImp() {
        mNotificationRepository = NotificationRepository.getInstance();
    }

    public void init(InboxContract.View view) {
        mInboxView = view;
    }

    @Override
    public void subscribeToNotificationUpdates() {
        mNotificationRepository
                .subscribeToAlertUpdates(isUnreadAlertsExist -> mInboxView.setAlertTabState(isUnreadAlertsExist));

        mNotificationRepository
                .subscribeToChatUpdates(isUnreadChatExist -> mInboxView.setChatTabState(isUnreadChatExist));
    }

    @Override
    public void onDestroy() {
        mInboxView = null;
    }
}
