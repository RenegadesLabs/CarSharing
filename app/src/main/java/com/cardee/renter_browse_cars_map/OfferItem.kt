package com.cardee.renter_browse_cars_map

import com.cardee.data_source.remote.api.offers.response.Offer

data class OfferItem(val id: Int?, val offer: Offer, var selected: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        when (other) {
            is OfferItem -> {
                val (targetId: Int?) = other
                if (targetId != null && id != null) {
                    return id.equals(targetId)
                }
                return false
            }
            else -> return false
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
