package com.cardee.domain.profile.usecase

import com.cardee.data_source.ProfileRepository
import com.cardee.domain.profile.entity.VerifyAccountState


class GetVerifyAccState {

    private val repository = ProfileRepository

    fun getVerifyState(): VerifyAccountState {
        return repository.getVerifyAccState()
    }
}