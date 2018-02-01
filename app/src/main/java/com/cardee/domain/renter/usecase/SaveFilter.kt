package com.cardee.domain.renter.usecase

import com.cardee.data_source.RenterCarsRepository
import com.cardee.domain.renter.entity.BrowseCarsFilter


class SaveFilter {
    private val repository = RenterCarsRepository.getInstance()

    fun saveFilter(filter: BrowseCarsFilter) {
        repository.saveFilter(filter)
    }
}