package com.cardee.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout


class TouchWrapper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private var lockable: LockableView? = null

    fun disableOnTouch(lockable: LockableView) {
        this.lockable = lockable
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lockable?.lock()
                return true
            }
            MotionEvent.ACTION_UP -> {
                lockable?.unlock()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}