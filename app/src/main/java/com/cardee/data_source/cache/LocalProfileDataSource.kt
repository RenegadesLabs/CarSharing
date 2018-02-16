package com.cardee.data_source.cache

import com.cardee.data_source.ProfileDataSource
import com.cardee.domain.profile.entity.VerifyAccountState

object LocalProfileDataSource : ProfileDataSource {

    private var verifyAccState: VerifyAccountState? = null

    override fun getVerifyAccState(): VerifyAccountState {
        return verifyAccState ?: VerifyAccountState()
    }

    override fun saveVerifyAccState(state: VerifyAccountState) {
        verifyAccState = state
    }

}