package com.cardee.data_source.remote.api.profile.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class UploadParticularsRequest(@Expose @SerializedName("name") var name: String = "",
                                    @Expose @SerializedName("phone") var phone: String = "",
                                    @Expose @SerializedName("date_birth") var dateBirth: String = "",
                                    @Expose @SerializedName("address") var address: String = "",
                                    @Expose @SerializedName("passport") var passport: String = "",
                                    @Expose @SerializedName("license_effective_date") var licenseEffectiveDate: String = "")