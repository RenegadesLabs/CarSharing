package com.cardee.data_source.inbox.repository;

import io.reactivex.functions.Consumer;

public interface NotificationContract {

    void fetchNotifications();

    void updateBookingNotificationCount(Integer readCount);

    void updateChatNotificationCount(Integer readCount);

    void subscribe(Consumer<Integer> consumer);

    void invalidateSession();

}
