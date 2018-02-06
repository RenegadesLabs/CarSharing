package com.cardee.domain.renter.usecase

import com.cardee.data_source.RenterCarsRepository
import com.cardee.domain.bookings.entity.BookCarState


class GetBookState {
    private val repository: RenterCarsRepository = RenterCarsRepository.getInstance()

    fun getBookState(): BookCarState {
        return repository.bookState
    }
}