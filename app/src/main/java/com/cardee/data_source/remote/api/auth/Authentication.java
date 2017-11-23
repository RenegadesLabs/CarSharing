package com.cardee.data_source.remote.api.auth;


import com.cardee.data_source.remote.api.auth.request.CheckUniqueLoginRequest;
import com.cardee.data_source.remote.api.auth.request.ForgotPassRequest;
import com.cardee.data_source.remote.api.auth.request.LoginRequest;
import com.cardee.data_source.remote.api.auth.request.PushRequest;
import com.cardee.data_source.remote.api.auth.request.SignUpRequest;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.data_source.remote.api.auth.request.VerifyPasswordRequest;
import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;
import com.cardee.data_source.remote.api.auth.response.SocialAuthResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface Authentication {

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> login(@Body LoginRequest request);

    @POST("auth/login_social")
    @Headers("Content-Type: application/json")
    Observable<SocialAuthResponse> loginSocial(@Body SocialLoginRequest request);

    @POST("auth/validate")
    @Headers("Content-Type: application/json")
    /*Observable<BaseAuthResponse>*/Call<BaseAuthResponse> checkUniqueLogin(@Body CheckUniqueLoginRequest request);

    @POST("auth/signup")
    @Headers("Content-Type: application/json")
    /*Observable*/Call<BaseAuthResponse> signUp(@Body SignUpRequest request);

    @Multipart
    @PUT("profiles/picture")
    Call<ResponseBody> setProfilePicture(@Part MultipartBody.Part picture);

    @POST("auth/forgot_password")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> forgotPassword(@Body ForgotPassRequest request);

    @POST("auth/verify_password")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> verifyPassword(@Body VerifyPasswordRequest request);

    @GET("auth/logout")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> logout(@Header("Authentication") String authenticationToken);

    @POST("auth/push")
    @Headers("Content-Type: application/json")
    Observable<BaseAuthResponse> push(@Body PushRequest request);
}
