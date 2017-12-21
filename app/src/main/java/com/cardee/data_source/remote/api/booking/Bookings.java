package com.cardee.data_source.remote.api.booking;


import com.cardee.data_source.remote.api.booking.response.BookingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Bookings {

    @GET("bookings/owner")
    Call<BookingResponse> getOwnerBookings(@Query("state") String filter, @Query("order") String sort);

    @GET("bookings/renter")
    Call<BookingResponse> getRenterBookings(@Query("state") String filter, @Query("order") String sort);
}
