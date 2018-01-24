package com.cardee.renter_browse_cars_map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_browse_cars_map.*

class BrowseCarsMapActivity(private var delegate: LocationClient = LocationClientImpl()) :
        LocationClient by delegate, AppCompatActivity(), OnMapReadyCallback, BrowseCarsContract.View<OfferItem> {

    private lateinit var adapter: OffersAdapter
    private lateinit var presenter: BrowseCarsPresenter
    private val eventProducer: PublishSubject<UIModelEvent> = PublishSubject.create()
    private var disposable: Disposable = Disposables.empty()
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_cars_map)
        init(this)
        supportActionBar ?: setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = null
        }
        map.let {
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }
        presenter = BrowseCarsPresenter(this)
        adapter = OffersAdapter(this)
        adapter.subscribe(presenter)
        disposable = eventProducer.subscribe(presenter)
        carsList.let {
            it.adapter = adapter
            it.itemAnimator = DefaultItemAnimator()
            it.layoutManager = LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false)
            adapter.initScrollingBehaviour(it)
        }
        presenter.loadAll()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            val uiSettings = it.uiSettings
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isIndoorLevelPickerEnabled = false
            adapter.initMapManager(this, it) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        connect()
        map.onResume()
    }

    override fun showProgress(show: Boolean) {
        loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun bind(offers: List<OfferItem>) {
        adapter.setItems(offers)
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        map.onDestroy()
        adapter.unsubscribe()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}
