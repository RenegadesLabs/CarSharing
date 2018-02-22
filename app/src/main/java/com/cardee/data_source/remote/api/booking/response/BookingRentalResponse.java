package com.cardee.data_source.remote.api.booking.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.response.entity.BookingRentalTerms;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BookingRentalResponse extends BaseResponse {

    @Expose
    @SerializedName("dataList")
    private BookingRentalTerms rentalTerms;

    public BookingRentalResponse(){

    }

    public BookingRentalTerms getRentalTerms() {
        return rentalTerms;
    }

    public void setRentalTerms(BookingRentalTerms rentalTerms) {
        this.rentalTerms = rentalTerms;
    }
}
