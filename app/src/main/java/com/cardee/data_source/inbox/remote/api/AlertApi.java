package com.cardee.data_source.inbox.remote.api;

import com.cardee.data_source.inbox.remote.api.request.AlertsRequest;
import com.cardee.data_source.inbox.remote.api.response.AlertListResponse;
import com.cardee.data_source.remote.api.NoDataResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlertApi {

    @GET("/api/dev/alerts/{attachment}")
    Single<AlertListResponse> getAlerts(@Path(value = "attachment") String attachment);

    @PUT("alerts/")
    Single<NoDataResponse> markAsRead(@Body AlertsRequest alerts);

}
