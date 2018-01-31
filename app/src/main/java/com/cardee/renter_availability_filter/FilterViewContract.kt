package com.cardee.renter_availability_filter


interface FilterViewContract {

    fun setPresenter(presenter: AvailabilityFilterPresenter)

    fun setCallback(callback: (Boolean) -> Unit)

    fun saveFilter(doOnSave: () -> Unit = {})

    fun resetFilter(doOnReset: () -> Unit = {})

}