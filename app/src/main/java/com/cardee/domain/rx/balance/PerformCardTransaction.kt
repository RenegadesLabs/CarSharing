package com.cardee.domain.rx.balance

import com.cardee.data_source.PaymentsRepository
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class PerformCardTransaction(private val repository: PaymentsRepository = PaymentsRepository(),
                             executor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                             responseThread: Scheduler = AndroidSchedulers.mainThread()) :
        UseCase<Boolean>(executor, responseThread) {

    override fun buildUseCaseObserver(request: Request): Observable<Response<Boolean>> {
        val (amount, token, type) = request as CardTransactionRequest
        return Observable.create<Response<Boolean>> { emitter ->
            val payment = CardPayment(amount, token)
            val responseSource = when (type) {
                CardTransactionRequest.Type.CREDIT -> {
                    repository.payCreditWithCard(payment)
                }
                CardTransactionRequest.Type.DEPOSIT -> {
                    repository.payDepositWithCard(payment)
                }
            }
            val disposable = responseSource.subscribe({ result ->
                emitter.onNext(Response(result))
            }, { error ->
                emitter.onNext(Response(null, Response.SOCKET_ERROR, error.message))
            })
        }
    }

    data class CardTransactionRequest(val amount: Long, val token: String, val type: Type) : Request {

        enum class Type { DEPOSIT, CREDIT }
    }
}
