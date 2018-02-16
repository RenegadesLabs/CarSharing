package com.cardee.data_source

import com.cardee.domain.profile.entity.VerifyAccountState


interface ProfileDataSource {

    fun getVerifyAccState(): VerifyAccountState

    fun saveVerifyAccState(state: VerifyAccountState)

}