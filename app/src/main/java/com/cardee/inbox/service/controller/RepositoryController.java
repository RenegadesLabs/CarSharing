package com.cardee.inbox.service.controller;

import com.google.firebase.messaging.RemoteMessage;

public interface RepositoryController {

    void updateInbox(RemoteMessage remoteMessage);
}
