package com.cardee.data_source.remote.api.offers.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffersResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    OfferResponseBody[] mOfferResponseBody;

    public OfferResponseBody[] getOffersResponseBody() {
        return mOfferResponseBody;
    }

    public void setOfferResponseBody(OfferResponseBody[] offerResponseBody) {
        mOfferResponseBody = offerResponseBody;
    }
}
