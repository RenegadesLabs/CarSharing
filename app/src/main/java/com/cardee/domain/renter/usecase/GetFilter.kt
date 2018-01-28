package com.cardee.domain.renter.usecase

import com.cardee.data_source.RenterCarsRepository
import com.cardee.domain.renter.entity.BrowseCarsFilter


class GetFilter {
    private val repository: RenterCarsRepository = RenterCarsRepository.getInstance()

    fun getFilter(): BrowseCarsFilter {
        return repository.filter
    }
}