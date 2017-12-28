package com.cardee.data_source.remote.api.booking;


import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.booking.request.ReviewAsOwner;
import com.cardee.data_source.remote.api.booking.request.ReviewAsRenter;
import com.cardee.data_source.remote.api.booking.response.BookingByIdResponse;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
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
    Single<NoDataResponse> sendReviewAsOwner(@Path("id") int id, @Body ReviewAsOwner reviewAsOwner);

    @PUT("bookings/{id}/rating")
    @Headers("Content-Type: application/json")
    Single<NoDataResponse> sendReviewAsRenter(@Path("id") int id, @Body ReviewAsRenter reviewAsRenter);
}
