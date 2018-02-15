package com.cardee.domain.account.entity

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean


data class VerifyAccountState(val statusStrings: Array<String> = arrayOf("Added", "Add"),
                              val particularsAdded: ObservableBoolean = ObservableBoolean(),
                              val identityAdded: ObservableBoolean = ObservableBoolean(),
                              val licenseAdded: ObservableBoolean = ObservableBoolean(),
                              val photoAdded: ObservableBoolean = ObservableBoolean(),
                              val creditAdded: ObservableBoolean = ObservableBoolean(),
                              val depositAdded: ObservableBoolean = ObservableBoolean(),
                              var name: String = "",
                              var identityCard: String = "",
                              var address: String = "",
                              var phone: String = "",
                              var birthDate: String = "",
                              var licenseDate: String = "") : BaseObservable()