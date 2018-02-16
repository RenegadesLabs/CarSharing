package com.cardee.data_source

import com.cardee.data_source.cache.LocalProfileDataSource
import com.cardee.domain.profile.entity.VerifyAccountState


object ProfileRepository : ProfileDataSource {

    private val localDataSource: ProfileDataSource = LocalProfileDataSource

    override fun getVerifyAccState(): VerifyAccountState {
        return localDataSource.getVerifyAccState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
        localDataSource.saveVerifyAccState(state)
    }
}