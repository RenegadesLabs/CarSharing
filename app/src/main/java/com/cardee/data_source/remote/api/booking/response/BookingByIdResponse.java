package com.cardee.data_source.remote.api.booking.response;


import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.response.entity.BookingEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingByIdResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private BookingEntity mBooking;

    public BookingByIdResponse() {
    }

    public BookingEntity getBooking() {
        return mBooking;
    }

    public void setBooking(BookingEntity booking) {
        mBooking = booking;
    }
}
