package com.cardee.auth.register.view

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cardee.R
import kotlinx.android.synthetic.main.fragment_register_first.*

class RegisterFirstStepFragment : Fragment() {

    companion object {
        val TAG = "RegisterFirstStepFragment"
    }

    val passRegex: Regex = Regex("^(?=.*[0-9])(?=.*[a-z,A-Z])(?=\\S+\$).{8,}\$")

    var regEmailEdit: AppCompatEditText? = null
    var regPassEdit: AppCompatEditText? = null
    var regTermsOfServiceTV: TextView? = null

    private var mViewListener: RegisterView? = null

    private val isFieldsNotEmpty: Boolean
        get() {
            val err = resources.getString(R.string.email_pass_empty_error)
            return when {
                et_nameRegister.text.toString().isBlank() -> {
                    et_nameRegister.error = err
                    false
                }
                regEmailEdit?.text.toString().isBlank() -> {
                    regEmailEdit?.error = err
                    false
                }
                regPassEdit?.text.toString().isBlank() -> {
                    l_registerPassword.isPasswordVisibilityToggleEnabled = false
                    regPassEdit?.error = err
                    false
                }
                else -> true
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_register_first, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regEmailEdit = et_emailRegister
        regPassEdit = et_passwordRegister
        regTermsOfServiceTV = tv_registerTermsOfService

        setTermsOfServiceText()
        initEditTexts()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        b_registerGoToLogin.setOnClickListener {
            mViewListener?.onLogin()
        }

        b_registerSignup.setOnClickListener {
            if (mViewListener != null && isFieldsNotEmpty)
                if (checkFields()) {
                    mViewListener?.onSignUp(regEmailEdit?.text.toString(), regPassEdit?.text.toString())
                }
        }

        b_registerFacebook.setOnClickListener {
            mViewListener?.onFacebook()
        }

        b_registerGoogle.setOnClickListener {
            mViewListener?.onGoogle()
        }
    }

    private fun checkFields(): Boolean {
        return when {
            regPassEdit?.text.toString().matches(passRegex).not() -> {
                l_registerPassword.isPasswordVisibilityToggleEnabled = false
                regPassEdit?.error = getString(R.string.pass_check_error)
                false
            }
            else -> true
        }
    }

    fun setViewListener(listener: RegisterView) {
        mViewListener = listener
    }

    private fun setTermsOfServiceText() {
        val text = getString(R.string.signup_terms_by) + "\n"
        val termsLink = getString(R.string.signup_terms_terms)
        val ampersand = " & "
        val privacyLink = getString(R.string.signup_terms_privacy)

        val linkTermsStart = text.length
        val linkTermsEnd = linkTermsStart + termsLink.length
        val linkPrivacyStart = linkTermsEnd + ampersand.length
        val linkPrivacyEnd = linkPrivacyStart + privacyLink.length

        val linkTermsSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                synchronized(this) {
                    mViewListener?.onTermsOfService()
                }
            }
        }

        val linkPrivacySpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                synchronized(this) {
                    mViewListener?.onTermsOfService()
                }
            }
        }
        val userTermsOfServiceText = SpannableString(text + termsLink + ampersand + privacyLink)
        userTermsOfServiceText.setSpan(linkTermsSpan, linkTermsStart, linkTermsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        userTermsOfServiceText.setSpan(linkPrivacySpan, linkPrivacyStart, linkPrivacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        regTermsOfServiceTV?.text = userTermsOfServiceText
        regTermsOfServiceTV?.movementMethod = LinkMovementMethod.getInstance()
        regTermsOfServiceTV?.highlightColor = Color.TRANSPARENT
    }

    private fun initEditTexts() {
        initName()
        initEmail()
        initPass()
    }

    private fun initName() {
        et_nameRegister?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() == "") {
                    et_nameRegister!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    et_nameRegister!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
                }
            }
        })

        et_nameRegister?.setOnTouchListener { _, motionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val drawRight = et_nameRegister!!.compoundDrawables[DRAWABLE_RIGHT]
                if (drawRight != null) {
                    if (motionEvent.rawX >= et_nameRegister.right - drawRight.bounds.width() - et_nameRegister.paddingEnd) {
                        et_nameRegister.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun initEmail() {
        et_emailRegister?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() == "") {
                    et_emailRegister?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    et_emailRegister?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
                }
            }
        })

        et_emailRegister?.setOnTouchListener { _, motionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val drawRight = et_emailRegister?.compoundDrawables?.get(DRAWABLE_RIGHT)
                if (drawRight != null) {
                    if (motionEvent.rawX >= et_emailRegister.right - drawRight.bounds.width() -
                            et_emailRegister.paddingEnd) {
                        et_emailRegister.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun initPass() {
        et_passwordRegister?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (!l_registerPassword.isPasswordVisibilityToggleEnabled) {
                    l_registerPassword.isPasswordVisibilityToggleEnabled = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
