package com.cardee.data_source.remote.api.profile.response.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class VerifyState(@Expose @SerializedName("is_identity_card") var identityCardVerified: Boolean = false,
                       @Expose @SerializedName("is_particulars") var particularsVerified: Boolean = false,
                       @Expose @SerializedName("is_driver_licence") var driverLicenceVerified: Boolean = false,
                       @Expose @SerializedName("is_driver_photo") var driverPhotoVerified: Boolean = false,
                       @Expose @SerializedName("is_deposit") var depositVerified: Boolean = false,
                       @Expose @SerializedName("is_credit_card") var creditCardVerified: Boolean = false)