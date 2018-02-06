package com.cardee.data_source.remote.api.offers.response.entity

import com.cardee.data_source.remote.api.profile.response.entity.Author
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferCarReview(@Expose @SerializedName("comment") val review: String?,
                          @Expose @SerializedName("rating") val rating: Int?,
                          @Expose @SerializedName("profile") val profile: Author?)
