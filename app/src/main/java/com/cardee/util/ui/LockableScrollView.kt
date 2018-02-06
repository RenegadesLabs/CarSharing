package com.cardee.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class LockableScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ScrollView(context, attrs, defStyleAttr), LockableView {

    private var locked = false

    fun isLocked(): Boolean {
        return locked
    }

    override fun lock() {
        locked = true
    }

    override fun unlock() {
        locked = false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!locked) return super.onTouchEvent(ev)
                return !locked
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (locked) {
            return false
        }
        return super.onInterceptTouchEvent(ev)
    }
}