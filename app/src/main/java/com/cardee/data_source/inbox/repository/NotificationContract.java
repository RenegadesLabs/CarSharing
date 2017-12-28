package com.cardee.data_source.inbox.repository;

import io.reactivex.functions.Consumer;

public interface NotificationContract {

    void fetchNotifications();

    void updateBookingNotificationCount(Integer readCount);

    void updateChatNotificationCount(Integer readCount);

    void subscribeToNotificationUpdates(Consumer<Integer> consumer);

    void subscribeToAlertUpdates(Consumer<Boolean> consumer);

    void subscribeToChatUpdates(Consumer<Boolean> consumer);

    void saveSessionData();

}
