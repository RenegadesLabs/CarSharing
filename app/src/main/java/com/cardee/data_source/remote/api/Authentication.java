package com.cardee.data_source.remote.api;


import com.cardee.data_source.remote.api.model.request.LoginRequest;
import com.cardee.data_source.remote.api.model.request.PushRequest;
import com.cardee.data_source.remote.api.model.request.SignUpRequest;
import com.cardee.data_source.remote.api.model.request.SocialLoginRequest;
import com.cardee.data_source.remote.api.model.request.VerifyPasswordRequest;
import com.cardee.data_source.remote.api.model.response.BaseAuthResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Authentication {

    @POST("/auth/login")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> login(@Body LoginRequest request);

    @POST("/auth/login_social")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> loginSocial(@Body SocialLoginRequest request);

    @POST("/auth/signup")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> signUp(@Body SignUpRequest request);

    @POST("/auth/verify_password")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> verifyPassword(@Body VerifyPasswordRequest request);

    @GET("/auth/logout")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> logout(@Header("Authentication") String authenticationToken);

    @POST("/auth/push")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> push(@Body PushRequest request);
}
