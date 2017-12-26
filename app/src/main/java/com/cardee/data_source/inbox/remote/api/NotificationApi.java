package com.cardee.data_source.inbox.remote.api;

import com.cardee.data_source.inbox.remote.api.response.NotificationResponse;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface NotificationApi {

    @GET("/api/dev/alerts")
    @Headers("Content-Type: application/json")
    Maybe<NotificationResponse> getNotificationCount();
}
