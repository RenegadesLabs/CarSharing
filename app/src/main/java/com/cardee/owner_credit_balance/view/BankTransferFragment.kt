package com.cardee.owner_credit_balance.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.cardee.R
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.util.ui.InputInteractionController
import com.cardee.util.ui.input_strategy.StrategyFactory
import kotlinx.android.synthetic.main.fragment_bank_transfer.*
import java.lang.ref.WeakReference


class BankTransferFragment : Fragment() {

    companion object {

        fun newInstance(): Fragment {
            return BankTransferFragment()
        }
    }

    private lateinit var presenter: TransactionsPresenter
    private lateinit var controller: InputInteractionController
    private var paddingLeft: Int = 0
    private var paddingLeftLarge: Int = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TransactionsPresenter.useInstance()
        controller = InputInteractionController()
        initRes(activity)
    }

    private fun initRes(context: Context) {
        context.resources.apply {
            paddingLeft = getDimensionPixelSize(R.dimen.widget_padding_mid)
            paddingLeftLarge = getDimensionPixelOffset(R.dimen.widget_padding_x_large)
            paddingRight = getDimensionPixelSize(R.dimen.widget_padding_xx_large)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_bank_transfer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weakCurrencySymbol = WeakReference(currencySymbol)
        val weakTransferAmount = WeakReference(transferAmount)
        val weakTransferAmountClear = WeakReference(transferAmountClear)
        val weakTransferNumberClear = WeakReference(transferNumberClear)


        controller.addInput(transferAmount, StrategyFactory.newPriceStrategy(2, { value ->
            val visible = !value.isEmpty()
            weakCurrencySymbol.get()?.visibility = if (visible) View.VISIBLE else View.GONE
            weakTransferAmountClear.get()?.visibility = if (visible) View.VISIBLE else View.GONE
            weakTransferAmount.get()?.let { inputView ->
                if (visible) {
                    inputView.setPadding(paddingLeftLarge, paddingTop, paddingRight, paddingBottom)
                } else {
                    inputView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
                }
            }
        }))


        controller.addInput(transferNumber, StrategyFactory.newNumberStrategy({ value ->
            weakTransferNumberClear.get()?.visibility = if (!value.isEmpty())
                View.VISIBLE
            else
                View.GONE
        }))

        initDateDialogAppearance()
        initViewClickInteraction()
    }

    private fun initDateDialogAppearance() {

    }


    private fun initViewClickInteraction() {
        transferAmountClear.setOnClickListener { view ->
            transferAmount.text = null
            hideKeyboard(view)
        }
        transferDateClear.setOnClickListener { clearDateInput() }
        transferNumber.setOnClickListener { view ->
            transferNumber.text = null
            hideKeyboard(view)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearDateInput() {
        transferDate.setText(R.string.top_up_amount)

    }
}