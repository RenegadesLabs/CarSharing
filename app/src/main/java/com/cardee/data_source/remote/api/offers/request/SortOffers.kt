package com.cardee.data_source.remote.api.offers.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SortOffers(@Expose
                 @SerializedName("order_by")
                 val orderName: String?)
