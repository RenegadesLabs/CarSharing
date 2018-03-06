package com.cardee.data_source.inbox.repository;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.ChatNotification;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface InboxContract {

    Flowable<List<Alert>> getLocalAlerts(String attachment);

    Single<List<Alert>> getRemoteAlerts(String attachment);

    Flowable<List<Chat>> getLocalChats(String attachment);

    Single<List<Chat>> getRemoteChats(String attachment);

    void fetchChatData(List<Chat> remoteChats);

    void fetchAlertData(List<Alert> remoteChats);


    Completable updateChat(ChatNotification chat);

    void addAlert(AlertNotification alert);

    Single markAsRead(List<Integer> alerts);

    void addNewChat(Chat chat);
}
