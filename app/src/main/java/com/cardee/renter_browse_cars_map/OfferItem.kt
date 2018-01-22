package com.cardee.renter_browse_cars_map

import com.cardee.data_source.remote.api.offers.response.Offer

data class OfferItem(val offer: Offer, var selected: Boolean = false)
