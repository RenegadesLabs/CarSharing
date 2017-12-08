package com.cardee.data_source.remote.api.inbox.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @Expose
    @SerializedName("booking_id")
    private Integer mBookingId; // this ID is use to create like a chatID to create new conversation
    @Expose
    @SerializedName("time_end")
    private String mTimeEnd;
    @Expose
    @SerializedName("time_begin")
    private String mTimeBegin;

    public Booking() {
    }

    public Integer getBookingId() {
        return mBookingId;
    }

    public void setBookingId(Integer bookingId) {
        mBookingId = bookingId;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeBegin() {
        return mTimeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        mTimeBegin = timeBegin;
    }
}
