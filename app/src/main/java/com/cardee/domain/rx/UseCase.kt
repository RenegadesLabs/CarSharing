package com.cardee.domain.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class UseCase<T>(private val executor: ThreadExecutor, private val responseThread: Scheduler) {

    private var disposable: Disposable = Disposables.empty()

    abstract protected fun buildUseCaseObserver(request: Request): Observable<Response<T>>

    fun execute(request: Request,
                success: (Response<T>) -> Unit,
                error: (Throwable) -> Unit,
                complete: () -> Unit) {
        disposable = buildUseCaseObserver(request)
                .observeOn(Schedulers.from(executor))
                .subscribeOn(responseThread)
                .subscribe(success, error, complete)
    }

    fun execute(request: Request,
                success: (Response<T>) -> Unit,
                error: (Throwable) -> Unit) {
        disposable = buildUseCaseObserver(request)
                .observeOn(Schedulers.from(executor))
                .subscribeOn(responseThread)
                .subscribe(success, error)
    }

    /**
     * Task cannot be disposed via fun stop().
     * Disposing can be performed only externally via disposable.dispose()
     */
    fun execute(request: Request, observer: Observer<Response<T>>) {
        buildUseCaseObserver(request)
                .observeOn(Schedulers.from(executor))
                .subscribeOn(responseThread)
                .subscribe(observer)
    }

    fun stop() {
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}


