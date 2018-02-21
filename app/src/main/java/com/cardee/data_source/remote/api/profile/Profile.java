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

import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Profile {

    @GET("profiles/owner")
    @Headers("Content-Type: application/json")
    Single<OwnerProfileResponse> loadOwnerProfile();

    @GET("profiles/owner/{id}")
    @Headers("Content-Type: application/json")
    Single<OwnerProfileResponse> getOwnerProfileById(@Path("id") int id);

    @GET("profiles/owner/cars")
    @Headers("Content-Type: application/json")
    Call<CarsResponse> loadOwnersCarList();

    @GET("profiles/renter")
    @Headers("Content-Type: application/json")
    Single<RenterProfileResponse> loadRenterProfile();

    @GET("profiles/renter/{id}")
    @Headers("Content-Type: application/json")
    Single<RenterProfileResponse> getRenterById(@Path("id") int id);

    //TODO: implement
    void uploadProfilePhoto();


    @PUT("profiles/owner/note")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> updateOwnerNote(@Body ChangeNoteRequest changeNoteRequest);

    @PUT("profiles/renter/note")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> updateRenterNote(@Body ChangeNoteRequest changeNoteRequest);

    @PUT("profiles/settings")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> updateNotificationSettings();

    @PUT("profiles/password")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> changePassword(@Body PassChangeRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> changeName(@Body ChangeNameRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> changeEmail(@Body ChangeEmailRequest request);

    @PUT("profiles/details")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> changePhone(@Body ChangePhoneRequest request);

    @Multipart
    @POST("profiles/verify/identity")
    Maybe<NoDataResponse> uploadIdentityPhoto(@Part MultipartBody.Part front, @Part MultipartBody.Part back);

    @Multipart
    @POST("profiles/verify/licence")
    Maybe<NoDataResponse> uploadLicensePhoto(@Part MultipartBody.Part front, @Part MultipartBody.Part back);

    @Multipart
    @POST("profiles/verify/photo")
    Maybe<NoDataResponse> uploadProfilePhoto(@Part MultipartBody.Part request);
}
