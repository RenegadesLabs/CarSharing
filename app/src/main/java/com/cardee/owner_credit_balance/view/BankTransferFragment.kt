package com.cardee.owner_credit_balance.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.cardee.R
import com.cardee.custom.modal.DatePickerMenuFragment
import com.cardee.owner_credit_balance.BalanceTransactions
import com.cardee.owner_credit_balance.presenter.TransactionsPresenter
import com.cardee.util.SimpleIso8601DateFormatter
import com.cardee.util.ui.InputInteractionController
import com.cardee.util.ui.input_strategy.StrategyFactory
import kotlinx.android.synthetic.main.fragment_bank_transfer.*
import java.lang.ref.WeakReference
import java.util.*


class BankTransferFragment : Fragment(), BalanceTransactions.View<Boolean> {

    companion object {

        fun newInstance(mode: BalanceTransactions.Mode = BalanceTransactions.Mode.CREDIT): Fragment {
            val fragment = BankTransferFragment()
            val args = Bundle()
            args.putSerializable(TransactionsPresenter.MODE, mode)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var presenter: TransactionsPresenter
    private lateinit var controller: InputInteractionController
    private lateinit var mode: BalanceTransactions.Mode
    private var toast: Toast? = null
    private var paddingLeft: Int = 0
    private var paddingLeftLarge: Int = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TransactionsPresenter.useInstance()
        controller = InputInteractionController()
        mode = arguments.getSerializable(TransactionsPresenter.MODE) as BalanceTransactions.Mode
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
        transferDate.setOnClickListener {
            val dateString = transferDate.text.toString()
            parseDate(dateString, { year, month, day ->
                val menu = DatePickerMenuFragment.newInstance(DatePickerMenuFragment.Companion.DATETYPE.TRANSACTION, year, month, day)
                menu.show(activity.supportFragmentManager, DatePickerMenuFragment::class.java.simpleName)
                menu.setOnSaveClickListener(object : DatePickerMenuFragment.DialogOnClickListener {
                    override fun onSaveClicked(type: DatePickerMenuFragment.Companion.DATETYPE, value: String) {
                        transferDate.text = formatIsoDate(value)
                    }
                })
            })
        }
    }

    private fun formatIsoDate(isoDate: String): String? {
        return SimpleIso8601DateFormatter.useInstance().format(isoDate, "dd MMM yyyy")
    }

    private fun parseDate(dateString: String, callback: (Int, Int, Int) -> Unit) {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        callback.invoke(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }


    private fun initViewClickInteraction() {
        transferAmountClear.setOnClickListener { view ->
            transferAmount.text = null
            hideKeyboard(view)
        }
        transferDateClear.setOnClickListener { clearDateInput() }
        transferNumberClear.setOnClickListener { view ->
            transferNumber.text = null
            hideKeyboard(view)
        }
        btnSubmit.setOnClickListener { submit() }
    }

    private fun submit() {
        val amountString = transferAmount.text.toString()
        if (amountString.isEmpty()) {
            onError("Please enter transaction amount")
            return
        }
        val isoDate = transferDate.text.toString().takeIf { it.isNotEmpty() }?.run {
            SimpleIso8601DateFormatter.useInstance().formatToIso(this, "dd MMM yyyy")
        }
        if (isoDate == null) {
            onError("Please enter transfer date")
            return
        }
        val number = transferNumber.text.toString()
        if (number.isEmpty()) {
            onError("Please enter transaction number")
            return
        }
        val amount = (amountString.toDouble() * 100).toLong()

        val args = Bundle()
        args.putLong(TransactionsPresenter.AMOUNT, amount)
        args.putString(TransactionsPresenter.DATE, isoDate)
        args.putString(TransactionsPresenter.NUMBER, number)
        args.putSerializable(TransactionsPresenter.MODE, mode)
        presenter.onTransferSubmit(this, args)
    }

    private fun hideKeyboard(view: View) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearDateInput() {
        transferDate.setText(R.string.top_up_amount)
    }

    override fun onResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFinish() {
        activity.onBackPressed()
    }

    override fun showProgress(isShowing: Boolean) {

    }

    override fun onError(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}