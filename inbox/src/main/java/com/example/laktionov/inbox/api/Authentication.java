package com.example.laktionov.inbox.api;

public interface Authentication {

    @POST("auth/push")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> push(@Body PushRequest request);
}
