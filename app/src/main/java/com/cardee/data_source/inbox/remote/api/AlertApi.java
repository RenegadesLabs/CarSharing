package com.cardee.data_source.inbox.remote.api;

import com.cardee.data_source.inbox.remote.api.response.AlertListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlertApi {

    @GET("/api/dev/alerts/{attachment}")
    Single<AlertListResponse> getAlerts(@Path(value = "attachment") String attachment);

}
