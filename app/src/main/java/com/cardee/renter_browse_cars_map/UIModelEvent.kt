package com.cardee.renter_browse_cars_map

import android.view.View
import com.cardee.data_source.remote.api.offers.response.Offer

data class UIModelEvent(val eventType: String, val view: View, val model: OfferItem) {

    companion object {
        val EVENT_OFFER_MAP_CLICK = "event_offer_map_click"
        val EVENT_OFFER_LIST_CLICK = "event_offer_list_click"
        val EVENT_OFFER_FAVORITE_TOGGLE = "event_offer_favorite_toggle"
        val EVENT_OFFER_FILTER_CLICK = "event_offer_filter_click"
    }
}
