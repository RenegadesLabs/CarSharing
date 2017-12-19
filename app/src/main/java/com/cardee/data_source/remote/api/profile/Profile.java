package com.cardee.data_source.remote.api.profile;


import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.profile.request.ChangeEmailRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNameRequest;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.data_source.remote.api.profile.request.ChangePhoneRequest;
import com.cardee.data_source.remote.api.profile.request.PassChangeRequest;
import com.cardee.data_source.remote.api.profile.response.CarsResponse;
import com.cardee.data_source.remote.api.profile.response.OwnerProfileResponse;
import com.cardee.data_source.remote.api.profile.response.RenterProfileResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface Profile {

    @GET("profiles/owner")
    @Headers("Content-Type: application/json")
    Observable<OwnerProfileResponse> loadOwnerProfile();

    @GET("profiles/owner/cars")
    @Headers("Content-Type: application/json")
    Observable<CarsResponse> loadOwnersCarList();

    @GET("profiles/renter")
    @Headers("Content-Type: application/json")
    Observable<RenterProfileResponse> loadRenterProfile();

    //TODO: implement
    void uploadProfilePhoto();


    @PUT("profiles/owner/note")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> updateOwnerNote(@Body ChangeNoteRequest changeNoteRequest);

    @PUT("profiles/renter/note")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> updateRenterNote(@Body ChangeNoteRequest changeNoteRequest);

    @PUT("profiles/settings")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> updateNotificationSettings();

    @PUT("profiles/password")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> changePassword(@Body PassChangeRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> changeName(@Body ChangeNameRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> changeEmail(@Body ChangeEmailRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Observable<NoDataResponse> changePhone(@Body ChangePhoneRequest request);
}
