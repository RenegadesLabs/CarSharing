package com.cardee.renter_browse_cars_map

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cardee.R
import com.cardee.renter_browse_cars.filter.view.FilterActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_browse_cars_map.*

class BrowseCarsMapActivity(private var delegate: LocationClient = LocationClientImpl()) :
        LocationClient by delegate, AppCompatActivity(), OnMapReadyCallback, BrowseCarsContract.View<OfferItem> {

    companion object {
        private val FILTER_REQUEST_CODE: Int = 112
    }

    private lateinit var adapter: OffersAdapter
    private lateinit var presenter: BrowseCarsPresenter
    private var favNormalIcon: Drawable? = null
    private var favSelectedIcon: Drawable? = null
    private val eventProducer: PublishSubject<UIModelEvent> = PublishSubject.create()
    private var disposable: Disposable = Disposables.empty()
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_cars_map)
        init(this)
        initResources()
        supportActionBar ?: setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = null
        }
        btnFilter.setOnClickListener { openFilter() }
        btnFavorites.setOnClickListener { presenter.toggleFavorite() }
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
        presenter.load()
    }

    private fun initResources() {
        favNormalIcon = VectorDrawableCompat.create(resources, R.drawable.ic_favorites_toolbar, null)
        favSelectedIcon = VectorDrawableCompat.create(resources, R.drawable.ic_favorites_toolbar_filled, null)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            val uiSettings = it.uiSettings
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isIndoorLevelPickerEnabled = false
            uiSettings.isMapToolbarEnabled = false
            adapter.initMapContent(this, it)
        }
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

    override fun openFilter() {
        val intent = Intent(this, FilterActivity::class.java)
        startActivityForResult(intent, FILTER_REQUEST_CODE)
    }

    override fun toggleFavorites(selected: Boolean) {
        if (selected) {
            btnFavorites.setImageDrawable(favSelectedIcon)
        } else {
            btnFavorites.setImageDrawable(favNormalIcon)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILTER_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    presenter.load()
                }
            }
        }
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
        showEmptyMessage(offers.isEmpty())
        adapter.setItems(offers)
    }

    private fun showEmptyMessage(empty: Boolean) {
        if (empty) {
            msgTextView.text = getString(R.string.no_offers_found)
        }
        msgTextView.visibility = if (empty) View.VISIBLE else View.GONE
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
