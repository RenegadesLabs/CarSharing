package com.cardee.data_source.inbox.service.controller;

import com.google.firebase.messaging.RemoteMessage;

public interface RepositoryController {

    void updateInbox(RemoteMessage remoteMessage, ControllerCallback callback);
}
