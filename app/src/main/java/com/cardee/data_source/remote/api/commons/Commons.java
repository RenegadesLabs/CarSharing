package com.cardee.data_source.remote.api.commons;


import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.commons.request.FeedbackRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Commons {

    @POST("/commons/feedback")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> sendFeedback(@Body FeedbackRequest request);
}
