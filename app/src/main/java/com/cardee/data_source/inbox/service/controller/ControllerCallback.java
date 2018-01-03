package com.cardee.data_source.inbox.service.controller;

import com.cardee.data_source.inbox.service.model.BaseNotification;

public interface ControllerCallback {

    void notifyUser(BaseNotification baseNotification);
}
