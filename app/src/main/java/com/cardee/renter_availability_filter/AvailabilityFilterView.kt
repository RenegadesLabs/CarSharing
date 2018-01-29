package com.cardee.renter_availability_filter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.cardee.R
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_availability_filter.view.*


class AvailabilityFilterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    private val exitSubject: PublishSubject<Boolean> = PublishSubject.create()
    private var disposable: Disposable = Disposables.empty()

    override fun onFinishInflate() {
        super.onFinishInflate()
        availabilityPager.adapter = AvailabilityPagerAdapter(context, exitSubject)
        availabilityTabs.setupWithViewPager(availabilityPager)
    }

    fun subscribe(consumer: (Boolean) -> Unit) {
        disposable = exitSubject.subscribe(consumer)
    }

    fun unsubscribe() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    class AvailabilityPagerAdapter(context: Context, val exitSubject: PublishSubject<Boolean>) : PagerAdapter() {

        val pages: Array<String>
        val inflator: LayoutInflater

        init {
            val hourlyTitle = context.getString(R.string.book_hourly)
            val dailyTitle = context.getString(R.string.book_daily)
            pages = arrayOf(hourlyTitle, dailyTitle)
            inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val page: View
            when (position) {
                0 -> {
                    page = inflator.inflate(R.layout.view_hourly_availability, container, false)
                    initHourlyCallback(page as HourlyAvailabilityView)
                }
                1 -> {
                    page = inflator.inflate(R.layout.view_daily_availability, container, false)
                    initDailyCallback(page as DailyAvailabilityView)
                }
                else -> page = View(container?.context)
            }
            container?.addView(page)
            return page
        }

        private fun initHourlyCallback(view: HourlyAvailabilityView) {
            view.setFinishCallback { saved -> exitSubject.onNext(saved) }
        }

        private fun initDailyCallback(view: DailyAvailabilityView) {
            view.setFinishCallback { saved -> exitSubject.onNext(saved) }
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
