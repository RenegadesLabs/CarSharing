package com.cardee.data_source.inbox.service.controller;

import com.cardee.data_source.inbox.service.model.Notification;

public interface ControllerCallback {

    void notifyUser(Notification notification);
}
