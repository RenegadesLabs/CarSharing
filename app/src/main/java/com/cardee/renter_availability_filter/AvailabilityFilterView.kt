package com.cardee.renter_availability_filter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.cardee.R
import kotlinx.android.synthetic.main.view_availability_filter.view.*


class AvailabilityFilterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        availabilityPager.adapter = AvailabilityPagerAdapter(context)
        availabilityTabs.setupWithViewPager(availabilityPager)
    }

    class AvailabilityPagerAdapter(context: Context) : PagerAdapter() {

        val pages: Array<String>

        init {
            val hourlyTitle = context.getString(R.string.book_hourly)
            val dailyTitle = context.getString(R.string.book_daily)
            pages = arrayOf(hourlyTitle, dailyTitle)
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val page = View(container?.context)
            container?.addView(page)
            return page
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getPageTitle(position: Int): CharSequence {
            return pages[position]
        }

        override fun getCount(): Int {
            return pages.size
        }
    }
}
