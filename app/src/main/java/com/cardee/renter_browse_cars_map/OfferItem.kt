package com.cardee.renter_browse_cars_map

import com.cardee.domain.renter.entity.OfferCar

data class OfferItem(val id: Int?, val offer: OfferCar, var selected: Boolean = false) {

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
