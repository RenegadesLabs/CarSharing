package com.cardee.domain.profile.usecase

import com.cardee.data_source.ProfileRepository
import com.cardee.domain.profile.entity.VerifyAccountState


class SaveVerifyAccState {

    private val repository = ProfileRepository

    fun saveVerifyState(state: VerifyAccountState) {
        repository.saveVerifyAccState(state)
    }
}