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
        val availabilityAdapter = AvailabilityPagerAdapter(context, exitSubject)
        availabilityPager.adapter = availabilityAdapter
        availabilityAdapter.presenter.getFilter { filter ->
            filter.bookingHourly?.let { hourly ->
                if (!hourly) {
                    availabilityPager.setCurrentItem(1, false)
                }
            }
        }
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
        val presenter: AvailabilityFilterPresenter

        init {
            val hourlyTitle = context.getString(R.string.book_hourly)
            val dailyTitle = context.getString(R.string.book_daily)
            pages = arrayOf(hourlyTitle, dailyTitle)
            inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            presenter = AvailabilityFilterPresenter(context)
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val page: FilterViewContract? = when (position) {
                0 -> inflator.inflate(R.layout.view_hourly_availability, container, false)
                        as HourlyAvailabilityView
                1 -> inflator.inflate(R.layout.view_daily_availability, container, false)
                        as DailyAvailabilityView
                else -> null
            }
            page?.run {
                setCallback { exitSubject.onNext(it) }
                setPresenter(presenter)
            }
            val view = page as View
            container?.addView(view)
            return view
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
