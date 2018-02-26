package com.cardee.domain.rx.balance

import com.cardee.data_source.PaymentsRepository
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class PerformBankTransaction(private val repository: PaymentsRepository = PaymentsRepository(),
                             threadExecutor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                             responseThread: Scheduler = AndroidSchedulers.mainThread()) :
        UseCase<Boolean>(threadExecutor, responseThread) {

    override fun buildUseCaseObserver(request: Request): Observable<Response<Boolean>> {
        val (amount, isoDate, number, type) = request as BankTransferRequest
        return Observable.create<Response<Boolean>> { emitter ->
            val transfer = BankTransfer(number, isoDate, amount)
            val responseSource = when (type) {
                BankTransferRequest.Type.CREDIT -> {
                    repository.payCreditWithBankTransfer(transfer)
                }
                BankTransferRequest.Type.DEPOSIT -> {
                    repository.payDepositWithBankTransfer(transfer)
                }
            }
            val disposable = responseSource.subscribe({ result ->
                emitter.onNext(Response(result))
            }, { error ->
                emitter.onNext(Response(null, Response.SOCKET_ERROR, error.message))
            })
        }
    }

    data class BankTransferRequest(val amount: Long,
                                   val isoDate: String,
                                   val number: String,
                                   val type: Type) : Request {
        enum class Type { DEPOSIT, CREDIT }
    }
}
