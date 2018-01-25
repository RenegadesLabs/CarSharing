package com.cardee.data_source.remote.api.offers.response;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.offers.response.entity.OfferCarDetailsEntity;
import com.cardee.data_source.remote.api.offers.response.entity.OfferOrderDetails;
import com.cardee.data_source.remote.api.offers.response.entity.OfferOwnerEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferResponseBody extends ErrorResponseBody {

    @Expose
    @SerializedName("car_details")
    private OfferCarDetailsEntity mCarDetailEntity;

    @Expose
    @SerializedName("owner")
    private OfferOwnerEntity mOwnerEntity;

    @Expose
    @SerializedName("rating_cnt")
    private Integer mRatingCount;

    @Expose
    @SerializedName("rating")
    private Float mRating;

    @Expose
    @SerializedName("order_details")
    private OfferOrderDetails mOrderDetails;

    public OfferResponseBody() {
    }

    public OfferCarDetailsEntity getCarDetailEntity() {
        return mCarDetailEntity;
    }

    public void setCarDetailEntity(OfferCarDetailsEntity carDetailEntity) {
        mCarDetailEntity = carDetailEntity;
    }

    public OfferOwnerEntity getOwnerEntity() {
        return mOwnerEntity;
    }

    public void setOwnerEntity(OfferOwnerEntity ownerEntity) {
        mOwnerEntity = ownerEntity;
    }

    public Integer getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        mRatingCount = ratingCount;
    }

    public Float getRating() {
        return mRating;
    }

    public void setRating(Float rating) {
        mRating = rating;
    }

    public OfferOrderDetails getOrderDetails() {
        return mOrderDetails;
    }

    public void setOrderDetails(OfferOrderDetails orderDetails) {
        mOrderDetails = orderDetails;
    }
}
