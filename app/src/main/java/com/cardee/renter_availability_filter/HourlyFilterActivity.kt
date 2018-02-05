package com.cardee.renter_availability_filter

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.cardee.R
import kotlinx.android.synthetic.main.activity_simple.*


class HourlyFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        if (supportActionBar == null) {
            setSupportActionBar(toolbar)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = null
        }
        val contentView: HourlyAvailabilityView =
                layoutInflater.inflate(R.layout.view_hourly_availability, content, false)
                        as HourlyAvailabilityView
        contentView.addView(contentView)
        contentView.setPresenter(AvailabilityFilterPresenter(this))
        contentView.configureSingleAction()
        contentView.setCallback { saved ->
            if (saved) {
                setResult(Activity.RESULT_OK)
            }
            finish()
        }
        toolbarTitle.text = getString(R.string.book_hourly)
        toolbarAction.setOnClickListener { contentView.reset() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}