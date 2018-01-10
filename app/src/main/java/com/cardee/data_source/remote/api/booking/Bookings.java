package com.cardee.data_source.remote.api.booking;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.booking.request.ReviewAsOwner;
import com.cardee.data_source.remote.api.booking.response.BookingByIdResponse;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;
import com.cardee.data_source.remote.api.booking.response.entity.Checklist;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;

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
import retrofit2.http.Query;

public interface Bookings {

    @GET("bookings/owner")
    Call<BookingResponse> getOwnerBookings(@Query("state") String filter, @Query("order") String sort);

    @GET("bookings/renter")
    Call<BookingResponse> getRenterBookings(@Query("state") String filter, @Query("order") String sort);

    @GET("bookings/{id}")
    Single<BookingByIdResponse> getBookingById(@Path("id") int id);

    @PUT("bookings/{id}/rating")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> sendBookingReview(@Path("id") int id, @Body ReviewAsOwner reviewAsOwner);

    @GET("bookings/{id}/checklist")
    Call<BaseResponse> getChecklist(@Path("id") int id);

    @POST("bookings/{id}/checklist")
    @Headers("Content-Type: application/json")
    Call<BaseResponse> saveChecklist(@Path("id") int id, @Body Checklist checklist);

    @Multipart
    @PUT("bookings/{id}/checklist")
    Call<UploadImageResponse> addPhoto(@Path("id") int id, @Part MultipartBody.Part picture);

}
