package com.cardee.renter_car_details.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.renter_car_details.view.viewholder.RenterCarDetailsViewHolder
import kotlinx.android.synthetic.main.activity_renter_car_details.*


class RenterCarDetailsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            iv_renterCarDetailsToolbarShare -> {
            }
            iv_renterCarDetailsToolbarFavoritesImg -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_renter_car_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        RenterCarDetailsViewHolder(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}