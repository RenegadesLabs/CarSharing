package com.cardee.auth.register.view

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cardee.R
import kotlinx.android.synthetic.main.fragment_register_final.*

class RegisterFinalStepFragment : Fragment() {

    companion object {
        const val TAG = "RegisterFinalStepFragment"
        private const val REG_USER_PIC = "reg_user_pic"
        private const val REG_USER_NAME = "reg_user_name"

        fun newInstance(name: String): RegisterFinalStepFragment {
            return RegisterFinalStepFragment().apply {
                arguments = Bundle().apply {
                    putString(REG_USER_NAME, name)
                }
            }
        }
    }

    private var mViewListener: RegisterView? = null

    var regUserPhoto: AppCompatImageView? = null
    var regTakePhotoView: FrameLayout? = null
    var regUserName: TextInputEditText? = null
    var regAsRenterButton: AppCompatButton? = null
    var regAsOwnerButton: AppCompatButton? = null

    private var mPictureByteArray: ByteArray? = null
    private var mNameText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_register_final, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regUserPhoto = iv_registerUserPhoto
        regTakePhotoView = fl_takePhoto
        regUserName = tiet_registerName
        regAsRenterButton = b_registerAsRenter
        regAsOwnerButton = b_registerAsOwner
        setButtonsActivated(false)
        setListeners()
        setUserName()

    }

    private fun setUserName() {
        mNameText = arguments.getString(REG_USER_NAME)
        tv_almostDone.text = resources.getString(R.string.signup_acc_created_template)
                .format(mNameText ?: "")
    }

    private fun setListeners() {
//        b_registerBackToFirstStep.setOnClickListener {
//            if (mViewListener != null)
//                mViewListener!!.onBackToFirstStep()
//        }
//        iv_registerUserPhoto.setOnClickListener {
//            if (mViewListener != null)
//                mViewListener!!.onTakePhoto()
//        }
//        b_registerAsOwner.setOnClickListener {
//            if (mViewListener != null)
//                mViewListener!!.onSignUpAsOwner(regUserName!!.text.toString(), convertByteArrayToFile())
//        }
//        b_registerAsRenter.setOnClickListener {
//            if (mViewListener != null)
//                mViewListener!!.onSignUpAsRenter(regUserName!!.text.toString(), convertByteArrayToFile())
//        }
//        tiet_registerName.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {}
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if ((text?.length ?: 0) > 0) {
//                    setButtonsActivated(true)
//                    return
//                }
//                setButtonsActivated(false)
//            }
//        })

        loginAsRenter.setOnClickListener {
            mViewListener?.onSignUpAsRenter(mNameText, null)
        }
        loginAsOwner.setOnClickListener {
            mViewListener?.onSignUpAsOwner(mNameText, null)
        }
    }

    fun setViewListener(listener: RegisterView) {
        mViewListener = listener
    }

    private fun setButtonsActivated(activated: Boolean) {
        if (activated) {
            regAsOwnerButton!!.alpha = 1f
            regAsOwnerButton!!.isClickable = true
            regAsRenterButton!!.alpha = 1f
            regAsRenterButton!!.isClickable = true
            return
        }
        regAsOwnerButton!!.alpha = .7f
        regAsOwnerButton!!.isClickable = false
        regAsRenterButton!!.alpha = .7f
        regAsRenterButton!!.isClickable = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPictureByteArray = null
    }

//    fun setUserPhoto(bmp: Bitmap) {
//        val stream = ByteArrayOutputStream()
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        mPictureByteArray = stream.toByteArray()
//        setImage(mPictureByteArray)
//    }
//
//    private fun setImage(pictureByteArray: ByteArray?) {
//        Glide.with(activity)
//                .load(pictureByteArray)
//                .placeholder(R.drawable.placeholder_user_img)
//                .override(300, 300)
//                .centerCrop()
//                .transform(CircleTransform(activity))
//                .into(regUserPhoto!!)
//
//        if (!regUserName!!.text.toString().isEmpty()) {
//            setButtonsActivated(true)
//        }
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        if (savedInstanceState != null) {
//            mPictureByteArray = savedInstanceState.getByteArray(REG_USER_PIC)
//            if (mPictureByteArray != null) {
//                setImage(mPictureByteArray)
//            }
//
//            mNameText = savedInstanceState.getString(REG_USER_NAME)
//            if (mNameText != null && !mNameText!!.isEmpty()) {
//                regUserName!!.setText(mNameText)
//            }
//        }
//    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        if (mPictureByteArray != null) {
//            outState!!.putByteArray(REG_USER_PIC, mPictureByteArray)
//        }
//
//        mNameText = regUserName!!.text.toString()
//        if (!mNameText!!.isEmpty()) {
//            outState!!.putString(REG_USER_NAME, mNameText)
//        }
//    }

//    private fun convertByteArrayToFile(): File? {
//        if (mPictureByteArray != null) {
//            val fos: FileOutputStream
//            var f: File? = null
//            try {
//                f = File(activity.cacheDir, "picture")
//                fos = FileOutputStream(f)
//                fos.write(mPictureByteArray!!)
//                fos.flush()
//                fos.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            return f
//        }
//        return null
//    }
}
