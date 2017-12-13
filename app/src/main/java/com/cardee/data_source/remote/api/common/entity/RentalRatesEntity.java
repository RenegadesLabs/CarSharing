package com.cardee.data_source.remote.api.common.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentalRatesEntity {

    @Expose
    @SerializedName("rate_first")
    private Double mRateFirst;

    @Expose
    @SerializedName("rate_second")
    private Double mRateSecond;

    @Expose
    @SerializedName("amnt_discount_first")
    private Integer mDiscountFirst;

    @Expose
    @SerializedName("amnt_discount_second")
    private Integer mDiscountSecond;

    @Expose
    @SerializedName("min_rental_duration")
    private Integer mMinRentalDuration;

    public RentalRatesEntity(Double rateFirst, Double rateSecond, Integer discountFirst,
                             Integer discountSecond, Integer minRentalDuration) {
        mRateFirst = rateFirst;
        mRateSecond = rateSecond;
        mDiscountFirst = discountFirst;
        mDiscountSecond = discountSecond;
        mMinRentalDuration = minRentalDuration;
    }

    public Double getRateFirst() {
        return mRateFirst;
    }

    public Double getRateSecond() {
        return mRateSecond;
    }

    public Integer getDiscountFirst() {
        return mDiscountFirst;
    }

    public Integer getDiscountSecond() {
        return mDiscountSecond;
    }

    public Integer getMinRentalDuration() {
        return mMinRentalDuration;
    }
}
