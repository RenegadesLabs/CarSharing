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
        const val TAG = "RegisterFirstStepFragment"
    }

    private val passRegex: Regex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+\$).{8,}\$")
    private val emailRegex: Regex = Regex("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}\$")

    private var regEmailEdit: AppCompatEditText? = null
    private var regPassEdit: AppCompatEditText? = null
    private var regTermsOfServiceTV: TextView? = null

    private var mViewListener: RegisterView? = null

    private val isFieldsNotEmpty: Boolean
        get() {
            return when {
                et_nameRegister.text.toString().isBlank() -> {
                    et_nameRegister.error = resources.getString(R.string.name_empty_error)
                    false
                }
                regEmailEdit?.text.toString().isBlank() -> {
                    regEmailEdit?.error = resources.getString(R.string.email_empty_error)
                    false
                }
                regPassEdit?.text.toString().isBlank() -> {
                    l_registerPassword.isPasswordVisibilityToggleEnabled = false
                    regPassEdit?.error = resources.getString(R.string.pass_empty_error)
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
                    mViewListener?.onSignUp(regEmailEdit?.text.toString(), regPassEdit?.text.toString(), et_nameRegister?.text.toString())
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
            et_emailRegister?.text.toString().matches(emailRegex).not() -> {
                et_emailRegister?.error = resources.getString(R.string.email_invalid_error)
                false
            }
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
                    et_nameRegister!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_button_transparent, 0)
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
                    et_emailRegister?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_button_transparent, 0)
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
