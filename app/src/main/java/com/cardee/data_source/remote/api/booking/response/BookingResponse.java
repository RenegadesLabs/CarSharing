package com.cardee.data_source.remote.api.booking.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;

import java.util.List;

public class BookingResponse extends BaseResponse {

    private List<BookingEntity> bookings;

    private ErrorResponseBody errors;

    public BookingResponse() {

    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public ErrorResponseBody getErrors() {
        return errors;
    }

    public void setErrors(ErrorResponseBody errors) {
        this.errors = errors;
    }
}
