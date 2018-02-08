package com.cardee.domain.renter.usecase

import com.cardee.data_source.RenterCarsRepository
import com.cardee.domain.bookings.entity.BookCarState

class SaveBookState {
    private val repository: RenterCarsRepository = RenterCarsRepository.getInstance()

    fun saveBookState(state: BookCarState) {
        repository.saveBookState(state)
    }
}
