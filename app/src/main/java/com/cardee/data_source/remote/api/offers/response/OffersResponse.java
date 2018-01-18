package com.cardee.data_source.remote.api.offers.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffersResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    OffersResponseBody mOffersResponseBody;

    public OffersResponseBody getOffersResponseBody() {
        return mOffersResponseBody;
    }

    public void setOffersResponseBody(OffersResponseBody offersResponseBody) {
        mOffersResponseBody = offersResponseBody;
    }
}
