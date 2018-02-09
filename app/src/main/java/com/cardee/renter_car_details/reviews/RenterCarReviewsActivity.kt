package com.cardee.renter_car_details.reviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.cardee.R
import com.cardee.mvp.BaseView
import com.cardee.owner_car_details.view.OwnerCarReviewsFragment
import kotlinx.android.synthetic.main.activity_renter_car_reviews.*

class RenterCarReviewsActivity : AppCompatActivity(), BaseView {

    private var mCurrentToast: Toast? = null
    private var mCarId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renter_car_reviews)
        initToolBar()
        getIntentData()
        showFragment()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun getIntentData() {
        mCarId = intent.getIntExtra("carId", -1)
    }

    private fun showFragment() {
        val fragment = OwnerCarReviewsFragment.newInstance(mCarId)
        val mCurTransaction = supportFragmentManager.beginTransaction()
        mCurTransaction.add(reviewsContainer.id, fragment)
        mCurTransaction.commit()
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showMessage(message: String?) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
