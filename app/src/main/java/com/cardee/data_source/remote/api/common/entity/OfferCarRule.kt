package com.cardee.data_source.remote.api.common.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferCarRule(@Expose @SerializedName("rule_id") val ruleId: Int?,
                        @Expose @SerializedName("rule_name") val ruleName: String?,
                        @Expose @SerializedName("is_active_rule") val activeRule: Boolean?)
