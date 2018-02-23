package com.cardee.data_source.remote.api.profile.response

import com.cardee.data_source.remote.api.BaseResponse
import com.cardee.data_source.remote.api.profile.response.entity.VerifyState
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class VerifyResponse(@Expose
                          @SerializedName("data")
                          var verifyState: VerifyState) : BaseResponse()