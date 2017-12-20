package com.cardee.data_source.remote.api.booking;


import com.cardee.data_source.remote.api.booking.response.BookingResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Bookings {

    @GET("bookings/owner")
    Call<BookingResponse> getOwnerBookings();

    @GET("bookings/renter")
    Call<BookingResponse> getRenterBookings();
}
