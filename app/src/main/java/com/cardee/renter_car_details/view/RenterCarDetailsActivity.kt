package com.cardee.renter_car_details.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cardee.R
import com.cardee.renter_book_car.view.BookCarActivity
import com.cardee.renter_car_details.view.viewholder.RenterCarDetailsViewHolder
import kotlinx.android.synthetic.main.activity_renter_car_details.*
import kotlinx.android.synthetic.main.view_renter_book_car.*


class RenterCarDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCarId: Int? = null

    override fun onClick(p0: View?) {
        when (p0) {
            iv_renterCarDetailsToolbarShare -> {
            }
            iv_renterCarDetailsToolbarFavoritesImg -> {
            }
            b_bookCar -> {
                val intent = Intent(this, BookCarActivity::class.java)
                intent.putExtra("carId", mCarId)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renter_car_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        RenterCarDetailsViewHolder(this)
        b_bookCar.setOnClickListener(this)
        getData()
    }

    private fun getData() {
        mCarId = intent.getIntExtra("carId", -1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}