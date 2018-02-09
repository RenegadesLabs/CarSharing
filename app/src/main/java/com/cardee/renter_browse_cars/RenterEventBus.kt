package com.cardee.renter_browse_cars

class RenterEventBus {

    data class Event(val updated: Boolean = false)

    companion object {

        private val busInstance: RenterEventBus by lazy { RenterEventBus() }

        fun getInstance(): RenterEventBus {
            return busInstance
        }
    }

    @Volatile private var pendingEvent: Event? = null

    fun put(event: Event) {
        pendingEvent = event
    }

    fun get(): Event? {
        val event = pendingEvent
        pendingEvent = null
        return event
    }
}
