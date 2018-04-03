package com.cardee.custom.modal

import android.app.Dialog
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.Toast
import com.cardee.R
import kotlinx.android.synthetic.main.modal_dialog_get_picture.view.*


class SelectPictureFragment : BottomSheetDialogFragment() {

    private var mCurrentToast: Toast? = null
    private var listner: DialogOnClickListener? = null

    private val mCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    fun getInstance(): SelectPictureFragment {
        return SelectPictureFragment()
    }

    fun setListener(listener: DialogOnClickListener) {
        this.listner = listener
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val rootView = View.inflate(context, R.layout.modal_dialog_get_picture, null)
        dialog.setContentView(rootView)
        setClickListeners(rootView)
        val params = (rootView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mCallback)
        }
        val parent = rootView.parent
        if (parent != null) {
            (parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun setClickListeners(root: View) {
        root.camera.setOnClickListener {
            listner?.onCameraClicked()
            dismiss()
        }
        root.gallery.setOnClickListener {
            listner?.onGalleryClicked()
            dismiss()
        }
        root.cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showMessage(message: String) {
        mCurrentToast?.cancel()
        mCurrentToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        mCurrentToast?.show()
    }

    interface DialogOnClickListener {
        fun onCameraClicked()
        fun onGalleryClicked()
    }
}
