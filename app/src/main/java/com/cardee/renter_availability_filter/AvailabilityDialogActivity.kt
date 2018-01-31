package com.cardee.renter_availability_filter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import com.cardee.R
import kotlinx.android.synthetic.main.activity_dialog_availability.*


class AvailabilityDialogActivity : AppCompatActivity() {

    var availabilityView: AvailabilityFilterView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_availability)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val content = inflater.inflate(R.layout.view_availability_filter, backgroundView, false)
        content.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        availabilityView = content as AvailabilityFilterView
        availabilityView?.subscribe { it -> proceedExit(it) }
        backgroundView.addView(content)
        dim.setOnClickListener { _ -> onBackPressed() }
        prepareWindow()
    }

    private fun prepareWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        window.attributes.dimAmount = 0.6f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onDestroy() {
        super.onDestroy()
        availabilityView?.unsubscribe()
    }

    private fun proceedExit(saved: Boolean) {
        if (saved) {
            setResult(Activity.RESULT_OK)
        }
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.exit_down)
    }
}