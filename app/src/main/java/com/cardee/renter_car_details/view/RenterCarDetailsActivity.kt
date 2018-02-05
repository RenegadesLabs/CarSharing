package com.cardee.renter_car_details.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.cardee.R
import kotlinx.android.synthetic.main.activity_renter_car_details.*


class RenterCarDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_renter_car_details)
    }

}