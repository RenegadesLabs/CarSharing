package com.cardee.domain.rx.balance

import com.cardee.data_source.Error
import com.cardee.data_source.RenterProfileDataSource
import com.cardee.data_source.remote.RemoteRenterProfileDataSource
import com.cardee.data_source.remote.api.profile.response.entity.RenterProfile
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


class FetchDepositBalance(private val repository: RemoteRenterProfileDataSource = RemoteRenterProfileDataSource.getInstance()) :
        UseCase<Double>(ThreadExecutor.getInstance()!!, AndroidSchedulers.mainThread()) {


    override fun buildUseCaseObserver(request: Request): Observable<Response<Double>> {
        return Observable.create<Response<Double>> { emitter ->
            repository.loadRenterProfile(object : RenterProfileDataSource.ProfileCallback {
                override fun onSuccess(renterProfile: RenterProfile?) {
                    emitter.onNext(Response(100.0))
                }

                override fun onError(error: Error?) {
                    emitter.onNext(Response(null, Response.SERVER_ERROR, error?.message))
                }
            })
        }
    }
}