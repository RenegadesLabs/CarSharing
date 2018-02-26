package com.cardee.domain.rx.balance

import com.cardee.data_source.PaymentsRepository
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.ref.WeakReference

class RetrieveTransactionHistory(private val repository: PaymentsRepository = PaymentsRepository(),
                                 executor: ThreadExecutor = ThreadExecutor.getInstance()!!,
                                 responseThread: Scheduler = AndroidSchedulers.mainThread()) :
        UseCase<List<Transaction>>(executor, responseThread) {

    override fun buildUseCaseObserver(request: Request): Observable<Response<List<Transaction>>> {
        val weakRepo = WeakReference(repository)
        return Observable.create { emitter ->
            val disposable = weakRepo.get()?.getTransactions()?.subscribe({ response ->
                emitter.onNext(Response(response))
            }, { error ->
                emitter.onNext(Response(null, Response.SERVER_ERROR, error.message))
            })
            disposable ?: emitter.onError(NullPointerException("PaymentRepository instance is null"))
        }
    }
}



