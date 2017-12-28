package com.cardee.data_source.inbox.remote.api.response;

import com.cardee.data_source.inbox.remote.api.model.entity.NotificationData;
import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private NotificationData mNotificationData;

    public NotificationResponse() {
    }

    public NotificationData getNotificationData() {
        return mNotificationData;
    }

    public void setNotificationData(NotificationData notificationData) {
        mNotificationData = notificationData;
    }
}
