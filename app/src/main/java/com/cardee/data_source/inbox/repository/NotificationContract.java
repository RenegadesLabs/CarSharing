package com.cardee.data_source.inbox.repository;

import io.reactivex.functions.Consumer;

public interface NotificationContract {

    void fetchNotifications();

    void setRelevantChatUnreadCount(Integer readCount);

    void setRelevantAlertUnreadCount(Integer readCount);

    void updateChatUnreadCount(Integer count);

    void updateInboxUnreadCount();

    boolean isCurrentSessionNeedToNotify(String attachment);


    void setCurrentChatSession(int chatId);

    void resetCurrentChatSession();

    boolean isCurrentMessagingSession(int chatId);

    void subscribeToNotificationUpdates(Consumer<Integer> consumer);

    void subscribeToAlertUpdates(Consumer<Boolean> consumer);

    void subscribeToChatUpdates(Consumer<Boolean> consumer);

    void saveSessionData();
}
