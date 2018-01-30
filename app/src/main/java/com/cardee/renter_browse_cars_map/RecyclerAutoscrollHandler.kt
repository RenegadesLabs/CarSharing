package com.cardee.renter_browse_cars_map

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject


class RecyclerAutoscrollHandler(recycle: RecyclerView) : RecyclerView.OnScrollListener() {

    private val layoutManager: LinearLayoutManager
    private val subject: PublishSubject<Int> = PublishSubject.create()
    private var disposable: Disposable = Disposables.empty()
    private var autoscrolling = false
    private var scrollState: Int = 0

    init {
        recycle.addOnScrollListener(this)
        when (recycle.layoutManager) {
            is LinearLayoutManager -> layoutManager = recycle.layoutManager as LinearLayoutManager
            else -> throw IllegalStateException("RecyclerView should have LinearLayoutManager")
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (autoscrolling) {
                autoscrolling = false
                return
            }
            val firstPos = layoutManager.findFirstVisibleItemPosition()
            recyclerView?.let {
                val first = it.findViewHolderForAdapterPosition(firstPos).itemView
                if (Math.abs(first.left) > first.right) {
                    recyclerView.smoothScrollBy(first.right, 0)
                    subject.onNext(firstPos + 1)
                } else {
                    recyclerView.smoothScrollToPosition(firstPos)
                    subject.onNext(firstPos)
                }
                autoscrolling = true
            }
        }
        scrollState = newState
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }

    fun subscribe(consumer: (position: Int) -> Unit) {
        disposable = subject.subscribe(consumer)
    }

    fun unsubscribe() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}